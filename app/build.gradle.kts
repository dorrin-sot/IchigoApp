plugins {
  alias(libs.plugins.android.application)
  alias(libs.plugins.kotlin.android)
  alias(libs.plugins.kotlin.compose)
  alias(libs.plugins.devtools.ksp)
  alias(libs.plugins.dagger.hilt)
}

android {
  namespace = "com.example.ichigoapp"
  compileSdk = 35

  defaultConfig {
    applicationId = "com.example.ichigoapp"
    minSdk = 24
    targetSdk = 35
    versionCode = 1
    versionName = "1.0"

    testInstrumentationRunner = "com.example.ichigoapp.CustomTestRunner"
  }

  buildTypes {
    release {
      isMinifyEnabled = false
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }
  }
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
  }
  kotlinOptions {
    jvmTarget = "11"
  }
  buildFeatures {
    compose = true
  }
  packaging {
    resources {
      excludes += "/META-INF/LICENSE-notice.md"
      excludes += "META-INF/LICENSE.md"
    }
  }

  sourceSets {
    getByName("main") {
      java.srcDirs("src/sharedTest/java")
    }
  }
}

dependencies {
  implementation(libs.androidx.core.ktx)
  implementation(libs.androidx.lifecycle.runtime.ktx)
  implementation(libs.androidx.activity.compose)
  implementation(platform(libs.androidx.compose.bom))
  implementation(libs.androidx.ui)
  implementation(libs.androidx.ui.graphics)
  implementation(libs.androidx.ui.tooling.preview)
  implementation(libs.androidx.material3)
  implementation(libs.retrofit2.retrofit)
  implementation(libs.converter.gson)
  implementation(libs.kotlinx.coroutines.android)
  implementation(libs.kotlinx.coroutines.core)
  implementation(libs.androidx.runtime.livedata)
  implementation(libs.androidx.lifecycle.livedata.ktx)
  implementation(libs.androidx.lifecycle.viewmodel.compose)
  implementation(libs.androidx.room.runtime)
  testImplementation(libs.junit)
  testImplementation(libs.core.testing)
  implementation(libs.androidx.material.icons.extended)
  implementation(libs.androidx.material.icons.core)
  testImplementation(libs.kotlinx.coroutines.test)
  testImplementation(libs.mockito.core)
  testImplementation(libs.mockito.kotlin)
  testImplementation(libs.mockk)
  testImplementation(libs.retrofit.mock)
  testImplementation(libs.junit.jupiter)
  androidTestImplementation(libs.mockk.android)
  androidTestImplementation(libs.hilt.android.testing)
  androidTestImplementation(libs.androidx.runner)
  androidTestUtil(libs.androidx.orchestrator)
  ksp(libs.androidx.room.compiler)
  implementation(libs.androidx.room.ktx)
  ksp(libs.hilt.compiler)
  kspAndroidTest(libs.hilt.compiler)
  implementation(libs.hilt.android)
  implementation(libs.androidx.hilt.navigation.compose)
  debugImplementation(libs.androidx.ui.tooling)
  androidTestImplementation(libs.androidx.ui.test.junit4)
  debugImplementation(libs.androidx.ui.test.manifest)
}