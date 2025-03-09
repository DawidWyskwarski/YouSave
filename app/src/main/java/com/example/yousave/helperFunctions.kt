package com.example.yousave

import android.icu.text.DecimalFormat

//spaces the amount so it is more readable
// 123456.123 -> 123 456.12 zł
fun formatMoney(amount: Double):String{
    val rounded = DecimalFormat("#.##").format(amount)
    val formatted = rounded.replace(Regex("(\\d)(?=(\\d{3})+(\\.?)\\b)"), "$1 ")

    //TODO add support for many different currencies
    return "$formatted zł"
}