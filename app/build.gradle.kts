import org.gradle.internal.impldep.bsh.commands.dir

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.example.project_ui_implementation"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.project_ui_implementation"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    /**
     * Dependencies added: Libs.volley and Libs.squareup.picasso
     * The former one is used to fecth the data from Google API (Book data)
     * The latter one is used to load the images into our system.
     */

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.firebase.database)
    // Firebase Authentication and Google Sign-In using the version catalog
    implementation(libs.firebase.auth)  // This will refer to the version defined in libs.versions.toml
    implementation(libs.google.auth)     // This will refer to the version defined in libs.versions.toml
    implementation (libs.volley)
    implementation (libs.squareup.picasso)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}