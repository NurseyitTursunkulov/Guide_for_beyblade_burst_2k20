package com.example.guideforbeybladeburst2k20

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.guideforbeybladeburst2k20.bookList.Book
import com.example.guideforbeybladeburst2k20.util.Event
import com.example.guideforbeybladeburst2k20.util.getString
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.InterstitialAd
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val _splashState = MutableLiveData<Event<SplashState>>()
    val splashState: LiveData<Event<SplashState>> = _splashState

    var adView: AdView? = null
    lateinit var interstitialAd: InterstitialAd

    private val _showAdvertEvent: MutableLiveData<Event<Boolean>> =
        MutableLiveData<Event<Boolean>>()
    val showAdvertEvent: LiveData<Event<Boolean>> = _showAdvertEvent

    var showAdvertState = true

    private val _navigateToDetailEvent = MutableLiveData<Event<Book>>()
    val navigateToDetailEvent: LiveData<Event<Book>> = _navigateToDetailEvent

    private val _items = MutableLiveData<List<Book>>().apply {
        value = getBooksList()
    }

    val items: LiveData<List<Book>> = _items

    init {
        viewModelScope.launch {
            delay(2000)
            _splashState.postValue(
                Event(
                    SplashState.MainActivity()
                )
            )
            showAdvert()
        }
    }

    fun showAdvert() {
        if (showAdvertState)
            _showAdvertEvent.postValue(Event(showAdvertState))
    }

    fun openBook(book: Book) {
        _navigateToDetailEvent.postValue(
            Event(
                book
            )
        )
    }

    private fun getBooksList(): List<Book> {
        return listOf(
            Book(
                title = getString(R.string.book1title),
                body = getString(R.string.book1body),
                imageId = R.drawable.foot1
            ),
            Book(
                title = getString(R.string.book_1_title),
                body = getString(R.string.book_1_body),
                imageId = R.drawable.foot2
            ),
            Book(
                title = getString(R.string.book_2_title),
                body = getString(R.string.book_2_body),
                imageId = R.drawable.image3
            ),
            Book(
                title = getString(R.string.book_3_title),
                body = getString(R.string.book_3_body),
                imageId = R.drawable.image5
            ),
            Book(
                title = getString(R.string.book_4_title),
                body = getString(R.string.book_4_body),
                imageId = R.drawable.image4
            ),
            Book(
                title = getString(R.string.book_5_title),
                body = getString(R.string.book_5_body),
                imageId = R.drawable.image7
            ),
            Book(
                title = getString(R.string.book_6_title),
                body = getString(R.string.book_6_body),
                imageId = R.drawable.image6
            ),
            Book(
                title = getString(R.string.book_7_title),
                body = getString(R.string.book_7_body),
                imageId = R.drawable.image2
            )
        )
    }
}

sealed class SplashState {
    class MainActivity : SplashState()
}
