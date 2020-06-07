package com.example.guideforbeybladeburst2k20

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.guideforbeybladeburst2k20.util.EventObserver
import com.example.guideforbeybladeburst2k20.util.divideTextToPartsBlade
import com.example.guideforbeybladeburst2k20.util.initAdvertBlade
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class SplashFragmentBlade : Fragment(R.layout.splash_fragmentblade) {

    private val viewModelBlade: MainViewModelBlade by sharedViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModelBlade.adView = initAdvertBlade(requireContext())
        val content = viewModelBlade.items.value
        content?.let { bookList ->
            divideTextToPartsBlade(bookList)
        }

        viewModelBlade.splashStateBlade.observe(viewLifecycleOwner,
            EventObserver {
                when (it) {
                    is SplashStateBlade.MainActivity -> {
                        findNavController().navigate(R.id.action_splashFragment_to_mainFragment)
                    }
                }
            })
        viewModelBlade.showAdvertEvent.observe(viewLifecycleOwner, EventObserver {
            if (viewModelBlade.interstitialAd.isLoaded) {
                viewModelBlade.interstitialAd.show()
            } else {
                Log.d("Nurs", "splash The interstitial wasn't loaded yet.")
            }
        })
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.hide()
    }

}