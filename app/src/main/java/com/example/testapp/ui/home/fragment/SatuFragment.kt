package com.example.testapp.ui.home.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.testapp.R
import kotlinx.android.synthetic.main.fragment_satu.*

class SatuFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_satu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var animal = arrayOf("dog", "cat", "lizard", "turtle", "axolotl")
        npExample.maxValue = 4
        npExample.minValue = 0
        npExample.displayedValues = animal
        npExample.setOnValueChangedListener { picker, oldVal, newVal ->
            print(picker.toString())
        }
    }
}