package com.aldikitta.crudnoteapp.feature_note.presentation.notes.components.toolbar

import android.text.format.DateFormat
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material.icons.rounded.PrivacyTip
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.compose.ui.viewinterop.AndroidView
import com.aldikitta.crudnoteapp.R
import com.aldikitta.crudnoteapp.ui.theme.*
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

private val ContentPadding = 8.dp
private val Elevation = 4.dp
private val ButtonSize = 24.dp
private const val Alpha = 0.75f

private val ExpandedPadding = 1.dp
private val CollapsedPadding = 3.dp

private val ExpandedCostaRicaHeight = 20.dp
private val CollapsedCostaRicaHeight = 16.dp

private val ExpandedWildlifeHeight = 80.dp
private val CollapsedWildlifeHeight = 75.dp

private val MapHeight = CollapsedCostaRicaHeight * 2

//@Preview
//@Composable
//fun CollapsingToolbarCollapsedPreview() {
//    CRUDNoteAppTheme {
//        CollapsingToolbar(
//            backgroundImageResId = R.drawable.toolbar_background,
//            progress = 0f,
//            onPrivacyTipButtonClicked = {},
//            onSettingsButtonClicked = {},
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(80.dp)
//        )
//    }
//}
//
//@Preview
//@Composable
//fun CollapsingToolbarHalfwayPreview() {
//    CRUDNoteAppTheme {
//        CollapsingToolbar(
//            backgroundImageResId = R.drawable.toolbar_background,
//            progress = 0.5f,
//            onPrivacyTipButtonClicked = {},
//            onSettingsButtonClicked = {},
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(120.dp)
//        )
//    }
//}
//
//@Preview
//@Composable
//fun CollapsingToolbarExpandedPreview() {
//    CRUDNoteAppTheme {
//        CollapsingToolbar(
//            backgroundImageResId = R.drawable.toolbar_background,
//            progress = 1f,
//            onPrivacyTipButtonClicked = {},
//            onSettingsButtonClicked = {},
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(160.dp)
//        )
//    }
//}

