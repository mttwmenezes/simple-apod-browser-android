/*
Copyright 2021 Matheus Menezes

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */

package br.com.mathsemilio.simpleapodbrowser.ui.screens.apoddetail.view

import android.view.*
import android.widget.*
import androidx.core.view.isVisible
import br.com.mathsemilio.simpleapodbrowser.R
import br.com.mathsemilio.simpleapodbrowser.common.*
import br.com.mathsemilio.simpleapodbrowser.domain.model.Apod
import br.com.mathsemilio.simpleapodbrowser.common.util.date.formatDate
import br.com.mathsemilio.simpleapodbrowser.ui.common.manager.ImageResourceManager

class ApodDetailViewImpl(
    layoutInflater: LayoutInflater,
    container: ViewGroup?
) : ApodDetailView() {

    private lateinit var imageViewApod: ImageView
    private lateinit var imageViewPlayIcon: ImageView

    private lateinit var textViewApodTitle: TextView
    private lateinit var textViewApodDate: TextView
    private lateinit var textViewApodExplanation: TextView

    private lateinit var apod: Apod

    init {
        rootView = layoutInflater.inflate(R.layout.apod_detail_screen, container, false)
        initializeViews()
    }

    override val apodImageView: ImageView
        get() = imageViewApod

    private fun initializeViews() {
        imageViewApod = findViewById(R.id.image_view_apod)
        imageViewPlayIcon = findViewById(R.id.image_view_play_icon)

        textViewApodTitle = findViewById(R.id.text_view_apod_title)
        textViewApodDate = findViewById(R.id.text_view_apod_date)
        textViewApodExplanation = findViewById(R.id.text_view_apod_explanation)
    }

    override fun bind(apod: Apod) {
        this.apod = apod

        loadResourcesBasedOnMediaType()

        textViewApodTitle.text = apod.title
        textViewApodDate.text = apod.date.formatDate()
        textViewApodExplanation.text = apod.explanation
    }

    private fun loadResourcesBasedOnMediaType() {
        when (apod.mediaType) {
            APOD_TYPE_IMAGE -> setupApodImage()
            APOD_TYPE_VIDEO -> setupVideoThumbnail()
        }
    }

    private fun setupApodImage() {
        imageViewPlayIcon.isVisible = false
        ImageResourceManager.loadWithPlaceholderFrom(apod.url, imageViewApod)
    }

    private fun setupVideoThumbnail() {
        ImageResourceManager.loadWithPlaceholderFrom(apod.thumbnailUrl!!, imageViewApod)

        imageViewPlayIcon.apply {
            isVisible = true
            setOnClickListener { notify { listener -> listener.onPlayIconClicked(apod.url) } }
        }
    }
}
