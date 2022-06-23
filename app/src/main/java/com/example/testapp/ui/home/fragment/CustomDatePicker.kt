package com.example.testapp.ui.home.fragment

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.widget.DatePicker
import android.widget.NumberPicker
import java.lang.reflect.Field


internal class CustomDatePicker(context: Context?, attrs: AttributeSet?) :
    DatePicker(context, attrs) {
    init {
        val fields: Array<Field> = DatePicker::class.java.declaredFields
        try {
            val s = arrayOf(
                "January",
                "February",
                "March",
                "April",
                "May",
                "June",
                "July",
                "August",
                "September",
                "October",
                "November",
                "December"
            )
            for (field in fields) {
                field.setAccessible(true)
                if (TextUtils.equals(field.getName(), "mMonthSpinner")) {
                    val monthPicker = field.get(this) as NumberPicker
                    monthPicker.minValue = 0
                    monthPicker.maxValue = 11
                    monthPicker.displayedValues = s
                }
                if (TextUtils.equals(field.getName(), "mShortMonths")) {
                    field.set(this, s)
                }
            }
        } catch (e: Exception) {
            println(e.message)
            e.printStackTrace()
        }
    }
}