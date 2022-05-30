package com.example.weatherapp.screen

import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.weatherapp.data.DataOrException
import com.example.weatherapp.model.Weather
import com.example.weatherapp.widgets.WeatherAppBar
import java.lang.Exception

@Composable
fun WeatherMainScreen(navController: NavController, viewModel: MainViewModel) {
    val weatherData =
        produceState<DataOrException<Weather, Boolean, Exception>>(
            initialValue = DataOrException(
                loading = true
            )
        ) {
            value = viewModel.getWeatherData(city = "Seattle", units = "imperial")
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
            elevation = 5.dp, icon = Icons.Default.ArrowBack
        ) {

        }
    }) {
        MainContent(weatherData = weatherData)
    }
}

@Composable
fun MainContent(weatherData: Weather) {
    Text(text = weatherData.city.name)
}