package com.example.guideforbeybladeburst2k20.bookList

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.guideforbeybladeburst2k20.MainViewModelBlade
import com.example.guideforbeybladeburst2k20.R
import com.example.guideforbeybladeburst2k20.databinding.FragmentMainbladeBinding
import com.example.guideforbeybladeburst2k20.util.EventObserver
import com.example.guideforbeybladeburst2k20.util.removeFullScreenBlade
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class MainFragmentBlade : Fragment() {

    lateinit var viewDataBinding: FragmentMainbladeBinding
    lateinit var listAdapterBlade: TasksAdapterBlade
    val viewModelBlade: MainViewModelBlade by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        removeFullScreenBlade()
        (activity as AppCompatActivity).supportActionBar?.show()
        viewDataBinding = FragmentMainbladeBinding.inflate(inflater, container, false).apply {
            viewmodel = viewModelBlade
        }
        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModelBlade.navigateToDetailEvent.observe(viewLifecycleOwner,
            EventObserver {
                findNavController().navigate(R.id.action_mainFragment_to_bookDetailFragment)
            })
        viewModelBlade.showAdvertEvent.observe(viewLifecycleOwner, EventObserver {
            if (viewModelBlade.interstitialAd.isLoaded) {
                viewModelBlade.interstitialAd.show()
            } else {
                Log.d("Nurs", "mainfrag The interstitial wasn't loaded yet.")
            }
        })
        initAdapterBlade()
    }

    override fun onPause() {
        viewModelBlade.adView?.pause()
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        viewModelBlade.adView?.resume()
    }

    override fun onDestroy() {
        viewModelBlade.adView?.destroy()
        super.onDestroy()
    }
}