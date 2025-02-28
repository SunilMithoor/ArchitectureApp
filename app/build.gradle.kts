import java.io.FileInputStream
import java.util.Properties


plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.dokka)
    alias(libs.plugins.hilt.android)
    kotlin("kapt")
}



apply(from = "../build.flavors.kts")

// Function to load version properties
fun loadVersionProperties(fileName: String): Properties {
    val versionPropsFile = rootProject.file(fileName)
    val versionProps = Properties()
    if (versionPropsFile.exists()) {
        versionProps.load(FileInputStream(versionPropsFile))
    }
    return versionProps
}

android {
    namespace = "com.sunil.app"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.sunil.app"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        multiDexEnabled = true
        vectorDrawables.useSupportLibrary = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.kotlinCompiler.get()
    }


    buildFeatures {
        buildConfig = true
//        viewBinding = true
        compose = true
        dataBinding = true
    }

    // Load keystore properties
    val keystoreProperties = loadVersionProperties("keystore.properties")

    signingConfigs {
        getByName("debug") {
            storeFile = rootProject.file(keystoreProperties["debugStoreFile"] as String)
            storePassword = keystoreProperties["debugStorePassword"] as String
            keyAlias = keystoreProperties["debugKeyAlias"] as String
            keyPassword = keystoreProperties["debugKeyPassword"] as String
        }

        create("release") {
            storeFile = rootProject.file(keystoreProperties["releaseStoreFile"] as String)
            storePassword = keystoreProperties["releaseStorePassword"] as String
            keyAlias = keystoreProperties["releaseKeyAlias"] as String
            keyPassword = keystoreProperties["releaseKeyPassword"] as String
        }
    }

    buildTypes {
        getByName("debug") {
            signingConfig = signingConfigs.getByName("debug") // ✅ Ensure debug uses the signing config
            isDebuggable = true
            isShrinkResources = false
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        getByName("release") {
            signingConfig = signingConfigs.getByName("release") // ✅ Ensure release uses the signing config
            lint {
                checkReleaseBuilds = false
                abortOnError = false
            }
            isDebuggable = false
            isShrinkResources = true
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }


    packaging {
        resources {
            excludes += setOf(
                "META-INF/LICENSE.md",
                "META-INF/NOTICE.md",
                "META-INF/LICENSE.txt",
                "META-INF/NOTICE.txt"
            )
        }
    }


}

androidComponents.onVariants { variant ->
    val flavorName = variant.flavorName ?: "devDebug" // Default if null
//    val buildTypeName = variant.buildType ?: "debug" // Default if null
//    val apkName = "MG App"

    // Determine correct flavor key
    val flavorKey = when {
        flavorName.startsWith("dev") -> "dev"
        flavorName.startsWith("stage") -> "stage"
        flavorName.startsWith("prod") -> "prod"
        else -> "dev"
    }

    // Load version properties for the selected flavor
    val versionProps = loadVersionProperties("version-$flavorKey.properties")

    // Set version properties dynamically
    val versionCode = versionProps["VERSION_CODE"]?.toString()?.toIntOrNull() ?: 1
    val versionMajor = versionProps["VERSION_MAJOR"]?.toString() ?: "1"
    val versionMinor = versionProps["VERSION_MINOR"]?.toString() ?: "0"
    val versionPatch = versionProps["VERSION_PATCH"]?.toString() ?: "0"
    val versionName = "$versionMajor.$versionMinor.$versionPatch"

    variant.outputs.forEach { output ->
        output.versionCode.set(versionCode)
        output.versionName.set(versionName)
    }


}

dependencies {


    //testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    //libraries
    //kotlin
    implementation(libs.androidx.core.ktx)
    //androidX
    implementation(libs.androidx.appcompat)
//    implementation(libs.material)
    implementation(libs.hilt.android)
    implementation(libs.timber)
    implementation(libs.gson)
    implementation(libs.room)
    implementation(libs.retrofit2)
    implementation(libs.converter.gson)
    implementation(libs.converter.scalars)
    implementation(libs.retrofit2.kotlinx.serialization.converter)
    implementation(libs.logging.interceptor)
    implementation(libs.swiperefreshlayout)
    implementation(libs.multidex)
    implementation(libs.recyclerview)
    implementation(libs.constraintlayout)
    implementation(libs.fragment.ktx)
    implementation(libs.navigation.fragment.ktx)
    implementation(libs.navigation.ui.ktx)
    implementation(libs.lifecycle.extensions)
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.lifecycle.livedata.ktx)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.coroutines.play.services)
    implementation(libs.databinding.runtime)
//    implementation(libs.compose.material3)
    implementation(libs.androidx.material3.android)
    implementation(libs.compose.material)
    implementation(libs.compose.material.icons.extended)
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.test.manifest)
    implementation(libs.compose.ui.util)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.compose.foundation)
    implementation(libs.compose.animation)
    implementation(libs.compose.runtime)
    implementation(libs.compose.runtime.saveable)

    implementation(libs.accompanist.systemuicontroller)
    implementation(libs.navigation.compose)
    implementation(libs.coil.compose)
    implementation(libs.hilt.navigation.compose)
//    implementation(libs.font.awesome.compose)


    implementation(platform(libs.compose.bom))

    //kapt
    kapt(libs.hilt.compiler)

    //annotation
    annotationProcessor(libs.room.compiler)

    //debugImplementation
    debugImplementation(libs.chucker.library)
    debugImplementation(libs.compose.ui.tooling)

    //release
    releaseImplementation(libs.chucker.library.no.op)

    //project
    implementation(project(":custom_snackbar"))

}
