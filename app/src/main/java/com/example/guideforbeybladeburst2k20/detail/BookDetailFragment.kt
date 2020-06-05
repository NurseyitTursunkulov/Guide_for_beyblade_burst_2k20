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
import com.example.guideforbeybladeburst2k20.MainViewModel
import com.example.guideforbeybladeburst2k20.R
import com.example.guideforbeybladeburst2k20.util.removeFullScreen
import kotlinx.android.synthetic.main.activity_screen_slide.*
import kotlinx.android.synthetic.main.fragment_book_detail.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class BookDetailFragment : Fragment(R.layout.activity_screen_slide) {

    val viewModel: MainViewModel by sharedViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        removeFullScreen()
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.show()
        pager.adapter = ScreenSlidePagerAdapter(requireActivity())
        initPendingIndicatorView()


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
                this@BookDetailFragment.findNavController().navigateUp()
            } else {
                // Otherwise, select the previous step.
                pager.currentItem = pager.currentItem - 1
                this@BookDetailFragment.findNavController().navigateUp()
            }
        }

    }


    private inner class ScreenSlidePagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int =
            viewModel.navigateToDetailEvent.value?.peekContent()?.listOfContentPerPage?.size ?: 1

        override fun createFragment(position: Int): Fragment {
            val content =
                viewModel.navigateToDetailEvent.value?.peekContent()?.listOfContentPerPage?.get(
                    position
                ) ?: ""
            return when (position) {
                0 -> ScreenSlideFirstPageFragment.newInstance(content)
                else -> ScreenSlidePageFragment.newInstance(
                    position,
                    content
                )
            }

        }
    }
}