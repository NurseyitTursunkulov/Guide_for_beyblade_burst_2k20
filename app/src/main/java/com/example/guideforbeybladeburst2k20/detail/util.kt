package com.example.guideforbeybladeburst2k20.detail

import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.androidsx.rateme.RateMeDialog
import com.androidsx.rateme.RateMeDialogTimer
import com.example.guideforbeybladeburst2k20.R
import com.example.guideforbeybladeburst2k20.bookList.getAdRequestBlade
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.InterstitialAd
import kotlinx.android.synthetic.main.activity_screen_slide.*
import java.util.*


fun BookDetailFragmentBlade.initPendingIndicatorViewBlade() {
    val book = viewModelBlade.navigateToDetailEvent.value?.peekContent()

    pageIndicatorView.count =
        book?.listOfContentPerPage?.size ?: 1 // specify total count of indicators

    pageIndicatorView.selection = 1
}

fun Fragment.showBannerAdvertBlade(adView: AdView, showAdvertState: Boolean) {
    if (showAdvertState) {
        adView.visibility = View.VISIBLE
        val adRequest = getAdRequestBlade()
        adView.loadAd(adRequest)
    }
}

fun Fragment.showRateMeDialogBlade() {
    RateMeDialogTimer.onStart(requireContext())
    if (RateMeDialogTimer.shouldShowRateDialog(requireContext(), 1, 2)) {
        RateMeDialog.Builder(requireActivity().packageName, "")
            .setHeaderBackgroundColor(resources.getColor(R.color.colorPrimary))
            .setBodyBackgroundColor(resources.getColor(R.color.dialog_body))
            .showAppIcon(R.mipmap.ic_logo_foreground)
            .enableFeedbackByEmail("")
            .setRateButtonBackgroundColor(resources.getColor(R.color.dialog_button))
            .build()
            .show(requireActivity().fragmentManager, "plain-dialog")
    }
}

fun showInterstitialAdvertSafeBlade(interstitialAd: InterstitialAd) {
    if (interstitialAd.isLoaded) {
        interstitialAd.show()
    } else {
        Log.d("Nurs", "first The interstitial wasn't loaded yet.")
    }
}

fun ScreenSlidePageFragmentBlade.getRandomImageBlade(): Int {
    val images =
        intArrayOf(
            R.drawable.foot1blade,
            R.drawable.foot2blade,
            R.drawable.foot3blade,
            R.drawable.foot4blade
        )
    val rand = Random()
    return images[rand.nextInt(images.size)]
}