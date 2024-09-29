package com.saefulrdevs.dicodingevent.viewmodel

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import com.saefulrdevs.dicodingevent.data.response.ListEventsItem
import com.saefulrdevs.dicodingevent.databinding.CardItemHorizontalBinding

class AdapterHorizontalEvent :
    ListAdapter<ListEventsItem, AdapterHorizontalEvent.HorizontalEventViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HorizontalEventViewHolder {
        val binding =
            CardItemHorizontalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HorizontalEventViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HorizontalEventViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class HorizontalEventViewHolder(private val binding: CardItemHorizontalBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(upcomingEvent: ListEventsItem) {
            binding.titleEvent.text = upcomingEvent.name
            Glide.with(binding.imageEvent.context)
                .load(upcomingEvent.imageLogo)
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