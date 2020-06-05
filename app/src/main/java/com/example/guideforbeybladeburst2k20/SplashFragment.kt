package com.example.guideforbeybladeburst2k20

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.guideforbeybladeburst2k20.util.EventObserver
import com.example.guideforbeybladeburst2k20.util.divideTextToParts
import com.example.guideforbeybladeburst2k20.util.initAdvert
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class SplashFragment : Fragment(R.layout.splash_fragment) {

    private val viewModel: MainViewModel by sharedViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.adView = initAdvert(requireContext())
        val content = viewModel.items.value
        content?.let { bookList ->
            divideTextToParts(bookList)
        }

        viewModel.splashState.observe(viewLifecycleOwner,
            EventObserver {
                when (it) {
                    is SplashState.MainActivity -> {
                        findNavController().navigate(R.id.action_splashFragment_to_mainFragment)
                    }
                }
            })
        viewModel.showAdvertEvent.observe(viewLifecycleOwner, EventObserver {
            if (viewModel.interstitialAd.isLoaded) {
                viewModel.interstitialAd.show()
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