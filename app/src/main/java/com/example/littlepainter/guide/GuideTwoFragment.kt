package com.example.littlepainter.guide

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.littlepainter.R
import com.example.littlepainter.databinding.FragmentGuideOneBinding
import com.example.littlepainter.databinding.FragmentGuideTwoBinding
import com.example.littlepainter.utils.AnimUtils

class GuideTwoFragment : Fragment() {
    private lateinit var binding: FragmentGuideTwoBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentGuideTwoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        AnimUtils.addRotateAndAlphaAnimation(binding.tvTitle, onEnd = {})
        AnimUtils.addRotateAndAlphaAnimation(binding.tvDesc, onEnd = {})
    }

}