import com.android.build.api.artifact.SingleArtifact
import java.io.FileInputStream
import java.util.Properties


plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
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
    compileSdk = 35

    defaultConfig {
        applicationId = "com.sunil.app"
        minSdk = 21
        targetSdk = 35
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

    buildFeatures {
        buildConfig = true
        viewBinding = true
//        dataBinding = true
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
}

androidComponents.onVariants { variant ->
    val flavorName = variant.flavorName ?: "devDebug" // Default if null
//    val buildTypeName = variant.buildType ?: "debug" // Default if null
    val apkName = "MG App"

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
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.dagger.hilt)
    implementation(libs.hilt.navigation)
    implementation(libs.timber)

}
