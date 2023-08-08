package com.example.littlepainter.draw

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.littlepainter.databinding.FragmentDrawBinding
import com.example.littlepainter.draw.color.ColorAdapter
import com.example.littlepainter.utils.ColorUtils


class DrawFragment : Fragment() {
    private lateinit var binding:FragmentDrawBinding
    private val mViewModel:DrawViewModel by viewModels()
    private val mAdapter = ColorAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDrawBinding.inflate(inflater,container,false)
        return binding.root
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.drawView.viewModel = mViewModel
        binding.drawView.lifeCycleOwner = viewLifecycleOwner
        binding.toolContainer.viewModel = mViewModel
        binding.progressView.mDrawViewModel = mViewModel

        //配置colorRecyclerView
        binding.colorRecyclerView.apply {
            layoutManager = LinearLayoutManager(context,RecyclerView.HORIZONTAL,false)
            adapter = mAdapter
        }

        //让adapter持有viewModel对象
        mAdapter.mDrawViewModel = mViewModel

        //监听颜色模型类对象
        mViewModel.mColorModels.observe(viewLifecycleOwner){
            //有数据了 数据变化了
            mAdapter.setData(it)
        }
    }

}












