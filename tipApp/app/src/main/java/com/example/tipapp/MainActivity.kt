package com.example.tipapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tipapp.components.InputField
import com.example.tipapp.components.RoundedIconButton
import com.example.tipapp.ui.theme.TipAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp {
                MainContent()
            }
        }
    }
}

@Composable
fun MyApp(content: @Composable () -> Unit) {
    TipAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            content()
        }
    }
}

@Composable
fun MainContent() {
    BillForm() { billAmount ->
        {}
    }
}

@Composable
@OptIn(ExperimentalComposeUiApi::class)
fun BillForm(modifier: Modifier = Modifier, onValChange: (String) -> Unit = {}) {
    val totalBillState = remember {
        mutableStateOf("")
    }
    val validState = remember(totalBillState.value) {
        totalBillState.value.trim().isNotEmpty()
    }
    val keyboardController = LocalSoftwareKeyboardController.current
    val numberOfPeople = remember {
        mutableStateOf(1)
    }
    val tipAmount = remember {
        mutableStateOf(0.0)
    }
    val tipPercentage = remember {
        mutableStateOf(0.0f)
    }

    Column {
        TopHeader()
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(corner = CornerSize(8.dp)),
            border = BorderStroke(2.dp, Color.LightGray)
        ) {
            Column() {
                InputField(
                    valueState = totalBillState,
                    labelId = "Enter bill",
                    enabled = true,
                    isSingleLine = true,
                    onAction = KeyboardActions {
                        if (!validState) {
                            return@KeyboardActions
                        }
                        onValChange(totalBillState.value.trim())

                        keyboardController?.hide()
                    }
                )

                if (validState) {
                    Column() {
                        Row(
                            modifier = Modifier.padding(4.dp),
                            horizontalArrangement = Arrangement.Start
                        ) {
                            Text(
                                text = "Split",
                                style = MaterialTheme.typography.h4,
                                modifier = Modifier.align(alignment = Alignment.CenterVertically)
                            )
                            Spacer(modifier = Modifier.width(120.dp))
                            Row(horizontalArrangement = Arrangement.End) {
                                RoundedIconButton(
                                    imageVector = Icons.Default.Remove,
                                    onClickEvent = { if (numberOfPeople.value > 1) numberOfPeople.value -= 1 },
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Text(
                                    text = numberOfPeople.value.toString(),
                                    style = MaterialTheme.typography.h4
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                RoundedIconButton(
                                    imageVector = Icons.Default.Add,
                                    onClickEvent = { numberOfPeople.value += 1 })
                            }
                        }
                        Row(
                            modifier = Modifier.padding(4.dp),
                            horizontalArrangement = Arrangement.Start
                        ) {
                            val tipFormatted = "%.2f".format(tipAmount.value)

                            Text(
                                text = "Tip",
                                modifier = Modifier.align(alignment = Alignment.CenterVertically),
                                style = MaterialTheme.typography.h4
                            )
                            Spacer(modifier = Modifier.width(200.dp))
                            Text(
                                text = "$$tipFormatted",
                                modifier = Modifier.align(alignment = Alignment.CenterVertically),
                                style = MaterialTheme.typography.h4
                            )
                        }
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.padding(4.dp)
                        ) {
                            val tipPercentageFormatted = "%.0f".format(tipPercentage.value * 100)

                            Spacer(modifier = Modifier.height(20.dp))
                            Text(
                                text = "$tipPercentageFormatted%",
                                style = MaterialTheme.typography.h4
                            )
                            Spacer(modifier = Modifier.height(20.dp))
                            Slider(
                                modifier = Modifier.padding(8.dp),
                                steps = 5,
                                value = tipPercentage.value,
                                onValueChange = { newValue ->
                                    tipPercentage.value = newValue
                                    tipAmount.value =
                                        (tipPercentage.value * totalBillState.value.trim()
                                            .toInt()).toDouble()
                                })
                        }

                    }

                } else {
                    Box {}
                }
            }
        }
    }
}

@Composable
fun TopHeader(total: Double = 0.0) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(8.dp)
            .clip(shape = RoundedCornerShape(corner = CornerSize(12.dp))),
        color = Color.Green
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            val totalFormatted = "%.2f".format(total)

            Text(text = "Total per person", style = MaterialTheme.typography.h5)
            Text(
                text = "$$totalFormatted",
                style = MaterialTheme.typography.h4,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
