package com.example.guideforbeybladeburst2k20

import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.example.guideforbeybladeburst2k20.util.initAddsBlade
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivityBlade : AppCompatActivity() {
    val viewModelBlade: MainViewModelBlade by viewModel()
    private lateinit var mInterstitialAd: InterstitialAd
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN)
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)

        setContentView(R.layout.content_mainblade)

        initAddsBlade(this)
        mInterstitialAd = InterstitialAd(this).apply {
            adUnitId = "ca-app-pub-3940256099942544/1033173712"
            loadAd(AdRequest.Builder().build())
            adListener = object : AdListener() {
                override fun onAdClosed() {
                    Log.d("Nurs", "onAdClosed")
                    mInterstitialAd.loadAd(AdRequest.Builder().build())
                }
            }
        }

        viewModelBlade.interstitialAd = mInterstitialAd

    }
}