package com.example.guideforbeybladeburst2k20.detail

import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.guideforbeybladeburst2k20.MainViewModelBlade
import com.example.guideforbeybladeburst2k20.R
import com.example.guideforbeybladeburst2k20.util.removeFullScreenBlade
import kotlinx.android.synthetic.main.activity_screen_slide.*
import kotlinx.android.synthetic.main.fragment_book_detailblade.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class BookDetailFragmentBlade : Fragment(R.layout.activity_screen_slide) {

    val viewModelBlade: MainViewModelBlade by sharedViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        removeFullScreenBlade()
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.show()
        pager.adapter = ScreenSlidePagerAdapterBlade(requireActivity())
        initPendingIndicatorViewBlade()


        pager.registerOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    pageIndicatorView.selection = position
                }
            })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // This callback will only be called when MyFragment is at least Started.
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            // Handle the back button event
            if (pager.currentItem == 0) {
                // If the user is currently looking at the first step, allow the system to handle the
                // Back button. This calls finish() on this activity and pops the back stack.
                this@BookDetailFragmentBlade.findNavController().navigateUp()
            } else {
                // Otherwise, select the previous step.
                pager.currentItem = pager.currentItem - 1
                this@BookDetailFragmentBlade.findNavController().navigateUp()
            }
        }

    }


    private inner class ScreenSlidePagerAdapterBlade(fa: FragmentActivity) :
        FragmentStateAdapter(fa) {
        override fun getItemCount(): Int =
            viewModelBlade.navigateToDetailEvent.value?.peekContent()?.listOfContentPerPage?.size
                ?: 1

        override fun createFragment(position: Int): Fragment {
            val content =
                viewModelBlade.navigateToDetailEvent.value?.peekContent()?.listOfContentPerPage?.get(
                    position
                ) ?: ""
            return when (position) {
                0 -> ScreenSlideFirstPageFragmentBlade.newInstance(content)
                else -> ScreenSlidePageFragmentBlade.newInstance(
                    position,
                    content
                )
            }

        }
    }
}