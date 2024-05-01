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

package br.com.mathsemilio.simpleapodbrowser.ui.common.permission

import android.Manifest

data class Permission(
    val androidName: String,
    val requestCode: Int
) {
    companion object {
        val WRITE_EXTERNAL_STORAGE = Permission(
            androidName = Manifest.permission.WRITE_EXTERNAL_STORAGE,
            requestCode = 1
        )
    }
}
