package com.aldikitta.crudnoteapp.feature_note.presentation.add_edit_note.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.TextStyle
import com.aldikitta.crudnoteapp.ui.theme.spacing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransparentTextField(
    text: String,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit,
    onFocusChange: (FocusState) -> Unit,
    singleline: Boolean = false,
    placeholder: (@Composable () -> Unit)? = null,
    focusColor: Color = Color.Transparent

) {
        OutlinedTextField(
            modifier = modifier
                .fillMaxWidth()
                .padding(MaterialTheme.spacing.small)
                .onFocusChanged { onFocusChange(it) },
            shape = MaterialTheme.shapes.medium,
            value = text,
            onValueChange = onValueChange,

            placeholder = placeholder,
//            placeholder = {
//                if (isHintVisible){
//                    Text(text = hint, modifier = modifier)
//                }
//            },
            singleLine = singleline,
//            textStyle = textStyle!!,
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent,
                focusedIndicatorColor = focusColor,
                unfocusedIndicatorColor = focusColor,
                disabledIndicatorColor = Color.Transparent,
//                selectionColors = Color.Red
            )
        )
    }