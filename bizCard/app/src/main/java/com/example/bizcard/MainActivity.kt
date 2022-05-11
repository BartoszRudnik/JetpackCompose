package com.example.bizcard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.bizcard.ui.theme.BizCardTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BizCardTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    CreateBizCard()
                }
            }
        }
    }
}

@Composable
fun CreateBizCard() {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        Card(
            modifier = Modifier
                .width(200.dp)
                .height(390.dp)
                .padding(12.dp),
            elevation = 4.dp,
            shape = RoundedCornerShape(corner = CornerSize(15.dp))
        ) {
            Column(
                modifier = Modifier.height(300.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CreateImageProfile(150.dp)
                Divider(thickness = 2.dp)
                CreateInfoSection()
                CreatePortfolioButton()
            }
        }
    }
}

@Composable
private fun CreatePortfolioButton() {
    val buttonClickedState = remember {
        mutableStateOf(false)
    }
    Surface {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = {
                buttonClickedState.value = !buttonClickedState.value
            }, modifier = Modifier.padding(8.dp)) {
                Text(text = "Portfolio", style = MaterialTheme.typography.button)
            }
            if (buttonClickedState.value) {
                Content()
            } else {
                Box {

                }
            }
        }
    }
}

@Composable
private fun CreateInfoSection() {
    Surface {
        Column(modifier = Modifier.padding(5.dp)) {
            Text(
                text = "Jan Kowalski",
                color = Color.Blue,
                style = MaterialTheme.typography.h4,
                modifier = Modifier.padding(4.dp)
            )
            Text(
                text = "Android Compose Programmer",
                style = MaterialTheme.typography.h5,
                modifier = Modifier.padding(4.dp),
            )
            Text(
                text = "jan@kowalski.com",
                style = MaterialTheme.typography.h6,
                modifier = Modifier.padding(4.dp),
            )
        }
    }
}

@Composable
private fun Content() {
    Box(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .padding(5.dp)
    ) {
        Surface(
            modifier = Modifier
                .padding(3.dp)
                .fillMaxWidth()
                .fillMaxHeight(),
            shape = RoundedCornerShape(corner = CornerSize(6.dp)),
            border = BorderStroke(2.dp, Color.LightGray)
        ) {
            Portfolio(data = listOf("Project 1", "Project 2", "Project 3"))
        }
    }
}

@Composable
fun Portfolio(data: List<String>) {
    LazyColumn(horizontalAlignment = Alignment.CenterHorizontally) {
        items(data) { item ->
            Card(
                modifier = Modifier
                    .padding(12.dp)
                    .fillMaxWidth(), shape = RectangleShape
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier.padding(12.dp)
                ) {
                    CreateImageProfile(size = 120.dp)
                    Column(verticalArrangement = Arrangement.SpaceAround) {
                        Text(item)
                        Text(text = "Extra project")
                    }
                }
            }
        }
    }
}

@Composable
private fun CreateImageProfile(size: Dp) {
    Surface(
        modifier = Modifier
            .size(size)
            .padding(5.dp),
        shape = CircleShape,
        border = BorderStroke(0.5.dp, Color.LightGray),
        elevation = 4.dp,
        color = MaterialTheme.colors.onSurface.copy(alpha = 0.5f)
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_baseline_person_24),
            contentDescription = "Profile picture", modifier = Modifier.size(135.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BizCardTheme {
        CreateBizCard()
    }
}