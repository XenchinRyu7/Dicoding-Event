package com.saefulrdevs.dicodingevent.ui.upcoming_event

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.saefulrdevs.dicodingevent.data.response.ListEventsItem
import com.saefulrdevs.dicodingevent.databinding.FragmentUpcomingEventBinding
import com.saefulrdevs.dicodingevent.viewmodel.AdapterHorizontalEvent
import com.saefulrdevs.dicodingevent.viewmodel.AdapterVerticalEvent
import com.saefulrdevs.dicodingevent.viewmodel.MainViewModel

class UpcomingEventFragment : Fragment() {

    private var _binding: FragmentUpcomingEventBinding? = null
    private val binding get() = _binding!!

    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpcomingEventBinding.inflate(inflater, container, false)

        val verticalLayout = LinearLayoutManager(requireContext())
        binding.rvUpcomingEvent.layoutManager = verticalLayout
        val itemUpcomingEventDecoration =
            DividerItemDecoration(requireContext(), verticalLayout.orientation)
        binding.rvUpcomingEvent.addItemDecoration(itemUpcomingEventDecoration)

        mainViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        mainViewModel.upcomingEvent.observe(viewLifecycleOwner) { listItems ->
            setUpcomingEvent(listItems)
        }

        return binding.root
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun setUpcomingEvent(listUpcomingEvent: List<ListEventsItem>) {
        val adapter = AdapterVerticalEvent()
        adapter.submitList(listUpcomingEvent)
        binding.rvUpcomingEvent.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}