package com.example.littlepainter.draw.color

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.littlepainter.databinding.LayoutColorItemBinding
import com.example.littlepainter.draw.DrawViewModel

class ColorAdapter : RecyclerView.Adapter<ColorAdapter.MyViewHolder>() {
    private var mColors: List<ColorModel> = emptyList()
    private var mCurrentColorIndex = -1
    var mDrawViewModel: DrawViewModel? = null

    class MyViewHolder(val binding: LayoutColorItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: ColorModel, callback: (ColorModel) -> Unit) {
            binding.colorView.setBackgroundColor(model.color)
            binding.viewBorder.visibility = if (model.isSelect) View.VISIBLE else View.INVISIBLE

            binding.colorView.setOnClickListener {
                binding.viewBorder.visibility = View.VISIBLE
                callback(model)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = LayoutColorItemBinding.inflate(inflater, parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return mColors.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(mColors[position]) { model ->
            //获取当前点击Item的索引值
            val index = mColors.indexOf(model)
            if (mCurrentColorIndex == -1) {
                mCurrentColorIndex = index
            } else if (mCurrentColorIndex != index) {
                //将之前的状态该回去
                mColors[mCurrentColorIndex].isSelect = false
                notifyItemChanged(mCurrentColorIndex)

                //改变现在的状态
                mColors[index].isSelect = true
                notifyItemChanged(index)
                //记录当前选中的索引
                mCurrentColorIndex = index
            }
            mDrawViewModel?.let {
                it.setColor(model.color)
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newColors: List<ColorModel>) {
        mColors = newColors
        notifyDataSetChanged()
    }
}