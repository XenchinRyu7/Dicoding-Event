package com.saefulrdevs.dicodingevent.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.saefulrdevs.dicodingevent.data.response.ListEventsItem
import com.saefulrdevs.dicodingevent.databinding.FragmentHomeBinding
import com.saefulrdevs.dicodingevent.viewmodel.AdapterHorizontalEvent
import com.saefulrdevs.dicodingevent.viewmodel.AdapterVerticalEvent
import com.saefulrdevs.dicodingevent.viewmodel.MainViewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        val horizontalLayout =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvUpcomingEvent.layoutManager = horizontalLayout
        val itemEventUpcomingDecoration =
            DividerItemDecoration(requireContext(), horizontalLayout.orientation)
        binding.rvUpcomingEvent.addItemDecoration(itemEventUpcomingDecoration)

        val verticalLayout = LinearLayoutManager(requireContext())
        binding.rvFinishedEvent.layoutManager = verticalLayout
        val itemFinishedEventDecoration =
            DividerItemDecoration(requireContext(), verticalLayout.orientation)
        binding.rvFinishedEvent.addItemDecoration(itemFinishedEventDecoration)

        mainViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
        mainViewModel.upcomingEvent.observe(viewLifecycleOwner) { listItems ->
            setUpcomingEvent(listItems)
        }

        mainViewModel.finishedEvent.observe(viewLifecycleOwner) { listItems ->
            setFinishedEvent(listItems)
        }
        return binding.root
    }

    private fun setUpcomingEvent(listUpcomingEvent: List<ListEventsItem>) {
        val limitedList = if (listUpcomingEvent.size > 5) listUpcomingEvent.take(5) else listUpcomingEvent
        val adapter = AdapterHorizontalEvent()
        adapter.submitList(limitedList)
        binding.rvUpcomingEvent.adapter = adapter
    }

    private fun setFinishedEvent(listFinishedEvent: List<ListEventsItem>) {
        val limitedList = if (listFinishedEvent.size > 5) listFinishedEvent.takeLast(5) else listFinishedEvent
        val adapter = AdapterVerticalEvent()
        adapter.submitList(limitedList)
        binding.rvFinishedEvent.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}