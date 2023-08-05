package com.example.littlepainter.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.littlepainter.R
import com.example.littlepainter.data.PictureViewModel
import com.example.littlepainter.databinding.FragmentHomeBinding
class HomeFragment : Fragment() {
    private lateinit var binding:FragmentHomeBinding
    private val viewModel:PictureViewModel by activityViewModels()
    private val mAdapter = PictureAdapter()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //加载假数据
        viewModel.loadData()
        //观察数据变化
        viewModel.models.observe(viewLifecycleOwner){
            mAdapter.setData(it)
        }

        //配置RecyclerView
        binding.recyclerView.apply {
            layoutManager = ScaleLayoutManager(requireContext())
            adapter = mAdapter
            PagerSnapHelper().attachToRecyclerView(this)
        }

        //add按钮点击事件
        binding.ivAdd.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_drawFragment)
        }
    }
}
