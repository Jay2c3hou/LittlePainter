package com.example.littlepainter.draw

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.littlepainter.R
import com.example.littlepainter.databinding.LayoutColorItemBinding

class ColorAdapter: RecyclerView.Adapter<ColorAdapter.MyViewHolder>() {
    private var mColors:List<Int> = emptyList()

    class MyViewHolder(val binding:LayoutColorItemBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(color:Int){
            binding.colorView.setBackgroundColor(color)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater =  LayoutInflater.from(parent.context)
        val binding = LayoutColorItemBinding.inflate(inflater,parent,false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return  mColors.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(mColors[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newColors:List<Int>){
        mColors = newColors
        notifyDataSetChanged()
    }
}