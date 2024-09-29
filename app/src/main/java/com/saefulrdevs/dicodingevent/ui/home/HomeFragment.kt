package com.saefulrdevs.dicodingevent.ui.home

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
import com.saefulrdevs.dicodingevent.databinding.FragmentHomeBinding
import com.saefulrdevs.dicodingevent.utils.UiHandler.handleError
import com.saefulrdevs.dicodingevent.utils.UiHandler.showLoading
import com.saefulrdevs.dicodingevent.viewmodel.AdapterHorizontalEvent
import com.saefulrdevs.dicodingevent.viewmodel.AdapterVerticalEvent
import com.saefulrdevs.dicodingevent.viewmodel.MainViewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val mainViewModel by viewModels<MainViewModel>()
    private lateinit var adapterVertical: AdapterVerticalEvent
    private lateinit var adapterHorizontal: AdapterHorizontalEvent

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        setupRecyclerViews()
        setupAdapters()
        observeViewModel()

        return binding.root
    }

    private fun setupRecyclerViews() {
        val horizontalLayout =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvUpcomingEvent.layoutManager = horizontalLayout
        binding.rvUpcomingEvent.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                horizontalLayout.orientation
            )
        )

        val verticalLayout = LinearLayoutManager(requireContext())
        binding.rvFinishedEvent.layoutManager = verticalLayout
        binding.rvFinishedEvent.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                verticalLayout.orientation
            )
        )
    }

    private fun setupAdapters() {
        adapterVertical = AdapterVerticalEvent { eventId ->
            val bundle = Bundle().apply {
                if (eventId != null) {
                    putInt("eventId", eventId)
                }
            }
            findNavController().navigate(R.id.navigation_detail, bundle)
        }

        adapterHorizontal = AdapterHorizontalEvent { eventId ->
            val bundle = Bundle().apply {
                if (eventId != null) {
                    putInt("eventId", eventId)
                }
            }
            findNavController().navigate(R.id.navigation_detail, bundle)
        }

        binding.rvUpcomingEvent.adapter = adapterHorizontal
        binding.rvFinishedEvent.adapter = adapterVertical
    }

    private fun observeViewModel() {

        mainViewModel.upcomingEvent.observe(viewLifecycleOwner) { listItems ->
            setUpcomingEvent(listItems)
            mainViewModel.clearErrorMessage()
        }

        mainViewModel.finishedEvent.observe(viewLifecycleOwner) { listItems ->
            setFinishedEvent(listItems)
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
                recyclerView = binding.rvFinishedEvent
            ) {
                mainViewModel.getUpcomingEvent()
                mainViewModel.getFinishedEvent()
            }
        }
    }

    private fun setUpcomingEvent(listUpcomingEvent: List<ListEventsItem>) {
        val limitedList =
            if (listUpcomingEvent.size > 5) listUpcomingEvent.take(5) else listUpcomingEvent
        adapterHorizontal.submitList(limitedList)
    }

    private fun setFinishedEvent(listFinishedEvent: List<ListEventsItem>) {
        val limitedList =
            if (listFinishedEvent.size > 5) listFinishedEvent.takeLast(5) else listFinishedEvent
        adapterVertical.submitList(limitedList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
