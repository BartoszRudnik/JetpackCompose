package com.example.noteapp.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.noteapp.R
import com.example.noteapp.components.NoteButton
import com.example.noteapp.components.NoteInputText
import com.example.noteapp.model.Note

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun NoteScreen(notes: List<Note>, onAddNote: (Note) -> Unit, onRemoveNote: (Note) -> Unit) {
    var title by remember {
        mutableStateOf("")
    }

    var description by remember {
        mutableStateOf("")
    }

    Column(modifier = Modifier.padding(6.dp)) {
        TopAppBar(title = {
            Text(text = stringResource(id = R.string.app_name))
        }, actions = {
            Icon(imageVector = Icons.Rounded.Notifications, contentDescription = "")
        }, backgroundColor = Color.LightGray)

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            NoteInputText(
                label = "Title",
                text = title,
                onTextChange = {
                    if (it.all { char ->
                            char.isLetter() || char.isWhitespace()
                        }) {
                        title = it
                    }
                },
                modifier = Modifier.padding(8.dp)
            )
            NoteInputText(
                label = "Description",
                text = description,
                onTextChange = {
                    if (it.all { char ->
                            char.isLetterOrDigit() || char.isWhitespace()
                        }) {
                        description = it
                    }
                },
                modifier = Modifier.padding(8.dp)
            )
            NoteButton(text = "Save", onClick = {
                if (title.isNotEmpty() && description.isNotEmpty()) {

                    title = ""
                    description = ""
                }
            }, modifier = Modifier.padding(8.dp))
        }
        Divider(modifier = Modifier.padding(10.dp))
        LazyColumn {
            items(notes) { note ->
                SingleNote(onRemoveNote, note)
            }
        }
    }

}

@Composable
private fun SingleNote(
    onRemoveNote: (Note) -> Unit,
    note: Note
) {
    Card(
        backgroundColor = Color.LightGray,
        modifier = Modifier
            .clickable { onRemoveNote(note) }
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.Start) {
            Text(text = note.title, style = MaterialTheme.typography.subtitle1)
            Text(text = note.description, style = MaterialTheme.typography.subtitle2)
            Text(text = note.creationDate.toString(), style = MaterialTheme.typography.caption)
        }
    }
}