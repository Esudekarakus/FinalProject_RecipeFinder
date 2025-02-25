plugins {
    id ("com.android.application")
    id ("kotlin-android")
    id ("kotlin-kapt")
    id ("dagger.hilt.android.plugin")
}

android {
    namespace= "com.example.recipefinder"
    compileSdk= 34

    defaultConfig {
        applicationId= "com.example.recipefinder"
        minSdk= 24
        targetSdk= 34
        versionCode= 1
        versionName= "1.0"


        testInstrumentationRunner ="androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }


    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility= JavaVersion.VERSION_17
        targetCompatibility= JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        compose =true
    }

    composeOptions {
        kotlinCompilerExtensionVersion ="1.5.1"
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    // Diğer bağımlılıklar buraya eklenecek
    implementation ("androidx.core:core-ktx:1.7.0")
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.4.0")
    implementation ("androidx.activity:activity-compose:1.4.0")
    implementation ("androidx.compose.ui:ui:1.1.0-alpha05")
    implementation ("androidx.compose.ui:ui-graphics:1.1.0-alpha05")
    implementation ("androidx.compose.ui:ui-tooling:1.1.0-alpha05")
    implementation ("androidx.compose.material3:material3:1.2.0-rc01")
    implementation ("androidx.navigation:navigation-ui-ktx:2.4.0")
    implementation ("androidx.navigation:navigation-fragment-ktx:2.4.0")
    implementation ("androidx.navigation:navigation-compose:2.4.0-alpha10")
    implementation ("com.google.code.gson:gson:2.8.8")
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.squareup.okhttp3:okhttp:4.9.2")
    implementation(libs.androidx.runtime.livedata)
    testImplementation ("junit:junit:4.13.2")
    implementation ("io.coil-kt:coil-compose:1.4.0")
    androidTestImplementation ("androidx.test.ext:junit:1.1.3")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.4.0")
    debugImplementation ("androidx.compose.ui:ui-tooling-preview:1.1.0-alpha05")
    debugImplementation ("androidx.compose.ui:ui-test-manifest:1.1.0-alpha05")
    implementation ("com.google.dagger:hilt-android:2.49")
    kapt ("com.google.dagger:hilt-android-compiler:2.49")
    implementation ("androidx.room:room-runtime:2.5.0-beta01")
    implementation ("androidx.room:room-ktx:2.4.3")
    kapt ("androidx.room:room-compiler:2.5.0-beta01")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.0")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.0")
    implementation ("androidx.hilt:hilt-navigation-compose:1.2.0")
    implementation ("com.github.chuckerteam.chucker:library:4.0.0")
    implementation ("androidx.work:work-runtime:2.7.1")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.1")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.1")
    implementation("androidx.datastore:datastore-preferences:1.0.0")
}

kapt {
    correctErrorTypes = true
}
