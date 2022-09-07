package com.aldikitta.crudnoteapp.feature_note.presentation.add_edit_note.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransparentTextField(
    text: String,
    hint: String,
    modifier: Modifier = Modifier,
    isHintVisible: Boolean = true,
    onValueChange: (String) -> Unit,
    onFocusChange: (FocusState) -> Unit,
    singleline: Boolean = false
) {
    Box(modifier = modifier) {
        TextField(
            value = text,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { onFocusChange(it) },
            placeholder = {
                if (isHintVisible){
                    Text(text = hint)
                }
            },
            singleLine = singleline
        )
    }
}