package com.aldikitta.crudnoteapp.feature_note.domain.model

import android.media.Image
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.aldikitta.crudnoteapp.ui.theme.*

@Entity
data class Note(
    @PrimaryKey val id: Int? = null,
    val title: String,
    val content: String,
    val timeStamp: Long,
    val color: Int,
) {
    companion object {
        val noteColors = listOf(RedOrange, RedPink, Violet, LightGreen)
    }
}

class InvalidNoteException(message: String) : Exception(message)
