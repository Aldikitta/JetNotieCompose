package com.aldikitta.crudnoteapp.feature_note.presentation.add_edit_note.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class,
    ExperimentalComposeUiApi::class
)
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
    val bringIntoViewRequester = BringIntoViewRequester()
    val focusManager = LocalFocusManager.current
    val coroutineScope = rememberCoroutineScope()
    val focusRequester = FocusRequester()
    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
//            .fillMaxHeight(),
            .focusRequester(focusRequester)
            .onFocusChanged { onFocusChange(it) },
//            .bringIntoViewRequester(bringIntoViewRequester)
//            .onFocusEvent { focusState ->
//                if (focusState.isFocused) {
//                    coroutineScope.launch {
//                        bringIntoViewRequester.bringIntoView()
//                    }
//                }
//            },
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