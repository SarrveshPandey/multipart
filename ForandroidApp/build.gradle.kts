plugins {
    id("com.android.application")
    kotlin("android")
    id ("kotlin-android-extensions")
    id("com.google.gms.google-services")


}

android {
    compileSdk = 31
    defaultConfig {
        applicationId = "com.solution.s.formultiplateform.android"
        minSdk = 21
        targetSdk = 31
        versionCode = 1
        versionName = "1.0"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
}

dependencies {

    implementation ("com.squareup.picasso:picasso:2.71828")


    implementation(project(":shared"))
    implementation("com.google.android.material:material:1.4.0")
    implementation("androidx.appcompat:appcompat:1.3.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.0")
    implementation("com.google.firebase:firebase-auth:21.0.1")
    implementation("com.google.firebase:firebase-common-ktx:20.0.0")
    implementation("com.google.firebase:firebase-auth-ktx:21.0.1")
    implementation("com.google.firebase:firebase-firestore-ktx:24.0.1")
    implementation("com.google.firebase:firebase-storage-ktx:20.0.0")
    implementation("com.google.firebase:firebase-database-ktx:20.0.3")
}