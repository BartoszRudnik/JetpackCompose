package com.example.weatherapp.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.weatherapp.R
import com.example.weatherapp.data.DataOrException
import com.example.weatherapp.model.Weather
import com.example.weatherapp.model.WeatherItem
import com.example.weatherapp.navigation.WeatherScreens
import com.example.weatherapp.util.formatDate
import com.example.weatherapp.util.formatDateTime
import com.example.weatherapp.util.formatDecimals
import com.example.weatherapp.widgets.WeatherAppBar

@Composable
fun WeatherMainScreen(navController: NavController, viewModel: MainViewModel, city: String) {
    val weatherData =
        produceState<DataOrException<Weather, Boolean, Exception>>(
            initialValue = DataOrException(
                loading = true
            )
        ) {
            value = viewModel.getWeatherData(city = city, units = "imperial")
        }.value

    if (weatherData.loading == true) {
        CircularProgressIndicator()
    } else if (weatherData.data != null) {
        MainScaffold(weatherData.data!!, navController)
    }


}

@Composable
fun MainScaffold(weatherData: Weather, navController: NavController) {
    Scaffold(topBar = {
        WeatherAppBar(
            navController = navController,
            title = weatherData.city.name + ", ${weatherData.city.country}",
            elevation = 5.dp, icon = Icons.Default.ArrowBack,
            onAddActionClicked = {
                navController.navigate(WeatherScreens.SearchScreen.name)
            }
        ) {

        }
    }) {
        MainContent(weatherData = weatherData)
    }
}

@Composable
fun MainContent(weatherData: Weather) {
    val imageUrl = "https://openweathermap.org/img/wn/${weatherData.list[0].weather[0].icon}.png"

    Column(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = formatDate(weatherData.list[0].dt),
            style = MaterialTheme.typography.caption,
            color = MaterialTheme.colors.onSecondary, fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(6.dp)
        )

        Surface(
            modifier = Modifier
                .padding(4.dp)
                .size(200.dp), shape = CircleShape, color = Color.Yellow
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                WeatherStateImage(imageUrl = imageUrl)
                Text(
                    text = formatDecimals(weatherData.list[0].temp.day) + "°",
                    style = MaterialTheme.typography.h4,
                    fontWeight = FontWeight.ExtraBold
                )
                Text(text = weatherData.list[0].weather[0].main, fontStyle = FontStyle.Italic)
            }
        }

        HumidityWindPressureRow(weatherItem = weatherData.list[0])
        Divider()
        SunriseAndSunsetRow(weatherItem = weatherData.list[0])
        Text(
            "This Week",
            style = MaterialTheme.typography.subtitle1,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
        )

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(), color = Color(0XFFEEF1EF),
            shape = RoundedCornerShape(size = 14.dp)
        ) {
            LazyColumn(modifier = Modifier.padding(4.dp), contentPadding = PaddingValues(2.dp)) {
                items(items = weatherData.list) { item: WeatherItem ->
                    WeatherDetailRow(weather = item)
                }
            }
        }
    }
}

@Composable
fun WeatherDetailRow(weather: WeatherItem) {
    val imageUrl = "https://openweathermap.org/img/wn/${weather.weather[0].icon}.png"

    Surface(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth(),
        shape = CircleShape.copy(topEnd = CornerSize(6.dp)),
        color = Color.White
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(formatDate(weather.dt).split(",")[0], modifier = Modifier.padding(4.dp))
            WeatherStateImage(imageUrl = imageUrl)
            Surface(modifier = Modifier.padding(4.dp), shape = CircleShape) {
                Text(
                    weather.weather[0].description,
                    modifier = Modifier.padding(4.dp),
                    style = MaterialTheme.typography.caption
                )
            }
            Text(
                text = weather.temp.max.toString() + "°",
                fontWeight = FontWeight.SemiBold,
                color = Color.Blue.copy(alpha = 0.7f)
            )
            Text(
                text = weather.temp.min.toString() + "°",
                fontWeight = FontWeight.SemiBold,
                color = Color.Gray.copy(alpha = 0.7f)
            )
        }
    }
}

@Composable
fun SunriseAndSunsetRow(weatherItem: WeatherItem) {
    Row(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        SunriseSunsetRowItem(
            value = weatherItem.sunrise,
            iconId = R.drawable.sunrise,
            contentDescription = "Sunrise icon"
        )
        SunriseSunsetRowItem(
            value = weatherItem.sunset,
            iconId = R.drawable.sunset,
            contentDescription = "Sunset icon"
        )
    }
}

@Composable
fun HumidityWindPressureRow(weatherItem: WeatherItem) {
    Row(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        HumidityRowItem(
            iconId = R.drawable.humidity,
            value = weatherItem.humidity,
            contentDescription = "Humidity icon"
        )
        HumidityRowItem(
            iconId = R.drawable.pressure,
            value = weatherItem.pressure,
            contentDescription = "Pressure icon"
        )
        HumidityRowItem(
            iconId = R.drawable.wind,
            value = weatherItem.speed.toInt(),
            contentDescription = "Wind icon"
        )
    }
}

@Composable
private fun SunriseSunsetRowItem(value: Int, iconId: Int, contentDescription: String) {
    Row(modifier = Modifier.padding(4.dp)) {
        Icon(
            painter = painterResource(id = iconId),
            contentDescription = contentDescription,
            modifier = Modifier.size(20.dp)
        )
        Text(text = formatDateTime(value), style = MaterialTheme.typography.caption)
    }
}

@Composable
private fun HumidityRowItem(iconId: Int, value: Int, contentDescription: String) {
    Row(modifier = Modifier.padding(4.dp)) {
        Icon(
            painter = painterResource(id = iconId),
            contentDescription = contentDescription,
            modifier = Modifier.size(20.dp)
        )
        Text(text = value.toString(), style = MaterialTheme.typography.caption)
    }
}

@Composable
fun WeatherStateImage(imageUrl: String) {
    Image(
        painter = rememberImagePainter(imageUrl),
        contentDescription = "Weather state icon",
        modifier = Modifier.size(80.dp)
    )
}