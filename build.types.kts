android {
    buildTypes {
        getByName("debug") {
            signingConfig = signingConfigs.getByName("debug")
            debuggable = true
            shrinkResources = false
            minifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        getByName("release") {
            signingConfig = signingConfigs.getByName("release")
            lint {
                checkReleaseBuilds = false
                abortOnError = false
            }
            debuggable = false
            shrinkResources = true
            zipAlignEnabled = true
            minifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}
