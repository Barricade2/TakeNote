package com.tete.takenote.note.presentation.notes

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Sort
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.boundsInParent
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.tete.takenote.core.utils.TestTags
import com.tete.takenote.note.presentation.notes.components.NoteItem
import com.tete.takenote.note.presentation.notes.components.OrderSection
import com.tete.takenote.coreui.ui.theme.TrackGray
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.lang.Math.abs

@Composable
fun NotesScreen(
    navController: NavController,
    viewModel: NotesViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val listState = rememberLazyListState()

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collectLatest { event ->
            when(event) {
                is NotesViewModel.UiEvent.ShowSnackbar -> {
                    val result = scaffoldState.snackbarHostState.showSnackbar(message = event.message, actionLabel = event.action)
                    if(result == SnackbarResult.ActionPerformed) {
                        viewModel.onEvent(NotesEvent.OnUndoDeleteNoteClick)
                    }
                }
                is NotesViewModel.UiEvent.Navigate -> {
                    navController.navigate(event.route)
                }
            }
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.onEvent(NotesEvent.OnAddNoteClick)
                },
                backgroundColor = MaterialTheme.colors.primary
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
            }
        },
        scaffoldState = scaffoldState
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Your note",
                    style = MaterialTheme.typography.h4
                )
                IconButton(
                    onClick = {
                        viewModel.onEvent(NotesEvent.OnToggleOrderSectionClick)
                        viewModel.setPreferencesStore()
                    },
                ) {
                    Icon(
                        imageVector = Icons.Default.Sort,
                        contentDescription = "Sort"
                    )
                }
            }
            AnimatedVisibility(
                visible = state.isOrderSectionVisible,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically()
            ) {
                OrderSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .testTag(TestTags.ORDER_SECTION),
                    noteOrder = state.noteOrder,
                    onOrderChange = {
                        viewModel.onEvent(NotesEvent.OnOrderChange(it))
                        viewModel.getPreferencesStore()
                    }
                )
                TransformableSample()
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row {
                LazyColumn(modifier = Modifier.weight(1f).padding(16.dp,0.dp), state = listState) {
                        items(state.notes) { note ->
                            NoteItem(
                                note = note,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        viewModel.onEvent(NotesEvent.OnNoteClick(note))
                                    },
                                onDeleteClick = {
                                    viewModel.onEvent(NotesEvent.OnDeleteNoteClick(note))
                                }
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }
                Track(state,listState,scope, viewModel)
            }
        }
    }
}

@Composable
fun Track(state: NotesState, listState: LazyListState, scope: CoroutineScope, viewModel: NotesViewModel) {
    val offsets = remember { mutableStateMapOf<Int, Float>() }
    val track = viewModel.getTrack()

    fun updateSelectedIndexIfNeeded(offset: Float) {
        val index = offsets.mapValues { abs(it.value - offset) }
            .entries.minByOrNull { it.value }?.key ?: return
        if (listState.firstVisibleItemIndex == index) return
        scope.launch {
            listState.scrollToItem(index)
        }
    }

    Column(
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxHeight()
            .background(TrackGray)
            .pointerInput(Unit) {
                detectTapGestures {
                    updateSelectedIndexIfNeeded(it.y)
                }
            }
            .pointerInput(Unit) {
                detectVerticalDragGestures { change, _ ->
                    updateSelectedIndexIfNeeded(change.position.y)
                }
            }
    ) {
        track.forEachIndexed { i, track ->
            Text(
                track,
                modifier = Modifier.onGloballyPositioned {
                    offsets[i] = it.boundsInParent().center.y
                },
                style = MaterialTheme.typography.overline)
        }
    }
}

@Composable
fun TransformableSample() {
    // set up all transformation states
    var scale by remember { mutableStateOf(1f) }
    //var offset by remember { mutableStateOf(Offset.Zero) }
    val state = rememberTransformableState { zoomChange, offsetChange, rotationChange ->
        scale *= zoomChange
       // offset += offsetChange
    }

    var scale1 by remember { mutableStateOf(1f) }
    var offset1 by remember { mutableStateOf(Offset.Zero) }
    val state1 = rememberTransformableState { zoomChange, offsetChange, rotationChange ->
        scale1 *= zoomChange
        //offset1 += offsetChange
    }

    var scale2 by remember { mutableStateOf(1f) }
    var offset2 by remember { mutableStateOf(Offset.Zero) }
    val state2 = rememberTransformableState { zoomChange, offsetChange, rotationChange ->
        scale2 *= zoomChange
            //offset2 += offsetChange
    }
    Row{
        Box(
            Modifier
                // apply other transformations like rotation and zoom
                // on the pizza slice emoji
                .graphicsLayer(
                    scaleX = scale,
                    scaleY = scale,
                   // translationX = offset.x,
                   // translationY = offset.y
                )
                // add transformable to listen to multitouch transformation events
                // after offset
                .transformable(state = state)
                .background(Color.Red)
                .size(100.dp)
        ){
            Text(text="Text")
        }
        Box(
            Modifier
                // apply other transformations like rotation and zoom
                // on the pizza slice emoji
                .graphicsLayer(
                    scaleX = scale1,
                    scaleY = scale1,
                 //   translationX = offset1.x,
                 //   translationY = offset1.y
                )
                // add transformable to listen to multitouch transformation events
                // after offset
                .transformable(state = state1)
                .background(Color.Blue)
                .size(100.dp)
        )
        Box(
            Modifier
                // apply other transformations like rotation and zoom
                // on the pizza slice emoji
                .graphicsLayer(
                    scaleX = scale2,
                    scaleY = scale2,
                 //   translationX = offset2.x,
                 //   translationY = offset2.y
                )
                // add transformable to listen to multitouch transformation events
                // after offset
                .transformable(state = state2)
                .background(Color.Green)
                .size(150.dp)
        )
    }

}