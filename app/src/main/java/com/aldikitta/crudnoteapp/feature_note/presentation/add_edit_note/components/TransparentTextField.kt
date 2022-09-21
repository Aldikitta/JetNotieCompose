package com.aldikitta.crudnoteapp.feature_note.presentation.add_edit_note.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransparentTextField(
    text: String,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit,
    onFocusChange: (FocusState) -> Unit,
    singleline: Boolean = false,
    placeholder: (@Composable () -> Unit)? = null,
    focusColor: Color = Color.Transparent,
    textStyle: TextStyle

) {
    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .onFocusChanged { onFocusChange(it) },
        shape = MaterialTheme.shapes.medium,
        value = text,
        onValueChange = onValueChange,
        placeholder = placeholder,
        singleLine = singleline,
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.Transparent,
            focusedIndicatorColor = focusColor,
            unfocusedIndicatorColor = focusColor,
            disabledIndicatorColor = Color.Transparent,
        ),
        textStyle = textStyle
    )
}