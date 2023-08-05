package com.example.littlepainter.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.littlepainter.data.Picture
import com.example.littlepainter.databinding.LayoutPictureItemBinding

class PictureAdapter : RecyclerView.Adapter<PictureAdapter.MyViewHolder>() {
    private var mPictures = emptyList<Picture>()

    override fun getItemCount(): Int {
        return mPictures.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = LayoutPictureItemBinding.inflate(inflater, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(mPictures[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newData: List<Picture>) {
        mPictures = newData
        //自动刷新以展示新的数据
        //一般来说，在适配器中当数据集发生变化时，都需要调用 notifyDataSetChanged() 来更新视图。
        notifyDataSetChanged()
    }

    //定义ViewHolder
    class MyViewHolder(private val binding: LayoutPictureItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(pictureModel: Picture) {
            binding.imageView.setImageResource(pictureModel.pic)
        }
    }
}