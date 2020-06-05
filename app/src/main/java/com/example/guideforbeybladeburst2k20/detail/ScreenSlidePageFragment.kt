package com.example.guideforbeybladeburst2k20.detail

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import coil.api.load
import com.example.guideforbeybladeburst2k20.MainViewModel
import com.example.guideforbeybladeburst2k20.R
import com.example.guideforbeybladeburst2k20.util.EventObserver
import com.example.guideforbeybladeburst2k20.util.removeFullScreen
import kotlinx.android.synthetic.main.detail_viewpager.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class ScreenSlidePageFragment :
    Fragment(R.layout.detail_viewpager) {
    private lateinit var content: String
    private var position: Int = 0

    val viewModel: MainViewModel by sharedViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            content = it.getString(CONTENT, "")
            position = it.getInt(POSITION)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        removeFullScreen()
        (activity as AppCompatActivity).supportActionBar?.show()
        content_text_view.text = content
        toolbar.title = viewModel.navigateToDetailEvent.value?.peekContent()?.title
        toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }
        viewModel.showAdvertEvent.observe(viewLifecycleOwner, EventObserver {
            showInterstitialAdvertSafe(viewModel.interstitialAd)
        })
        showBannerAdvert(ad_view_detail_pager, viewModel.showAdvertState)

        imageView.load(getRandomImage())

        showRateMeDialog()
    }

    override fun onResume() {
        super.onResume()
        Log.d("Nurs", "onResume pos $position")
        if (position % 2 == 0) {
            Log.d("Nurs", "pos if $position")
            viewModel.showAdvert()
        }
    }

    companion object {
        const val POSITION = "position_"
        const val CONTENT = "content_"

        @JvmStatic
        fun newInstance(position: Int, content: String) =
            ScreenSlidePageFragment().apply {
                arguments = Bundle().apply {
                    putInt(POSITION, position)
                    putString(CONTENT, content)
                }
            }
    }
}