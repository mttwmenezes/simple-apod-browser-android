/*
Copyright 2024 Matheus Menezes

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

package br.com.mathsemilio.simpleapodbrowser.feature.latest.ui.view.list

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import br.com.mathsemilio.simpleapodbrowser.R
import br.com.mathsemilio.simpleapodbrowser.databinding.ItemPeriodFilterBinding
import br.com.mathsemilio.simpleapodbrowser.databinding.ItemProminentBinding
import br.com.mathsemilio.simpleapodbrowser.databinding.ItemRegularBinding
import br.com.mathsemilio.simpleapodbrowser.databinding.ItemRegularSpacerBinding
import br.com.mathsemilio.simpleapodbrowser.databinding.ItemSmallSpacerBinding
import br.com.mathsemilio.simpleapodbrowser.feature.common.ImageResource

sealed class LatestItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    class SmallSpacer(binding: ItemSmallSpacerBinding) : LatestItemViewHolder(binding.root)

    class RegularSpacer(binding: ItemRegularSpacerBinding) : LatestItemViewHolder(binding.root)

    class PeriodFilter(
        private val binding: ItemPeriodFilterBinding
    ) : LatestItemViewHolder(binding.root) {

        private val options = mapOf(
            R.id.option_last_week to PeriodFilterOption.LAST_WEEK,
            R.id.option_last_two_weeks to PeriodFilterOption.LAST_TWO_WEEKS,
            R.id.option_last_month to PeriodFilterOption.LAST_MONTH
        )

        fun bind(
            item: LatestListItem.PeriodFilter,
            onPeriodFilterChanged: (PeriodFilterOption) -> Unit
        ) {
            val chipId = options.filterValues { it == item.periodSelected }.keys.first()
            binding.periodFilters.apply {
                check(chipId)
                setOnCheckedStateChangeListener { group, _ ->
                    onPeriodFilterChanged(options[group.checkedChipId] ?: PeriodFilterOption.LAST_WEEK)
                }
            }
        }
    }

    class Prominent(
        private val binding: ItemProminentBinding
    ) : LatestItemViewHolder(binding.root) {

        fun bind(item: LatestListItem.Prominent, onItemClicked: (String) -> Unit) {
            binding.apply {
                ImageResource.load(item.imageUrl, image)
                title.text = item.title
                mediaType.text = item.mediaType

                root.setOnClickListener { onItemClicked(item.id) }
                image.setOnClickListener { onItemClicked(item.id) }
            }
        }
    }

    class Regular(
        private val binding: ItemRegularBinding
    ) : LatestItemViewHolder(binding.root) {

        fun bind(item: LatestListItem.Regular, onItemClicked: (String) -> Unit) {
            binding.apply {
                ImageResource.load(item.imageUrl, image)
                title.text = item.title
                mediaType.text = item.mediaType
                date.text = item.formattedDate

                root.setOnClickListener { onItemClicked(item.id) }
                image.setOnClickListener { onItemClicked(item.id) }
            }
        }
    }
}
