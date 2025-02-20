android {
    flavorDimensions += "environment"

    productFlavors {
        create("dev") {
            dimension = "environment"
            applicationIdSuffix = ".dev"
            manifestPlaceholders["appName"] = "App Dev"
            buildConfigField("String", "BASE_URL", "\"https://catfact.ninja\"")
        }
        create("stage") {
            dimension = "environment"
            applicationIdSuffix = ".stage"
            manifestPlaceholders["appName"] = "App Stage"
            buildConfigField("String", "BASE_URL", "\"https://catfact.ninja\"")
        }
        create("prod") {
            dimension = "environment"
            manifestPlaceholders["appName"] = "App Prod"
            buildConfigField("String", "BASE_URL", "\"https://catfact.ninja\"")
        }
    }
}
