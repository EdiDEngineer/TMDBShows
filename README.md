# TMDBShows

This repository contains a TMDB android application that loads TV Shows and displays them in a list.

## 💻 Requirements

[Android Studio](https://developer.android.com/studio)

[Downloadable APK](https://github.com/EdiDEngineer/TMDBShows/blob/master/app-debug.apk)

Android Minimum SDK: 21

Android Target SDK: 33

Kotlin Version 1.8.10


        
## Overview
Simple Android app with a screen that requests a list of TV shows and presents them to the user. Also contains a feature to sort the results alphabetically based on the TV shows name field.

## Resources
A request to the The Movie Database API will return a result set encoded using the JSON
format, more information about the API can be found here
https://www.themoviedb.org/documentation/api

## App Architecture 
This App uses ``MVVM``(Model-View-ViewModel) + Clean Architecture principles.  

## Libraries used
- Jetpack Compose for UI.
- Hilt for dependency injection.
- Lifecycle components containing, viewmodel and lifecylce to handle state.
- Retrofit + OkHttp to make network calls.
- Coroutines + Flow for asynchronous tasks. 
- Coil for image loading.
- Mockito, JUnit for unit testing. 
- AndroidJUnit for UI testing.

## License

```
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    https://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
