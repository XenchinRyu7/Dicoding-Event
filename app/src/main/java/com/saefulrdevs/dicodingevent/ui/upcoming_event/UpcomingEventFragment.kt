package com.saefulrdevs.dicodingevent.ui.upcoming_event

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.saefulrdevs.dicodingevent.R
import com.saefulrdevs.dicodingevent.data.response.ListEventsItem
import com.saefulrdevs.dicodingevent.databinding.FragmentUpcomingEventBinding
import com.saefulrdevs.dicodingevent.utils.UiHandler.handleError
import com.saefulrdevs.dicodingevent.utils.UiHandler.showLoading
import com.saefulrdevs.dicodingevent.viewmodel.AdapterVerticalEvent
import com.saefulrdevs.dicodingevent.viewmodel.MainViewModel

class UpcomingEventFragment : Fragment() {

    private var _binding: FragmentUpcomingEventBinding? = null
    private val binding get() = _binding!!

    private val mainViewModel by viewModels<MainViewModel>()
    private lateinit var adapterVertical: AdapterVerticalEvent

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpcomingEventBinding.inflate(inflater, container, false)

        setupRecyclerView()
        setupAdapter()
        observeViewModel()

        return binding.root
    }

    private fun setupRecyclerView() {
        val verticalLayout = LinearLayoutManager(requireContext())
        binding.rvUpcomingEvent.layoutManager = verticalLayout
        val itemUpcomingEventDecoration =
            DividerItemDecoration(requireContext(), verticalLayout.orientation)
        binding.rvUpcomingEvent.addItemDecoration(itemUpcomingEventDecoration)
    }

    private fun setupAdapter() {
        adapterVertical = AdapterVerticalEvent { eventId ->
            val bundle = Bundle().apply {
                eventId?.let { putInt("eventId", it) }
            }
            findNavController().navigate(R.id.navigation_detail, bundle)
        }

        binding.rvUpcomingEvent.adapter = adapterVertical
    }

    private fun observeViewModel() {
        mainViewModel.upcomingEvent.observe(viewLifecycleOwner) { listItems ->
            setUpcomingEvent(listItems)
            mainViewModel.clearErrorMessage()
        }

        mainViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it, binding.progressBar)
        }

        mainViewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            handleError(
                isError = errorMessage != null,
                message = errorMessage,
                errorTextView = binding.tvErrorMessage,
                refreshButton = binding.btnRefresh,
                recyclerView = binding.rvUpcomingEvent
            ) {
                mainViewModel.getUpcomingEvent()
                mainViewModel.getFinishedEvent()
            }
        }
    }

    private fun setUpcomingEvent(listUpcomingEvent: List<ListEventsItem>) {
        adapterVertical.submitList(listUpcomingEvent)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
