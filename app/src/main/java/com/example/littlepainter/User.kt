package com.example.littlepainter

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User constructor(val name:String,val age:Int) : Parcelable