package com.example.littlepainter.guide

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.littlepainter.databinding.FragmentGuideBinding


class GuideFragment : Fragment() {
private lateinit var binding:FragmentGuideBinding
    @SuppressLint("SuspiciousIndentation")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
      binding = FragmentGuideBinding.inflate(inflater,container,false)
        return binding.root
    }

}