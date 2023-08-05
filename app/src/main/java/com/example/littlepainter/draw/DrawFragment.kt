package com.example.littlepainter.draw

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.littlepainter.databinding.FragmentDrawBinding
import com.example.littlepainter.utils.ColorUtils
import com.example.littlepainter.utils.dp2px


class DrawFragment : Fragment() {
    private lateinit var binding:FragmentDrawBinding
    private val mViewModel:DrawViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDrawBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.drawView.viewModel = mViewModel
        binding.drawView.lifeCycleOwner = viewLifecycleOwner
        binding.toolContainer.viewModel = mViewModel

        //配置colorRecyclerView
        binding.colorRecyclerView.apply {
            layoutManager = LinearLayoutManager(context,RecyclerView.HORIZONTAL,false)
            val mAdapter = ColorAdapter()
            adapter = mAdapter
            mAdapter.setData(ColorUtils.getRandomColor(100))
        }

        mViewModel.mToolType.observe(viewLifecycleOwner){
            if (it == ToolType.Palette){
                binding.colorRecyclerView.animate().translationY(requireActivity().dp2px(-178).toFloat())
            }
        }
    }

}