package com.aldikitta.crudnoteapp.feature_note.presentation.notes.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.aldikitta.crudnoteapp.feature_note.domain.model.Note
import com.aldikitta.crudnoteapp.ui.theme.spacing

@Composable
fun NoteItem(
    note: Note,
    modifier: Modifier = Modifier,
    onnDeleteClick: () -> Unit
) {
    Card() {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(MaterialTheme.spacing.medium)
        ) {
            Text(text = note.title)
            Text(text = note.title)
        }
        IconButton(onClick = onnDeleteClick) {
            Icon(
                imageVector = Icons.Filled.Delete,
                contentDescription = null
            )
        }
    }
}