package com.example.myapplication.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.myapplication.components.FABContent
import com.example.myapplication.components.HomeScreenTopBar
import com.example.myapplication.components.TitleSection
import com.example.myapplication.model.Book
import com.example.myapplication.navigation.ReaderScreens
import com.google.firebase.auth.FirebaseAuth

@Composable
fun ReaderHomeScreen(navController: NavController) {
    Scaffold(topBar = {
        HomeScreenTopBar(navController = navController, title = "A. Reader")
    }, floatingActionButton = {
        FABContent {
            navController.navigate(ReaderScreens.BookSearchScreen.name)
        }
    }) {
        Surface(modifier = Modifier.fillMaxSize()) {
            HomeContent(navController = navController)
        }
    }
}

@Composable
fun HomeContent(navController: NavController) {
    val currentUserName = FirebaseAuth.getInstance().currentUser?.email!!.split("@")[0]

    Column(modifier = Modifier.padding(8.dp), verticalArrangement = Arrangement.Top) {
        Row(modifier = Modifier.align(alignment = Alignment.Start)) {
            TitleSection(label = "Your reading \n" + " activity right now")
            Spacer(modifier = Modifier.fillMaxWidth(0.7f))
            Column {
                Icon(
                    imageVector = Icons.Filled.AccountCircle,
                    contentDescription = "account icon",
                    modifier = Modifier
                        .clickable {
                            navController.navigate(ReaderScreens.StatsScreen.name)
                        }
                        .size(50.dp), tint = MaterialTheme.colors.secondaryVariant
                )
                Text(
                    text = currentUserName,
                    modifier = Modifier.padding(4.dp),
                    style = MaterialTheme.typography.overline,
                    color = Color.Red,
                    fontSize = 14.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Clip
                )
                Divider()
            }
        }
        ReadingRightNowArea(books = listOf(), navController = navController)
        TitleSection(label = "Reading List")
        BookListArea(listOfBooks = emptyList<Book>(), navController = navController)
    }
}

@Composable
fun BookListArea(listOfBooks: List<Book>, navController: NavController) {
    HorizontalScrollableComponent(listOfBooks) {}
}

@Composable
fun HorizontalScrollableComponent(listOfBooks: List<Book>, onCardPressed: (String) -> Unit = {}) {
    val scrollState = rememberScrollState()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(280.dp)
            .horizontalScroll(scrollState)
    ) {
        for (book in listOfBooks) {
            ListCard(book = book) {
                onCardPressed(it)
            }
        }
    }
}

@Composable
fun ReadingRightNowArea(books: List<Book>, navController: NavController) {
    ListCard()
}

@Composable
fun ListCard(
    book: Book = Book("adf", "Running", "Me and you", "hello world"),
    onPressDetails: (String) -> Unit = {}
) {
    val context = LocalContext.current
    val resources = context.resources
    val displayMetrics = resources.displayMetrics

    val screenWidth = displayMetrics.widthPixels / displayMetrics.density
    val spacing = 10.dp

    Card(
        shape = RoundedCornerShape(29.dp),
        backgroundColor = Color.White,
        elevation = 6.dp,
        modifier = Modifier
            .padding(16.dp)
            .height(240.dp)
            .width(200.dp)
            .clickable { onPressDetails.invoke(book.title.toString()) }
    ) {
        Column(
            modifier = Modifier.width(screenWidth.dp - (spacing * 2)),
            horizontalAlignment = Alignment.Start
        ) {
            Row(horizontalArrangement = Arrangement.Center) {
                Image(
                    painter = rememberImagePainter(data = ""),
                    contentDescription = "Book image",
                    modifier = Modifier
                        .height(140.dp)
                        .width(100.dp)
                        .padding(4.dp)
                )

                Spacer(modifier = Modifier.width(50.dp))

                Column(
                    modifier = Modifier.padding(4.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Rounded.FavoriteBorder,
                        contentDescription = "favorite icon",
                        modifier = Modifier.padding(2.dp)
                    )
                    BookRating()
                }
            }
            Text(
                text = book.title.toString(),
                modifier = Modifier.padding(4.dp),
                fontWeight = FontWeight.Bold,
                maxLines = 2, overflow = TextOverflow.Ellipsis
            )
            Text(
                text = book.authors.toString(),
                modifier = Modifier.padding(4.dp),
                style = MaterialTheme.typography.caption
            )

            Row(
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.Bottom
            ) {
                RoundedButton(label = "Reading", radius = 70)
            }
        }
    }
}

@Composable
fun RoundedButton(label: String = "Reading", radius: Int = 30, onPress: () -> Unit = {}) {
    Surface(
        modifier = Modifier.clip(
            RoundedCornerShape(
                bottomEndPercent = radius,
                topStartPercent = radius
            )
        ), color = Color.LightGray
    ) {
        Column(
            modifier = Modifier
                .width(90.dp)
                .heightIn(40.dp)
                .clickable {
                    onPress.invoke()
                },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.button,
                color = Color.White,
                fontSize = 15.sp
            )
        }
    }
}

@Composable
fun BookRating(score: Double = 3.5) {
    Surface(
        modifier = Modifier
            .height(70.dp)
            .padding(4.dp), shape = RoundedCornerShape(56.dp), elevation = 6.dp, color = Color.White
    ) {
        Column(
            modifier = Modifier.padding(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Filled.StarBorder,
                contentDescription = "star icon",
                modifier = Modifier.padding(2.dp)
            )
            Text(text = score.toString(), style = MaterialTheme.typography.subtitle1)
        }
    }
}



