//package com.aldikitta.crudnoteapp.feature_note.presentation.notes.components
//
//import androidx.compose.runtime.Composable
//
//@Composable
//fun StaggeredGridView() {
//    // on below line we are creating
//    // an array of images.
//    val images = listOf(
//        R.drawable.imgone,
//        R.drawable.imgtwo,
//        R.drawable.imgthree,
//        R.drawable.imgfour,
//        R.drawable.imgfive,
//        R.drawable.imgsix,
//        R.drawable.imgseven,
//        R.drawable.imgeight,
//        R.drawable.imgnine,
//        R.drawable.imgten
//    )
//
//    // on below line we are creating a column
//    // for our staggered grid view.
//    Column(
//        // for this column we are adding a
//        // modifier to it to fill max size.
//        modifier = Modifier
//            .fillMaxSize()
//    ) {
//        // on below line we are creating a column
//        // for each item of our staggered grid.
//        Column(
//            // in this column we are adding modifier to it
//            // and adding padding from all sides and vertical scroll.
//            modifier = Modifier
//                .verticalScroll(rememberScrollState())
//                .padding(5.dp)
//        ) {
//            // on below line we are calling our
//            // custom staggered vertical grid item.
//            CustomStaggeredVerticalGrid(
//                // on below line we are specifying
//                // number of columns for our grid view.
//                numColumns = 2,
//
//                // on below line we are adding padding
//                // from all sides for our grid view.
//                modifier = Modifier.padding(5.dp)
//            ) {
//                // inside staggered grid view we are
//                // adding images for each item of grid.
//                images.forEach { img ->
//                    // on below line inside our grid
//                    // item we are adding card.
//                    Card(
//                        // on below line inside the card we
//                        // are adding modifier to it to specify
//                        // max width, padding, elevation and shape for the card
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(5.dp),
//                        elevation = 10.dp,
//                        shape = RoundedCornerShape(10.dp)
//                    ) {
//                        // on below line we are adding column inside our card.
//                        Column(
//                            // in this column we are adding modifier
//                            // to fill max size and align our
//                            // card center horizontally.
//                            modifier = Modifier
//                                .fillMaxSize()
//                                .align(Alignment.CenterHorizontally),
//                            horizontalAlignment = Alignment.CenterHorizontally
//                        ) {
//                            // inside our column we are creating an image.
//                            Image(
//                                // on below line we are specifying the
//                                // drawable image for our image.
//                                painterResource(id = img),
//
//                                // on below line we are specifying
//                                // content description for our image
//                                contentDescription = "images",
//
//                                // on below line we are specifying
//                                // alignment for our image.
//                                alignment = Alignment.Center,
//                            )
//                        }
//                    }
//                }
//            }
//        }
//    }
//}
//
//// on below line we are creating a custom
//// composable item for our grid view item.
//@Composable
//fun CustomStaggeredVerticalGrid(
//    // on below line we are specifying
//    // parameters as modifier, num of columns
//    modifier: Modifier = Modifier,
//    numColumns: Int = 2,
//    content: @Composable () -> Unit
//) {
//    // inside this grid we are creating
//    // a layout on below line.
//    Layout(
//        // on below line we are specifying
//        // content for our layout.
//        content = content,
//        // on below line we are adding modifier.
//        modifier = modifier
//    ) { measurable, constraints ->
//        // on below line we are creating a variable for our column width.
//        val columnWidth = (constraints.maxWidth / numColumns)
//
//        // on the below line we are creating and initializing our items constraint widget.
//        val itemConstraints = constraints.copy(maxWidth = columnWidth)
//
//        // on below line we are creating and initializing our column height
//        val columnHeights = IntArray(numColumns) { 0 }
//
//        // on below line we are creating and initializing placebles
//        val placeables = measurable.map { measurable ->
//            // inside placeble we are creating
//            // variables as column and placebles.
//            val column = testColumn(columnHeights)
//            val placeable = measurable.measure(itemConstraints)
//
//            // on below line we are increasing our column height/
//            columnHeights[column] += placeable.height
//            placeable
//        }
//
//        // on below line we are creating a variable for
//        // our height and specifying height for it.
//        val height =
//            columnHeights.maxOrNull()?.coerceIn(constraints.minHeight, constraints.maxHeight)
//                ?: constraints.minHeight
//
//        // on below line we are specifying height and width for our layout.
//        layout(
//            width = constraints.maxWidth,
//            height = height
//        ) {
//            // on below line we are creating a variable for column y pointer.
//            val columnYPointers = IntArray(numColumns) { 0 }
//
//            // on below line we are setting x and y for each placeable item
//            placeables.forEach { placeable ->
//                // on below line we are calling test
//                // column method to get our column index
//                val column = testColumn(columnYPointers)
//
//                placeable.place(
//                    x = columnWidth * column,
//                    y = columnYPointers[column]
//                )
//
//                // on below line we are setting
//                // column y pointer and incrementing it.
//                columnYPointers[column] += placeable.height
//            }
//        }
//    }
//}
//
//// on below line we are creating a test column method for setting height.
//private fun testColumn(columnHeights: IntArray): Int {
//    // on below line we are creating a variable for min height.
//    var minHeight = Int.MAX_VALUE
//
//    // on below line we are creating a variable for column index.
//    var columnIndex = 0
//
//    // on below line we are setting column  height for each index.
//    columnHeights.forEachIndexed { index, height ->
//        if (height < minHeight) {
//            minHeight = height
//            columnIndex = index
//        }
//    }
//    // at last we are returning our column index.
//    return columnIndex
//}