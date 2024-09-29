package com.saefulrdevs.dicodingevent.ui.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.saefulrdevs.dicodingevent.R
import com.saefulrdevs.dicodingevent.databinding.FragmentDetailBinding
import com.saefulrdevs.dicodingevent.viewmodel.MainViewModel

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)

        val eventId = arguments?.getInt("eventId")

        if (eventId != null) {
            mainViewModel.getDetailEvent(eventId)
        }

        mainViewModel.detailEvent.observe(viewLifecycleOwner) { event ->
            binding.tvEventName.text = event.name
            binding.tvOwnerName.text = event.ownerName
            binding.tvEventTime.text = getString(R.string.event_time, event.beginTime)
            val remainingQuota = event.quota?.minus(event.registrants ?: 0) ?: 0
            binding.tvQuota.text = getString(R.string.quota_remaining, remainingQuota)

            binding.tvDescription.text = event.description?.let {
                HtmlCompat.fromHtml(
                    it,
                    HtmlCompat.FROM_HTML_MODE_LEGACY
                )
            }

            Glide.with(this)
                .load(event.mediaCover)
                .into(binding.ivMediaCover)

            binding.btnEventLink.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(event.link))
                startActivity(intent)
            }
        }

        mainViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        mainViewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            if (!errorMessage.isNullOrEmpty()) {
                binding.handlingLayout.visibility = View.VISIBLE
                binding.tvErrorMessage.text = errorMessage
                binding.btnRefresh.visibility = View.VISIBLE

                binding.btnRefresh.setOnClickListener {
                    if (eventId != null) {
                        mainViewModel.getDetailEvent(eventId)
                    }
                }
            } else {
                binding.handlingLayout.visibility = View.GONE
            }
        }
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        return binding.root
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

