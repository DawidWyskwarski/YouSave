package com.example.yousave

import android.graphics.Color
import androidx.annotation.DrawableRes

data class Category(val name:String,
                    val moneySpent:Double,
                    val transactions:Int,
                    val color:Color,
                    @DrawableRes val image:Int)
