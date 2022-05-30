package com.example.weatherapp.screen

import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.navigation.NavController
import com.example.weatherapp.data.DataOrException
import com.example.weatherapp.model.Weather
import java.lang.Exception

@Composable
fun WeatherMainScreen(navController: NavController, viewModel: MainViewModel) {
    ShowData(viewModel)
}

@Composable
fun ShowData(mainViewModel: MainViewModel) {
    val weatherData =
        produceState<DataOrException<Weather, Boolean, Exception>>(
            initialValue = DataOrException(
                loading = true
            )
        ) {
            value = mainViewModel.getWeatherData(city = "Seattle", units = "imperial")
        }.value

    if (weatherData.loading == true) {
        CircularProgressIndicator()
    } else if (weatherData.data != null) {
        Text(text = weatherData.data!!.city.country)
    }
}