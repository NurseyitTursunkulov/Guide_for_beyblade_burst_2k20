package com.example.guideforbeybladeburst2k20.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.guideforbeybladeburst2k20.MainViewModelBlade
import com.example.guideforbeybladeburst2k20.databinding.FragmentBookDetailbladeBinding
import com.example.guideforbeybladeburst2k20.util.EventObserver
import kotlinx.android.synthetic.main.fragment_book_detailblade.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ScreenSlideFirstPageFragmentBlade : Fragment() {
    lateinit var content: String
    lateinit var viewDataBinding: FragmentBookDetailbladeBinding
    val viewModelBlade: MainViewModelBlade by sharedViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            content = it.getString(CONTENT, "")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModelBlade.showAdvertBlade()
        (activity as AppCompatActivity).supportActionBar?.show()
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        viewDataBinding = FragmentBookDetailbladeBinding.inflate(inflater, container, false).apply {
            bookInfo = viewModelBlade.navigateToDetailEvent.value?.peekContent()
        }
        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        content_text_view.text = content
        toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }

        showBannerAdvertBlade(ad_view_detail, viewModelBlade.showAdvertState)

        viewModelBlade.showAdvertEvent.observe(viewLifecycleOwner, EventObserver {
            showInterstitialAdvertSafeBlade(viewModelBlade.interstitialAd)
        })
    }

    companion object {
        const val CONTENT = "content_"

        @JvmStatic
        fun newInstance(content: String) =
            ScreenSlideFirstPageFragmentBlade().apply {
                arguments = Bundle().apply {
                    putString(CONTENT, content)
                }
            }
    }
}