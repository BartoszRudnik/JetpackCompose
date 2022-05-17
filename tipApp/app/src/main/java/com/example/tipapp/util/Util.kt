package com.example.tipapp.util

fun calculateTotalPerPerson(
    totalBill: Double,
    splitBy: Int,
    tipPercentage: Float
): Double {
    var bill = totalBill + (totalBill * tipPercentage)
    bill /= splitBy

    return bill
}