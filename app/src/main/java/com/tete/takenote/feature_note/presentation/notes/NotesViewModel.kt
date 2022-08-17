package com.tete.takenote.feature_note.presentation.notes

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tete.takenote.feature_note.data.local.datastore.PreferenceDatastore
import com.tete.takenote.feature_note.data.local.entity.NoteEntity
import com.tete.takenote.feature_note.domain.use_case.NoteUseCases
import com.tete.takenote.feature_note.domain.util.NoteOrder
import com.tete.takenote.feature_note.domain.util.OrderType
import com.tete.takenote.feature_note.presentation.util.Screens
import com.tete.takenote.feature_note.presentation.util.replace
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.text.MessageFormat
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases,
    private val preferencesStore: PreferenceDatastore
) : ViewModel() {

    private val _state = mutableStateOf(NotesState())
    val state: State<NotesState> = _state

    private var recentlyDeletedNote: NoteEntity? = null

    private var getNotesJob: Job? = null

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    init {
        getNotes(NoteOrder.Title(OrderType.Ascending))
    }

    fun setPreferencesStore() {
        viewModelScope.launch{
            preferencesStore.saveIntValue(66)
        }
    }

    fun getPreferencesStore() {
        viewModelScope.launch(Dispatchers.IO){
            preferencesStore.getIntValue.collect {
                println(it)
            }
        }
    }

    fun onEvent(event: NotesEvent) {
        when (event) {
            is NotesEvent.OnOrderChange -> {
                if (state.value.noteOrder::class == event.noteOrder::class &&
                    state.value.noteOrder.orderType == event.noteOrder.orderType
                ) {
                    return
                }
                getNotes(event.noteOrder)
            }
            is NotesEvent.OnDeleteNoteClick -> {
                viewModelScope.launch {
                    noteUseCases.deleteNote(event.note)
                    recentlyDeletedNote = event.note
                    sendUiEvent(UiEvent.ShowSnackbar(message =  "Note deleted", action = "Undo"))
                }
            }
            is NotesEvent.OnUndoDeleteNoteClick -> {
                viewModelScope.launch {
                    noteUseCases.addNote(recentlyDeletedNote ?: return@launch)
                    recentlyDeletedNote = null
                }
            }
            is NotesEvent.OnToggleOrderSectionClick -> {
                _state.value = state.value.copy(
                    isOrderSectionVisible = !state.value.isOrderSectionVisible
                )
            }
            is NotesEvent.OnNoteClick -> {
                viewModelScope.launch {
                    val route: String = Screens.AddEditNoteScreen.route.replace(
                        "{noteId}" to event.note.id,
                        "{noteColor}" to event.note.color
                    )
                    sendUiEvent(UiEvent.Navigate(route))
                }
            }
            is NotesEvent.OnAddNoteClick -> {
                viewModelScope.launch {
                    sendUiEvent(UiEvent.Navigate(Screens.AddEditNoteScreen.route))
                }
            }
        }
    }

    private fun getNotes(noteOrder: NoteOrder) {
        getNotesJob?.cancel()
        getNotesJob = noteUseCases.getNotes(noteOrder)
            .onEach { notes ->
                _state.value = state.value.copy(
                    notes = notes,
                    noteOrder = noteOrder
                )
            }
            .launchIn(viewModelScope)
    }

    fun getTrack(): List<String> {
        return state.value.notes.map{
            if (state.value.noteOrder is NoteOrder.Date) {
                it.timestamp.toString().first().uppercase()
            } else if (state.value.noteOrder is NoteOrder.Title) {
                it.title.first().uppercase()
            } else if (state.value.noteOrder is NoteOrder.Color) {
                it.color.toString().first().uppercase()
            } else {
                it.title.first().uppercase()
            }
        }.toSet().toList()
    }

    private suspend fun sendUiEvent(event: UiEvent) {
        _uiEvent.emit(event)
    }

    sealed class UiEvent {
        data class ShowSnackbar(val message: String, val action: String? = null): UiEvent()
        data class Navigate (val route: String): UiEvent()
        object NavigateUp: UiEvent()
    }
}