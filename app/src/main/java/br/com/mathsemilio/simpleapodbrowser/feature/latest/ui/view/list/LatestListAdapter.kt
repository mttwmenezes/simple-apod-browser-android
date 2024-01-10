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

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.mathsemilio.simpleapodbrowser.R
import br.com.mathsemilio.simpleapodbrowser.databinding.ItemPeriodFilterBinding
import br.com.mathsemilio.simpleapodbrowser.databinding.ItemProminentBinding
import br.com.mathsemilio.simpleapodbrowser.databinding.ItemRegularBinding
import br.com.mathsemilio.simpleapodbrowser.databinding.ItemRegularSpacerBinding
import br.com.mathsemilio.simpleapodbrowser.databinding.ItemSmallSpacerBinding

class LatestListAdapter(
    private val listener: Listener
) : RecyclerView.Adapter<LatestItemViewHolder>() {

    interface Listener {
        fun onPeriodFilterChanged(option: PeriodFilterOption)
        fun onListItemClicked(id: String)
    }

    var items: List<LatestListItem> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LatestItemViewHolder {
        return when (viewType) {
            R.layout.item_period_filter -> {
                LatestItemViewHolder.PeriodFilter(
                    ItemPeriodFilterBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }

            R.layout.item_prominent -> {
                LatestItemViewHolder.Prominent(
                    ItemProminentBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }

            R.layout.item_regular -> {
                LatestItemViewHolder.Regular(
                    ItemRegularBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }

            R.layout.item_regular_spacer -> {
                LatestItemViewHolder.RegularSpacer(
                    ItemRegularSpacerBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }

            R.layout.item_small_spacer -> {
                LatestItemViewHolder.SmallSpacer(
                    ItemSmallSpacerBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }

            else -> throw IllegalArgumentException("Invalid viewType: $viewType")
        }
    }

    override fun onBindViewHolder(holder: LatestItemViewHolder, position: Int) {
        when (holder) {
            is LatestItemViewHolder.PeriodFilter -> {
                holder.bind(items[position] as LatestListItem.PeriodFilter) {
                    listener.onPeriodFilterChanged(it)
                }
            }

            is LatestItemViewHolder.Prominent -> {
                holder.bind(items[position] as LatestListItem.Prominent) {
                    listener.onListItemClicked(it)
                }
            }

            is LatestItemViewHolder.Regular -> {
                holder.bind(items[position] as LatestListItem.Regular) {
                    listener.onListItemClicked(it)
                }
            }

            else -> { /* no-op */ }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is LatestListItem.PeriodFilter -> R.layout.item_period_filter
            is LatestListItem.Prominent -> R.layout.item_prominent
            is LatestListItem.Regular -> R.layout.item_regular
            LatestListItem.RegularSpacer -> R.layout.item_regular_spacer
            LatestListItem.SmallSpacer -> R.layout.item_small_spacer
        }
    }

    override fun getItemCount() = items.count()
}
