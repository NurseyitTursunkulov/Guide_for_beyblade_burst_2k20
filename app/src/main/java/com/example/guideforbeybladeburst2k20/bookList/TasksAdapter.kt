/*
 * Copyright (C) 2019 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.guideforbeybladeburst2k20.bookList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.example.guideforbeybladeburst2k20.MainViewModel
import com.example.guideforbeybladeburst2k20.R
import com.example.guideforbeybladeburst2k20.databinding.ListItemBookBinding
import com.google.android.gms.ads.AdView

class TasksAdapter(private val viewModel: MainViewModel) :
    ListAdapter<Book, RecyclerView.ViewHolder>(TaskDiffCallback()) {
    private val MENU_ITEM_VIEW_TYPE = 0

    // The banner ad view type.
    private val BANNER_AD_VIEW_TYPE = 1
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        when (holder) {
            is ViewHolder -> holder.bind(viewModel, item)
            else -> {
                val bannerHolder = holder as AdViewHolder
                bannerHolder.bind()
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            MENU_ITEM_VIEW_TYPE -> ViewHolder.from(parent)
            else -> {
                val bannerLayoutView = LayoutInflater.from(
                    parent.context
                ).inflate(
                    R.layout.banner_ad_container,
                    parent, false
                )
                AdViewHolder(bannerLayoutView)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0 && viewModel.showAdvertState)
            BANNER_AD_VIEW_TYPE else MENU_ITEM_VIEW_TYPE
    }

    class ViewHolder private constructor(val binding: ListItemBookBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(viewModel: MainViewModel, item: Book) {

            binding.viewmodel = viewModel
            binding.task = item
            binding.imageViewBookCover.load(item.imageId) {
                crossfade(true)
                placeholder(R.drawable.bookdash_placeholder)
            }
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemBookBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }

    class AdViewHolder internal constructor(view: View) :
        RecyclerView.ViewHolder(view) {
        var ad_view: AdView = view.findViewById(R.id.ad_viewR)
        fun bind() {
            val adRequest = getAdRequest()
            ad_view.loadAd(adRequest)
        }
    }
}
