package com.example.bangga_bangga

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.FloatRange
import androidx.core.graphics.toColorInt
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.bangga_bangga.databinding.ActivityHomeBinding
import com.example.bangga_bangga.databinding.BannerItemBinding
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.bangga_bangga.databinding.ActivityRegisterBinding

class BannerAdapter(private val bannerList: Array<Array<String>>, private var listener: OnBannerClickListener) : RecyclerView.Adapter<BannerAdapter.BannerViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : BannerViewHolder {
        val binding = BannerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BannerViewHolder(binding)
    }
    override fun getItemCount(): Int = Int.MAX_VALUE
    override fun onBindViewHolder(holder: BannerViewHolder, position: Int) {
        val (text, color) = bannerList[position % bannerList.size]
        holder.binding.bannerText.text = text
        holder.binding.bannerText.setBackgroundColor(color.toColorInt())
        holder.binding.root.setOnClickListener{
            listener.onBannerClick(position)
        }
    }

    fun setOnBannerClickListener(listener: OnBannerClickListener) {
        this.listener = listener
    }
    inner class BannerViewHolder(val binding: BannerItemBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                listener.onBannerClick(adapterPosition % bannerList.size)
            }
        }
    }
}