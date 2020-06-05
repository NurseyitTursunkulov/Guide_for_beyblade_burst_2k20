package com.example.guideforbeybladeburst2k20.bookList

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import java.util.*


fun MainFragment.initAdapter() {
    val viewModel = viewDataBinding.viewmodel
    if (viewModel != null) {
        listAdapter = TasksAdapter(viewModel)
        viewDataBinding.recyclerViewBooks.adapter = listAdapter
        viewDataBinding.recyclerViewBooks.layoutManager = GridLayoutManager(requireContext(), 2)
        if (viewModel.showAdvertState)
            makeOneSpanForAdView()
        viewModel.items.observe(viewLifecycleOwner, Observer {
            listAdapter.submitList(it)
        })
    } else {
        //            Timber.w("ViewModel not initialized when attempting to set up adapter.")
    }
}

class TaskDiffCallback : DiffUtil.ItemCallback<Book>() {

    override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean {
        return oldItem == newItem
    }

}

@BindingAdapter("imageResource")
fun setImageResource(imageView: ImageView, resource: Int) {
    imageView.setImageResource(resource)
}

fun getAdRequest(): AdRequest? {
    val adRequest = AdRequest.Builder().build()
    val testDeviceIds = Arrays.asList("F5E4CD8EA025C4062D9E4BE54D002D25")
    val configuration =
        RequestConfiguration.Builder().setTestDeviceIds(testDeviceIds).build()
    MobileAds.setRequestConfiguration(configuration)
    return adRequest
}

private fun MainFragment.makeOneSpanForAdView() {
    (viewDataBinding.recyclerViewBooks.layoutManager as GridLayoutManager)
        .spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
        override fun getSpanSize(position: Int): Int {
            return when (position) {
                0 -> 2
                else -> 1
            }
        }
    }
}
