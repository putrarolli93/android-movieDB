# android-mvvm


this repo is for super simple example MVVM architecture using MovieDB Api data source

- MVVM
- Depedency Injection: KOIN
- Http request: Retrofit
- Room for DB
- Source: https://developers.themoviedb.org/


complete depedencies:

    implementation "org.jetbrains.anko:anko-commons:0.10.8"
    // Retrofit
    implementation 'com.squareup.retrofit2:retrofit:2.3.0'
    implementation 'com.squareup.retrofit2:converter-gson:2.3.0'
    implementation 'com.squareup.okhttp3:logging-interceptor:3.9.1'
    // Koin
    implementation 'org.koin:koin-androidx-viewmodel:2.0.1'
    //Room
    implementation "androidx.room:room-runtime:2.2.0-rc01"
    implementation "androidx.room:room-ktx:2.2.0-rc01"
    kapt "androidx.room:room-compiler:2.2.0-rc01"
    //Glide
    implementation 'com.github.bumptech.glide:glide:4.11.0'
