package com.example.guideforbeybladeburst2k20

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.guideforbeybladeburst2k20.bookList.Blade
import com.example.guideforbeybladeburst2k20.util.Event
import com.example.guideforbeybladeburst2k20.util.getStringBlade
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.InterstitialAd
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModelBlade(application: Application) : AndroidViewModel(application) {
    private val _splashState = MutableLiveData<Event<SplashStateBlade>>()
    val splashStateBlade: LiveData<Event<SplashStateBlade>> = _splashState

    var adView: AdView? = null
    lateinit var interstitialAd: InterstitialAd

    private val _showAdvertEvent: MutableLiveData<Event<Boolean>> =
        MutableLiveData<Event<Boolean>>()
    val showAdvertEvent: LiveData<Event<Boolean>> = _showAdvertEvent

    var showAdvertState = false

    private val _navigateToDetailEvent = MutableLiveData<Event<Blade>>()
    val navigateToDetailEvent: LiveData<Event<Blade>> = _navigateToDetailEvent

    private val _items = MutableLiveData<List<Blade>>().apply {
        value = getBooksListBlade()
    }

    val items: LiveData<List<Blade>> = _items

    init {
        viewModelScope.launch {
            delay(3000)
            _splashState.postValue(
                Event(
                    SplashStateBlade.MainActivity()
                )
            )
            showAdvertBlade()
        }
    }

    fun showAdvertBlade() {
        if (showAdvertState)
            _showAdvertEvent.postValue(Event(showAdvertState))
    }

    fun openBookBlade(blade: Blade) {
        _navigateToDetailEvent.postValue(
            Event(
                blade
            )
        )
    }

    private fun getBooksListBlade(): List<Blade> {
        return listOf(
            Blade(
                title = getStringBlade(R.string.book1title),
                body = getStringBlade(R.string.book1body),
                imageId = R.drawable.foot1blade
            ),
            Blade(
                title = getStringBlade(R.string.book_1_title),
                body = getStringBlade(R.string.book_1_body),
                imageId = R.drawable.foot2blade
            ),
            Blade(
                title = getStringBlade(R.string.book_2_title),
                body = getStringBlade(R.string.book_2_body),
                imageId = R.drawable.foot3blade
            ),
            Blade(
                title = getStringBlade(R.string.book_3_title),
                body = getStringBlade(R.string.book_3_body),
                imageId = R.drawable.foot4blade
            )
        )
    }
}

sealed class SplashStateBlade {
    class MainActivity : SplashStateBlade()
}
