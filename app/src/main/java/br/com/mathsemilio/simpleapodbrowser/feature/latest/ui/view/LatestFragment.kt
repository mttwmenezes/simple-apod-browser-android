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

package br.com.mathsemilio.simpleapodbrowser.feature.latest.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import br.com.mathsemilio.simpleapodbrowser.databinding.FragmentLatestBinding
import br.com.mathsemilio.simpleapodbrowser.feature.latest.ui.view.list.LatestListAdapter
import br.com.mathsemilio.simpleapodbrowser.feature.latest.ui.view.list.PeriodFilterOption

class LatestFragment : Fragment(), LatestListAdapter.Listener {

    private var _binding: FragmentLatestBinding? = null
    private val binding get() = _binding!!

    private lateinit var listAdapter: LatestListAdapter

    private var periodSelected = PeriodFilterOption.LAST_WEEK

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLatestBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        listAdapter = LatestListAdapter(this)
        binding.recyclerView.apply {
            adapter = listAdapter
            setHasFixedSize(true)
        }
    }

    override fun onPeriodFilterChanged(option: PeriodFilterOption) {
        // TODO Not yet implemented
    }

    override fun onListItemClicked(id: String) {
        // TODO Not yet implemented
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