@Composable
fun CollapsingToolbar(
    progress: Float,
    onSortClicked: () -> Unit,
    onSearchClicked: () -> Unit,
    modifier: Modifier = Modifier,
    noteCount: Int
) {
    val wildlifeHeight = with(LocalDensity.current) {
        lerp(CollapsedWildlifeHeight.toPx(), ExpandedWildlifeHeight.toPx(), progress).toDp()
    }
    val logoPadding = with(LocalDensity.current) {
        lerp(CollapsedPadding.toPx(), ExpandedPadding.toPx(), progress).toDp()
    }

    val day = SimpleDateFormat("h:mm a")
    val month = SimpleDateFormat("EEE, MMM d")

    val dayOfMonth = month.format(Date())
    var dayOfWeek by remember {
        mutableStateOf("")
    }

    LaunchedEffect(Unit) {
        while (true) {
            var getCurrentTime = day.format(Date())
            dayOfWeek = getCurrentTime
            delay(1000)
        }
    }

    val currentHour by remember {
        mutableStateOf(Calendar.getInstance().get(Calendar.HOUR_OF_DAY))
    }
    var greeting by remember {
        mutableStateOf("")
    }
    LaunchedEffect(currentHour) {
        when (currentHour) {
            in 0..11 -> {
                greeting = "Good Morning"
                Log.d("TAG", greeting)
            }
            in 12..15 -> {
                greeting = "Good Afternoon"
                Log.d("TAG", greeting)
            }
            in 16..20 -> {
                greeting = "Good Evening"
                Log.d("TAG", greeting)
            }
            in 21..23 -> {
                greeting = "Good Night"
                Log.d("TAG", greeting)
            }
        }
    }


    Surface(
        modifier = modifier
    ) {
        val gradientGrayWhite = Brush.linearGradient(
            colors = listOf(
                MaterialTheme.colorScheme.primaryContainer.copy(alpha = progress * Alpha),
                MaterialTheme.colorScheme.primary.copy(alpha = progress * Alpha)
            ),
            tileMode = TileMode.Clamp,
            start = Offset(0f, Float.POSITIVE_INFINITY),
            end = Offset(Float.POSITIVE_INFINITY, 0f)
        )

        Box(modifier = Modifier.fillMaxSize()) {
//            Box(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .clip(shape = RoundedCornerShape(MaterialTheme.spacing.medium))
//                    .background(gradientGrayWhite)
//            )

            FeatureItem(
                modifier = modifier
                    .fillMaxSize(),
                darkColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = progress * Alpha),
                mediumColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = progress * Alpha),
                lightColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = progress * Alpha),
                )

            Box(
                modifier = Modifier
                    .statusBarsPadding()
                    .padding(horizontal = ContentPadding)
                    .fillMaxSize()
            ) {
                CollapsingToolbarLayout(progress = progress) {
                    Text(
                        text = "All Notes ($noteCount)",
                        modifier = Modifier
                            .wrapContentWidth()
                            .padding(start = MaterialTheme.spacing.small)
                            .graphicsLayer { alpha = ((0.25f - progress) * 4).coerceIn(0f, 1f) },
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground,
                    )

                    Text(
                        modifier = Modifier
                            .graphicsLayer {
                                alpha = ((progress - 0.5f) * 4).coerceIn(
                                    minimumValue = 0f,
                                    maximumValue = 1f
                                )
                            }
                            .padding(start = MaterialTheme.spacing.small)
                            .wrapContentWidth(),
                        text = greeting,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        modifier = Modifier
                            .graphicsLayer {
                                alpha = ((progress - 0.5f) * 4).coerceIn(
                                    minimumValue = 0f,
                                    maximumValue = 1f
                                )
                            }
                            .padding(start = MaterialTheme.spacing.small)
                            .wrapContentWidth(),
                        text = "You have $noteCount notes",
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Light
                    )
                    Text(
                        modifier = Modifier
                            .padding(end = MaterialTheme.spacing.small)
                            .height(wildlifeHeight)

                            .graphicsLayer {
                                alpha = ((progress - 0.5f) * 4).coerceIn(
                                    minimumValue = 0f,
                                    maximumValue = 1f
                                )
                            }
                            .wrapContentWidth(),
                        text = "$dayOfWeek,\r\n$dayOfMonth",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        fontWeight = FontWeight.Light,
                        textAlign = TextAlign.Center
                    )
                    Row(
                        modifier = Modifier.wrapContentSize(),
                        horizontalArrangement = Arrangement.spacedBy(ContentPadding)
                    ) {
                        IconButton(
                            onClick = onSortClicked,
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Sort,
                                contentDescription = null,
                            )
                        }
                        IconButton(
                            onClick = onSearchClicked,
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Search,
                                contentDescription = null,
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun CollapsingToolbarLayout(
    progress: Float,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        content = content
    ) { measurables, constraints ->
        check(measurables.size == 5) // [0]: Country Map | [1-3]: Logo Images | [4]: Buttons

        val placeables = measurables.map {
            it.measure(constraints)
        }
        layout(
            width = constraints.maxWidth,
            height = constraints.maxHeight
        ) {

            val expandedHorizontalGuideline = (constraints.maxHeight * 0.4f).roundToInt()
            val collapsedHorizontalGuideline = (constraints.maxHeight * 0.5f).roundToInt()

            val countryMap = placeables[0]
            val costa = placeables[1]
            val rica = placeables[2]
            val wildlife = placeables[3]
            val buttons = placeables[4]
            countryMap.placeRelative(
                x = 0,
                y = collapsedHorizontalGuideline - countryMap.height / 2,
            )
            costa.placeRelative(
                x = lerp(
                    start = 0,
                    stop = 0,
                    fraction = progress
                ),
                y = lerp(
                    start = collapsedHorizontalGuideline - costa.height / 2,
                    stop = expandedHorizontalGuideline - costa.height / 2,
                    fraction = progress
                )
            )
            rica.placeRelative(
                x = lerp(
                    start = 0,
                    stop = 0,
                    fraction = progress
                ),
                y = lerp(
                    start = collapsedHorizontalGuideline + rica.height / 2,
                    stop = expandedHorizontalGuideline + rica.height / 2,
                    fraction = progress
                )
            )
            wildlife.placeRelative(
                x = lerp(
                    start = constraints.maxWidth - wildlife.width,
                    stop = constraints.maxWidth - wildlife.width,
                    fraction = progress
                ),
                y = lerp(
                    start = constraints.maxHeight - wildlife.height,
                    stop = constraints.maxHeight - wildlife.height,
                    fraction = progress
                )
            )
            buttons.placeRelative(
                x = constraints.maxWidth - buttons.width,
                y = lerp(
                    start = (constraints.maxHeight - buttons.height) / 2,
                    stop = expandedHorizontalGuideline / 4,
                    fraction = progress
                )
            )
        }
    }
}