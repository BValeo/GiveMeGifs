package me.bvaleo.givemegifs.ui.adapter

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.load.resource.gif.GifDrawable
import me.bvaleo.givemegifs.databinding.ListGifItemBinding
import me.bvaleo.givemegifs.model.ResponseGif

class GifAdapter(private var gifList: MutableList<ResponseGif>) : RecyclerView.Adapter<GifAdapter.ViewHolder>() {

    class ViewHolder(val binding: ListGifItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(gif: ResponseGif) {
            binding.gif = gif
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val bind = ListGifItemBinding.inflate(inflater, parent, false)
        return ViewHolder(bind)
    }

    override fun getItemCount(): Int = gifList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(gifList[position])
    }

    fun getItem(position: Int) = gifList[position]

    fun addData(data: MutableList<ResponseGif>){
        Log.d("AdapterGif", "AddData")
        gifList.addAll(data)
        notifyItemInserted(gifList.size - data.size)
    }

    fun clearData() {
        gifList.clear()
        notifyDataSetChanged()
    }

    fun saveData() = gifList
}