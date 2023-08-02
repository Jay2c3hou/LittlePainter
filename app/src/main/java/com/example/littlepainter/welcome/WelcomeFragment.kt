package com.example.littlepainter.welcome


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.AnimationSet
import android.view.animation.BounceInterpolator
import android.view.animation.ScaleAnimation
import androidx.navigation.fragment.findNavController
import com.example.littlepainter.R
import com.example.littlepainter.databinding.FragmentWelcomeBinding
import com.example.littlepainter.utils.AnimUtils
import com.example.littlepainter.utils.Constants
import com.example.littlepainter.utils.SPUtils

class WelcomeFragment : Fragment() {
    private lateinit var binding: FragmentWelcomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWelcomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        AnimUtils.addScaleAndAlphaAnimation(
            binding.logo,
            0.4f,
            1f,
            0.1f,
            1f,
            BounceInterpolator(),
            1500,
            onEnd = {
                navigate()
            }
        )
    }

    private fun navigate(){
        //判断用户是不是第一次使用这个APP
        if (SPUtils.defaultUtils(requireContext()).isFirst) {
            findNavController().navigate(R.id.action_welcomeFragment_to_guideFragment)
        }else{
            findNavController().navigate(R.id.action_welcomeFragment_to_homeFragment)
        }
    }
}