package com.example.testapp.ui.home.fragment

import com.example.testapp.databinding.FragmentSatuBinding
import com.example.testapp.utils.base.BaseFragment

class SatuFragment: BaseFragment<FragmentSatuBinding>(FragmentSatuBinding::inflate) {

    override fun initData() {
        super.initData()
        var animal = arrayOf("dog", "cat", "lizard", "turtle", "axolotl")
        binding?.apply {
            npExample.maxValue = 4
            npExample.minValue = 0
            npExample.displayedValues = animal
            npExample.setOnValueChangedListener { picker, oldVal, newVal ->
                print(picker.toString())
            }
        }
    }

}