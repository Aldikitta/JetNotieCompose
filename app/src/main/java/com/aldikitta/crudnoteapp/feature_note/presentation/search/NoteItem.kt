package com.aldikitta.crudnoteapp.feature_note.presentation.search

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aldikitta.crudnoteapp.feature_note.domain.model.Note

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LazyItemScope.NoteItem(
    modifier: Modifier = Modifier,
    task: Note,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .padding(horizontal = 8.dp)
            .animateItemPlacement(),
        shape = RoundedCornerShape(16.dp),
    ) {
        Column(
            Modifier
                .clickable {
                    onClick()
                }
                .padding(12.dp)
        ) {
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Spacer(Modifier.width(8.dp))
                Text(
                    text = task.title,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
//            if (task.dueDate != 0L) {
//                Spacer(Modifier.height(8.dp))
//                Row(verticalAlignment = Alignment.CenterVertically) {
//                    Icon(
//                        modifier = Modifier.size(13.dp),
//                        painter = painterResource(R.drawable.ic_alarm),
//                        contentDescription = stringResource(R.string.due_date),
//                        tint = if (task.dueDate.isDueDateOverdue()) Color.Red else MaterialTheme.colors.onSurface
//                    )
//                    Spacer(Modifier.width(4.dp))
//                    Text(
//                        text = task.dueDate.formatDateDependingOnDay(),
//                        style = MaterialTheme.typography.body2,
//                        color = if (task.dueDate.isDueDateOverdue()) Color.Red else MaterialTheme.colors.onSurface
//                    )
//                }
//            }
        }
    }
}

//@Preview
//@Composable
//fun LazyItemScope.TaskItemPreview() {
//    TaskItem(
//        task = Task(
//            title = "Task 1",
//            description = "Task 1 description",
//            dueDate = 1666999999999L,
//            priority = 1,
//            isCompleted = false
//        ),
//        onComplete = {},
//        onClick = {}
//    )
//}