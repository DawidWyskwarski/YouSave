package com.example.yousave.home

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.DrawableRes

data class Category(val name:String, val moneySpent:Double, val transactions:Int, val color:Int, @DrawableRes val image:Int): Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readDouble(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeDouble(moneySpent)
        parcel.writeInt(transactions)
        parcel.writeInt(color)
        parcel.writeInt(image)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Category> {
        override fun createFromParcel(parcel: Parcel): Category {
            return Category(parcel)
        }

        override fun newArray(size: Int): Array<Category?> {
            return arrayOfNulls(size)
        }
    }
}
