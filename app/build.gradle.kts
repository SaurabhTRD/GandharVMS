plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
}
android {
    namespace = "com.android.gandharvms"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.android.gandharvms"
        minSdk = 28
        targetSdk = 33
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    viewBinding {
      enable = true
    }
    buildFeatures {
        viewBinding = true
    }
}
dependencies {
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.firebase:firebase-storage-ktx:20.2.1")
    implementation("com.google.firebase:firebase-firestore-ktx:24.9.0")
    implementation("com.google.firebase:firebase-auth-ktx:22.1.2")
    implementation("com.google.firebase:firebase-messaging:23.3.1")
    implementation("com.android.volley:volley:1.2.1")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.9.0")
    implementation ("androidx.core:core-ktx:1.12.0")
    implementation("androidx.activity:activity:1.8.0")


//    implementation("androidx.activity:activity:1.8.0")
//    implementation("com.google.firebase:firebase-firestore:24.9.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    implementation(platform("com.google.firebase:firebase-bom:32.3.1"))

    implementation("com.google.firebase:firebase-analytics-ktx")
    implementation("com.google.firebase:firebase-database-ktx")

   /*implementation("androidx.recyclerview:recyclerview:1.3.1")*/

    implementation( "com.github.dhaval2404:imagepicker:2.1")
    implementation ("com.squareup.picasso:picasso:2.8")
    // Apache POI
    implementation ("org.apache.poi:poi:4.1.2")
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")

    implementation ("io.github.muddz:styleabletoast:2.4.0")
    implementation ("com.github.Spikeysanju:MotionToast:1.1")
    implementation ("com.github.GrenderG:Toasty:1.5.2")
//    implementation ("com.evrencoskun.library:tableview:0.8.9")
    implementation ("com.github.evrencoskun:TableView:v0.8.9.4")

    implementation ("androidx.recyclerview:recyclerview:1.1.0")
    // For control over item selection of both touch and mouse driven selection
    implementation ("androidx.recyclerview:recyclerview-selection:1.1.0")

    implementation("commons-io:commons-io:2.11.0")







}