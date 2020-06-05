package com.example.guideforbeybladeburst2k20.detail

import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.androidsx.rateme.RateMeDialog
import com.androidsx.rateme.RateMeDialogTimer
import com.example.guideforbeybladeburst2k20.R
import com.example.guideforbeybladeburst2k20.bookList.getAdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.InterstitialAd
import kotlinx.android.synthetic.main.activity_screen_slide.*
import java.util.*


fun BookDetailFragment.initPendingIndicatorView() {
    val book = viewModel.navigateToDetailEvent.value?.peekContent()

    pageIndicatorView.count =
        book?.listOfContentPerPage?.size ?: 1 // specify total count of indicators

    pageIndicatorView.selection = 1
}

fun Fragment.showBannerAdvert(adView: AdView, showAdvertState: Boolean) {
    if (showAdvertState) {
        adView.visibility = View.VISIBLE
        val adRequest = getAdRequest()
        adView.loadAd(adRequest)
    }
}

fun Fragment.showRateMeDialog() {
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

fun showInterstitialAdvertSafe(interstitialAd: InterstitialAd) {
    if (interstitialAd.isLoaded) {
        interstitialAd.show()
    } else {
        Log.d("Nurs", "first The interstitial wasn't loaded yet.")
    }
}

fun ScreenSlidePageFragment.getRandomImage(): Int {
    val images =
        intArrayOf(
            R.drawable.foot5 //ok
            , R.drawable.image1// ok
            , R.drawable.image2, //ok
            R.drawable.foot12, //ok
            R.drawable.foot13,// ok
            R.drawable.foot15,// ok
            R.drawable.foot16,// ok
            R.drawable.foot18,
            R.drawable.foot20,
            R.drawable.foot21,
            R.drawable.foot19
        )
    val rand = Random()
    return images[rand.nextInt(images.size)]
}