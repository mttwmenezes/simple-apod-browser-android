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

package br.com.mathsemilio.simpleapodbrowser.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.mathsemilio.simpleapodbrowser.common.FAVORITE_APOD_TABLE
import java.io.Serializable

@Entity(tableName = FAVORITE_APOD_TABLE)
data class Apod(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val url: String,
    val date: String,
    val mediaType: String,
    val explanation: String,
    val thumbnailUrl: String? = null,
    val copyright: String? = null,
    val isFavorite: Boolean = false
) : Serializable
