package com.saefulrdevs.dicodingevent.viewmodel

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.saefulrdevs.dicodingevent.data.response.ListEventsItem
import com.saefulrdevs.dicodingevent.databinding.CardItemVerticalBinding

class AdapterVerticalEvent :
    ListAdapter<ListEventsItem, AdapterVerticalEvent.VerticalEventViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VerticalEventViewHolder {
        val binding =
            CardItemVerticalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VerticalEventViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VerticalEventViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class VerticalEventViewHolder(private val binding: CardItemVerticalBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(finishedEvent: ListEventsItem) {
            binding.titleEvent.text = finishedEvent.name
            binding.descriptionEvent.text = finishedEvent.summary
            Glide.with(binding.imageEvent.context)
                .load(finishedEvent.imageLogo)
                .into(binding.imageEvent)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListEventsItem>() {
            override fun areItemsTheSame(
                oldItem: ListEventsItem,
                newItem: ListEventsItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: ListEventsItem,
                newItem: ListEventsItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}