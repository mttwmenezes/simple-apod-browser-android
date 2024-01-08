/*
Copyright 2023 Matheus Menezes

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

package br.com.mathsemilio.simpleapodbrowser.ui.sheet

import android.os.Bundle
import android.view.View
import android.widget.TextView
import br.com.mathsemilio.simpleapodbrowser.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ImageCopyrightBottomSheet : BottomSheetDialogFragment(R.layout.image_copyright_bottom_sheet) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<TextView>(R.id.copyright).text = requireArguments().getString(
            ARG_COPYRIGHT
        )
    }

    companion object {
        private const val ARG_COPYRIGHT = "copyright"

        @JvmStatic
        fun newInstance(copyright: String): ImageCopyrightBottomSheet {
            return ImageCopyrightBottomSheet().apply {
                arguments = Bundle(1).apply { putString(ARG_COPYRIGHT, copyright) }
            }
        }
    }
}
