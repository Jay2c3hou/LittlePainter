package com.example.littlepainter.guide

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.example.littlepainter.databinding.FragmentGuideBinding


class GuideFragment : Fragment() {
    private lateinit var binding: FragmentGuideBinding

    @SuppressLint("SuspiciousIndentation")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGuideBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //配置ViewPager的adapter
        binding.viewpager.adapter = GuideAdapter(
            listOf(
                GuideOneFragment(), GuideTwoFragment(), GuideThreeFragment(), GuideFourFragment(),
            ), parentFragmentManager, lifecycle
        )
        binding.viewpager.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                binding.indicatorView.select(position)
            }
        })
        binding.indicatorView.addPagerChangeCallBack = { page ->
            if (binding.viewpager.currentItem != page) {
                binding.viewpager.setCurrentItem(page, true)
            }
        }
    }
}