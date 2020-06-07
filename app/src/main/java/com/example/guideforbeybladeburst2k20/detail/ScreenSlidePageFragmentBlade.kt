package com.example.guideforbeybladeburst2k20.detail

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import coil.api.load
import com.example.guideforbeybladeburst2k20.MainViewModelBlade
import com.example.guideforbeybladeburst2k20.R
import com.example.guideforbeybladeburst2k20.util.EventObserver
import com.example.guideforbeybladeburst2k20.util.removeFullScreenBlade
import kotlinx.android.synthetic.main.detail_viewpagerblade.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class ScreenSlidePageFragmentBlade :
    Fragment(R.layout.detail_viewpagerblade) {
    private lateinit var content: String
    private var position: Int = 0

    val viewModelBlade: MainViewModelBlade by sharedViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            content = it.getString(CONTENT, "")
            position = it.getInt(POSITION)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        removeFullScreenBlade()
        (activity as AppCompatActivity).supportActionBar?.show()
        content_text_view.text = content
        toolbar.title = viewModelBlade.navigateToDetailEvent.value?.peekContent()?.title
        toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }
        viewModelBlade.showAdvertEvent.observe(viewLifecycleOwner, EventObserver {
            showInterstitialAdvertSafeBlade(viewModelBlade.interstitialAd)
        })
        showBannerAdvertBlade(ad_view_detail_pager, viewModelBlade.showAdvertState)

        imageView.load(getRandomImageBlade())

        showRateMeDialogBlade()
    }

    override fun onResume() {
        super.onResume()
        Log.d("Nurs", "onResume pos $position")
        if (position % 2 == 0) {
            Log.d("Nurs", "pos if $position")
            viewModelBlade.showAdvertBlade()
        }
    }

    companion object {
        const val POSITION = "position_"
        const val CONTENT = "content_"

        @JvmStatic
        fun newInstance(position: Int, content: String) =
            ScreenSlidePageFragmentBlade().apply {
                arguments = Bundle().apply {
                    putInt(POSITION, position)
                    putString(CONTENT, content)
                }
            }
    }
}