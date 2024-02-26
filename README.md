# MovieFlix App

The MovieFlix app is a part of the interview process with Atcom

## ✨ Features

- 🎬 Fetch and display a list of popular movies from The Movie Database API.
- 📽️ Click on any movie to view detailed information, including synopsis, ratings, and release date.
- 💾 Locally cache movie details for offline access and faster load times.
- 🚫 Robust error handling to gracefully manage internet connectivity issues.
- 👆 Implement swipe-to-refresh functionality to easily update movie lists in case of network errors.
- 🔄 Ensures data freshness by frequently updating the contents of the local database with the latest from the API.

## 🚀 Technologies and Libraries Used

- 🎭 **Jetpack Compose**: Utilized for building native UIs using a declarative approach, making UI development more intuitive and efficient. [Learn more](https://developer.android.com/jetpack/compose)
- 🌐 **Hilt**: Implemented for Dependency Injection to manage dependencies and lifecycle, simplifying the architecture and testing. [Learn more](https://developer.android.com/training/dependency-injection/hilt-android)
- 🚀 **Kotlin**: The primary programming language, leveraging its modern features for more concise and expressive code.
- 🏛️ **Android Architecture Components**
- ⏱️ **Kotlin Coroutines**: Used for asynchronous programming, simplifying the way background tasks are handled and improving app performance. [Learn more](https://kotlinlang.org/docs/coroutines-overview.html)
- 🎨 **Material Components for Android**: For implementing Material Design, ensuring the application has a beautiful and functional UI. [Learn more](https://material.io/develop/android/docs/getting-started/)
- 🔄 **Custom Paging Logic**: Developed to efficiently load and display lists of movies, enhancing user experience by dynamically loading data as needed.
- 📡 **Retrofit**: For network communication, integrated seamlessly with Kotlin Coroutines for efficient network calls. [Learn more](https://square.github.io/retrofit/)
- 🖼️ **Coil for Compose**: For image loading within Compose, providing an easy and efficient way to load and cache images. [Learn more](https://coil-kt.github.io/coil/compose/)
- 📚 **Room**: For local data persistence, enabling robust data caching mechanisms and offline functionality. [Learn more](https://developer.android.com/training/data-storage/room)
- 🔄 **Swipe-to-Refresh**: Functionality integrated for refreshing content

## 🔮 Improvements to be made

1. Securing the api key

2. Making the app's UI adaptive to different screen sizes, since at the moment, values were hardcoded in, with the pixel 5 in mind
