package com.example.tickety

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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.tickety.ui.theme.ButtonBackground
import com.example.tickety.ui.theme.ChipBorder
import com.example.tickety.ui.theme.IconTint

@Composable
fun bookingScreen(
    navController: NavController
) {
    bookingScreenContent { navController.navigateUp() }
}

@Composable
fun bookingScreenContent(onClickClose: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        seatsContent(
            modifier = Modifier.align(Alignment.TopCenter),
            onClickClose = onClickClose
        )
        bottomSheetContent(
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
fun seatsContent(
    modifier: Modifier = Modifier,
    onClickClose: () -> Unit
) {

    val image =
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQOAVQdear9IJr4dgVgzH0rOVZt-dV6T6dc6A&usqp=CAU"

    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(.9f)
            .background(Color.Black)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            closeScreen(onClickClose = onClickClose)
        }

        seats()
        seatsText()
    }
}

@Composable
fun bottomSheetContent(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(.3f)
            .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
            .background(Color.White)
            .padding(vertical = 24.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        daysList()
        timeList()
        bottomView({})
    }
}

@Composable
fun daysList() {
    var selectedIndex by remember {
        mutableStateOf(-1)
    }
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(count = 30) {
            daysItem(
                index = it,
                selected = selectedIndex,
                onClick = { selectedIndex = it })
        }
    }
}

@Composable
fun timeList() {
    var selectedIndex by remember {
        mutableStateOf(-1)
    }
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(count = 30) {
            timeItem(
                index = it,
                selected = selectedIndex
            ) { selectedIndex = it }
        }
    }
}

@Composable
fun daysItem(onClick: () -> Unit, index: Int, selected: Int) {
    val isSelected = index == selected
    Column(
        modifier = Modifier
            .border(
                width = 1.dp,
                color = ChipBorder,
                shape = RoundedCornerShape(8.dp)
            )
            .clip(RoundedCornerShape(8.dp))
            .background(if (isSelected) ButtonBackground else Color.Transparent)
            .clickable(onClick = onClick)
            .padding(vertical = 4.dp, horizontal = 8.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = (index + 1).toString(),
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            color = if (isSelected) Color.White else Color.Black
        )
        Text(
            text = getDayOfWeek(index),
            fontSize = 12.sp,
            color = Color.Blue
        )
    }
}

private fun getDayOfWeek(index: Int): String {
    return when (index % 7) {
        0 -> "Sun"
        1 -> "Mon"
        2 -> "Tue"
        3 -> "Wed"
        4 -> "Thu"
        5 -> "Fri"
        6 -> "Sat"
        else -> ""
    }
}


@Composable
@Preview
fun timeItem(index: Int, selected: Int, onClick: () -> Unit) {
    val selected = index == selected
    Column(
        modifier = Modifier
            .border(
                width = 1.dp,
                color = ChipBorder,
                shape = RoundedCornerShape(16.dp)
            )
            .clip(RoundedCornerShape(16.dp))
            .clickable(onClick = onClick)
            .background(if (selected) ButtonBackground else Color.Transparent)
            .padding(8.dp)
    ) {
        Text(
            text = "10:30",
            fontSize = 14.sp,
            color = if (selected) Color.White else Color.Black
        )
    }
}

@Composable
@Preview
fun bottomView(onClickBooking: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column() {
            Text(
                text = "$100.00",
                fontSize = 16.sp
            )
            Text(
                text = "4 tickets",
                fontSize = 12.sp,
                color = Color.Blue
            )
        }

        bookingButton("Buy tickets", onClickBooking)
    }
}

@Composable
@Preview
fun seats() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        columnsOfSeats(rotation = 8f)
        columnsOfSeats(rotation = 0f, modifier = Modifier.padding(vertical = 8.dp))
        columnsOfSeats(rotation = -8f)
    }
}

@Composable
fun columnsOfSeats(
    modifier: Modifier = Modifier,
    rotation: Float
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceAround
    ) {
        seat(modifier = Modifier.rotate(rotation))
        seat(modifier = Modifier.rotate(rotation))
        seat(modifier = Modifier.rotate(rotation))
        seat(modifier = Modifier.rotate(rotation))
        seat(modifier = Modifier.rotate(rotation))
    }
}

@Composable
@Preview
fun seat(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.align(Alignment.Center)
        ) {
            seatItem()
            seatItem()
        }
        Icon(
            modifier = Modifier
                .size(84.dp)
                .align(Alignment.Center),
            painter = painterResource(id = R.drawable.ic_border),
            contentDescription = "",
            tint = Color.White
        )
    }
}

@Composable
fun seatItem() {
    var isSelected by remember {
        mutableStateOf(false)
    }
    Icon(
        modifier = Modifier
            .clickable { isSelected = !isSelected },
        painter = painterResource(id = R.drawable.ic_seat),
        contentDescription = "",
        tint = if (isSelected) ButtonBackground else IconTint
    )
}

@Composable
@Preview
fun seatsText() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        seatsHint(IconTint, "Available")
        seatsHint(Color.Blue, "Taken")
        seatsHint(ButtonBackground, "Selected")
    }
}

@Composable
@Preview
fun seatsHint(
    color: Color = IconTint,
    text: String = "Available"
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(
            modifier = Modifier
                .size(12.dp)
                .clip(CircleShape)
                .background(color)
        )

        Spacer(modifier = Modifier.size(4.dp))
        Text(
            text = text,
            color = ButtonBackground
        )
    }
}