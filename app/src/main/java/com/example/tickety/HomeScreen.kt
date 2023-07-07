package com.example.tickety

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.tickety.ui.theme.BottomIcon
import com.example.tickety.ui.theme.ButtonBackground
import com.example.tickety.ui.theme.ButtonText
import com.example.tickety.ui.theme.ChipBorder
import com.example.tickety.ui.theme.IconTint
import kotlin.math.absoluteValue

@Composable
fun homeScreen(
    navController: NavController
){
    val images = remember {
        mutableStateListOf(
            "https://images.ctfassets.net/usf1vwtuqyxm/1soIBawzwa52bYit498iYy/8b67c35d5116f96c187e8ba27f4cc264/fb3-poster-jude-law-full.jpg",
            "https://images.ctfassets.net/usf1vwtuqyxm/1soIBawzwa52bYit498iYy/8b67c35d5116f96c187e8ba27f4cc264/fb3-poster-jude-law-full.jpg",
            "https://images.ctfassets.net/usf1vwtuqyxm/1soIBawzwa52bYit498iYy/8b67c35d5116f96c187e8ba27f4cc264/fb3-poster-jude-law-full.jpg",
            "https://images.ctfassets.net/usf1vwtuqyxm/1soIBawzwa52bYit498iYy/8b67c35d5116f96c187e8ba27f4cc264/fb3-poster-jude-law-full.jpg",
            "https://images.ctfassets.net/usf1vwtuqyxm/1soIBawzwa52bYit498iYy/8b67c35d5116f96c187e8ba27f4cc264/fb3-poster-jude-law-full.jpg"
        )
    }
    homeContent(
        images
    ) { navController.navigate(AppDestination.MovieDetailsScreen) }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun homeContent(images: SnapshotStateList<String>, onClickMovie: () -> Unit) {
    val pagerState = rememberPagerState(initialPage = 1)
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        backgroundImage(images = images.toList(), pagerState = pagerState)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(.9f)
                .padding(vertical = 16.dp),
            verticalArrangement = Arrangement.SpaceAround
        ) {
            dataChips()
            moviesPager(
                onClick = onClickMovie,
                images = images,
                pagerState
            )
            timeText(text = "2h 33m")
            Text(
                text = "Fantastic Beasts: The Secrets of Dumbledore",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                textAlign = TextAlign.Center,
                fontSize = 30.sp
            )
            categoriesChips()
        }
        bottomBar(modifier = Modifier.align(Alignment.BottomCenter))
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun backgroundImage(images: List<String>, pagerState: PagerState){
    var page by remember {
        mutableStateOf(pagerState.currentPage)
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(.5f)
    ){
        Image(
            modifier = Modifier
                .fillMaxSize()
                .blur(40.dp),
            painter = rememberAsyncImagePainter(model = images[page]),
            contentDescription ="image",
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(brush = Brush.verticalGradient(listOf(Color.Transparent, Color.White)))
        ){

        }
    }
}

@Preview
@Composable
fun dataChips() {
    var isSecondSelected by remember {
        mutableStateOf(false)
    }
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = Modifier
                .border(
                    width = 2.dp,
                    color = ButtonBackground,
                    shape = RoundedCornerShape(16.dp)
                )
                .clip(RoundedCornerShape(16.dp))
                .background(if (!isSecondSelected) ButtonBackground else Color.Transparent)
                .clickable { isSecondSelected = false }
                .padding(8.dp),
            text = "Now Showing",
            fontSize = 14.sp,
            color = ButtonText
        )
        Spacer(modifier = Modifier.size(4.dp))
        Text(
            modifier = Modifier
                .border(
                    width = 1.dp,
                    color = ButtonBackground,
                    shape = RoundedCornerShape(16.dp)
                )
                .clip(RoundedCornerShape(16.dp))
                .background(if (isSecondSelected) ButtonBackground else Color.Transparent)
                .clickable { isSecondSelected = true }
                .padding(vertical = 8.dp, horizontal = 12.dp),
            text = "Coming Soon",
            fontSize = 14.sp,
            color = ButtonText,
            textAlign = TextAlign.Center
        )

    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun moviesPager(onClick : () -> Unit, images: List<String>, pagerState: PagerState) {
    val pagerState = rememberPagerState()
    HorizontalPager(
        pageCount = images.size,
        state = pagerState,
        contentPadding = PaddingValues(horizontal = 32.dp, vertical = 32.dp),
        pageSpacing = 16.dp
    ) { page ->
        Image(
            painter = rememberAsyncImagePainter(model = images[page]), contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(.7f)
                .graphicsLayer {
                    val pageOffset = (
                            (pagerState.currentPage - page) + pagerState
                                .currentPageOffsetFraction
                            ).absoluteValue

                    val transformation = lerp(
                        start = 0.9f,
                        stop = 1f,
                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                    )
                    alpha = transformation
                    scaleY = transformation
                }
                .clip(RoundedCornerShape(32.dp))
                .clickable(onClick = onClick)
        )
    }
}

@Composable
@Preview
fun timeText(text: String = "2h 33m") {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(16.dp),
            painter = painterResource(R.drawable.clock_circle),
            contentDescription = "close Screen",
            tint = IconTint
        )
        Spacer(modifier = Modifier.size(4.dp))
        Text(
            text = text,
            fontSize = 14.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
@Preview
fun categoriesChips() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        categoryChip(text = "Fantasy")
        Spacer(modifier = Modifier.size(4.dp))
        categoryChip(text = "Adventure")
    }
}

@Composable
fun categoryChip(text: String) {
    Text(
        modifier = Modifier
            .border(
                width = 2.dp,
                color = ChipBorder,
                shape = RoundedCornerShape(16.dp)
            )
            .clip(RoundedCornerShape(16.dp))
            .padding(vertical = 8.dp, horizontal = 12.dp),
        text = text,
        fontSize = 14.sp,
        color = Color.Black
    )
}

@Composable
@Preview
fun bottomBar(
    modifier: Modifier = Modifier
) {

    var selectedItem by remember {
        mutableStateOf(1)
    }
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Icon(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .clickable { selectedItem = 1 }
                .background(if (selectedItem == 1) ButtonBackground else Color.Transparent)
                .padding(8.dp),
            painter = painterResource(id = R.drawable.ic_movies),
            contentDescription = "Home",
            tint = if (selectedItem == 1) IconTint else BottomIcon
        )

        Icon(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .clickable { selectedItem = 2 }
                .background(if (selectedItem == 2) ButtonBackground else Color.Transparent)
                .padding(8.dp),
            painter = painterResource(id = R.drawable.ic_search),
            contentDescription = "Home",
            tint = if (selectedItem == 2) IconTint else BottomIcon
        )

        Icon(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .clickable { selectedItem = 3 }
                .background(if (selectedItem == 3) ButtonBackground else Color.Transparent)
                .padding(8.dp),
            painter = painterResource(id = R.drawable.ic_ticket),
            contentDescription = "Home",
            tint = if (selectedItem == 3) IconTint else BottomIcon
        )

        Icon(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .clickable { selectedItem = 4 }
                .background(if (selectedItem == 4) ButtonBackground else Color.Transparent)
                .padding(8.dp),
            painter = painterResource(id = R.drawable.ic_profile),
            contentDescription = "Home",
            tint = if (selectedItem == 4) IconTint else BottomIcon
        )

    }
}