package com.example.tickety

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.tickety.ui.theme.ButtonBackground
import com.example.tickety.ui.theme.IconTint

@Composable
fun movieDetailsScreen(
    navController: NavController
) {
    val image =
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQOAVQdear9IJr4dgVgzH0rOVZt-dV6T6dc6A&usqp=CAU"
    movieDetailsContent(
        image,
        onClickBooking = {navController.navigate(AppDestination.BookingScreen)},
        onClickClose = { navController.navigateUp() }
    )
}

@Composable
fun movieDetailsContent(image: String, onClickBooking: () -> Unit, onClickClose: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        movieImage(image = image, modifier = Modifier.align(Alignment.TopCenter))
        movieImageButtons(onClickClose = onClickClose)
        movieDetails(
            modifier = Modifier.align(Alignment.BottomCenter),
            onClickBooking = onClickBooking
        )
    }
}

@Composable
fun movieImage(
    image: String,
    modifier: Modifier = Modifier
) {
    Image(
        painter = rememberAsyncImagePainter(model = image),
        contentDescription = "Movie name",
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(.5f),
        contentScale = ContentScale.Crop
    )
}

@Composable
fun movieDetails(
    modifier: Modifier = Modifier,
    onClickBooking: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(.56f)
            .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
            .background(Color.White),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        movieInfo()
        Text(
            text = "Fantastic Beasts: The Secrets of Dumbledore",
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            textAlign = TextAlign.Center,
            fontSize = 30.sp
        )
        categoriesChips()
        characters()
        Text(
            modifier = Modifier
                .fillMaxWidth(.8f),
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
            text = stringResource(id = com.example.tickety.R.string.fake_string),
            maxLines = 3,
            overflow = TextOverflow.Ellipsis
        )
        bookingButton(
            onClick = onClickBooking
        )
    }
}

@Composable
fun movieImageButtons(onClickClose: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 32.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

           closeScreen(onClickClose = onClickClose)
            timeText2()
        }

        Spacer(modifier = Modifier.fillMaxHeight(.065f))
        Icon(
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
                .background(ButtonBackground)
                .padding(16.dp),
            painter = painterResource(id = com.example.tickety.R.drawable.play),
            contentDescription = "Home",
            tint = IconTint
        )
    }
}

@Composable
fun timeText2(text: String = "2h 33m") {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(ButtonBackground)
            .padding(8.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(16.dp),
            painter = painterResource(com.example.tickety.R.drawable.clock_circle),
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
fun movieInfo() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        infoText(rate = "6.8", title = "IMDb", maxRate = "/10")
        infoText(rate = "63%", title = "Rotten Tomatoes")
        infoText(rate = "4", title = "IGn", maxRate = "/10")
    }
}

@Composable
fun infoText(rate: String, maxRate: String = "", title: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Row {
            Text(text = rate, fontSize = 16.sp)
            Text(text = maxRate, fontSize = 16.sp, color = ButtonBackground)
        }
        Text(text = title, fontSize = 16.sp, color = ButtonBackground)
    }
}

@Composable
fun characters() {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 24.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(count = 8) {
            Image(
                modifier = Modifier
                    .size(70.dp)
                    .clip(CircleShape),
                painter = rememberAsyncImagePainter(model = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQOAVQdear9IJr4dgVgzH0rOVZt-dV6T6dc6A&usqp=CAU"),
                contentDescription = "actor image",
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Composable
fun bookingButton(text: String = "Booking", onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(30.dp))
            .background(ButtonBackground)
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp, horizontal = 12.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(24.dp),
            painter = painterResource(com.example.tickety.R.drawable.ic_ticket),
            contentDescription = "close Screen",
            tint = IconTint
        )
        Spacer(modifier = Modifier.size(8.dp))
        Text(
            text = text,
            fontSize = 14.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun closeScreen(onClickClose : ()-> Unit){
    Icon(
        modifier = Modifier
            .size(36.dp)
            .clip(CircleShape)
            .background(ButtonBackground)
            .clickable(onClick = onClickClose)
            .padding(8.dp),
        painter = painterResource(id = com.example.tickety.R.drawable.close_circle),
        contentDescription = "Home",
        tint = IconTint
    )
}