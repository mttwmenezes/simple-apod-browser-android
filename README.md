# Simple APOD Browser
APOD client app for Android.

[![simple-apod-browser-readme-image.png](https://i.postimg.cc/wBX2nxgh/Simple-APOD-Browser-Git-README-image.png)](https://postimg.cc/Y4SQWH49)

## What is APOD?
APOD is short for [Astronomy Picture of the Day](https://apod.nasa.gov/apod/astropix.html), 
which is a website provided by NASA and the Michigan Technological University. According to the website:
> Each day a different image or photograph of our universe is featured, along with a brief explanation written by a professional astronomer.

## Features
- Feeling lucky... (Random APOD picker)
- Pick APOD by date
- Favorite APODs
- Export images (Not supported on Android 13+, see [issue](https://github.com/mttwmenezes/simple-apod-browser-android/issues/1) for information.)

## API
This app makes use of the APOD API, which is part of the open APIs provided by NASA. The API is open-source and
is available on [GitHub](https://github.com/nasa/apod-api).

## Build instructions
By default, the app uses the APIs `DEMO_KEY` public key for accessing its services. If you wish to use your own,
open the `ApodService.kt` file and replace all occurrences of `BuildConfig.PUBLIC_KEY`. You can generate a key at 
[NASA's Open APIs website](https://api.nasa.gov/).

## Development technologies
Libraries/Frameworks used:
- <a href="https://developer.android.com/topic/libraries/architecture/viewmodel">ViewModel</a>
- <a href="https://developer.android.com/topic/libraries/architecture/livedata">LiveData</a>
- <a href="https://developer.android.com/training/data-storage/room/">Room</a>
- <a href="https://developer.android.com/guide/navigation/navigation-getting-started">Navigation Component</a>
- <a href="https://developer.android.com/training/dependency-injection/hilt-android">Hilt</a>
- <a href="https://developer.android.com/jetpack/androidx/releases/swiperefreshlayout">Swipe-Refresh Layout</a>
- <a href="https://developer.android.com/jetpack/androidx/releases/preference/">AndroidX Preference</a>
- <a href="https://developer.android.com/kotlin/coroutines">Kotlin Coroutines</a>
- <a href="https://square.github.io/retrofit/">Retrofit</a>
- <a href="https://github.com/bumptech/glide">Glide</a>
- <a href="https://site.mockito.org/">Mockito</a>

## License
```
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
```
