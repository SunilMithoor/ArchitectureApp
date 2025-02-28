android {
    flavorDimensions += "environment"

    productFlavors {
        create("dev") {
            dimension = "environment"
            applicationIdSuffix = ".dev"
            manifestPlaceholders["appName"] = "App Dev"
            buildConfigField("String", "BASE_URL_1", "\"https://api.restful-api.dev/\"")
            buildConfigField("String", "BASE_URL_2", "\"https://jsonplaceholder.typicode.com\"")
            buildConfigField("String", "BASE_URL_3", "\"https://movies-mock-server.vercel.app/\"")
        }
        create("stage") {
            dimension = "environment"
            applicationIdSuffix = ".stage"
            manifestPlaceholders["appName"] = "App Stage"
            buildConfigField("String", "BASE_URL_1", "\"https://api.restful-api.dev/\"")
            buildConfigField("String", "BASE_URL_2", "\"https://jsonplaceholder.typicode.com\"")
            buildConfigField("String", "BASE_URL_3", "\"https://movies-mock-server.vercel.app/\"")
        }
        create("prod") {
            dimension = "environment"
            manifestPlaceholders["appName"] = "App Prod"
            buildConfigField("String", "BASE_URL_1", "\"https://api.restful-api.dev/\"")
            buildConfigField("String", "BASE_URL_2", "\"https://jsonplaceholder.typicode.com\"")
            buildConfigField("String", "BASE_URL_3", "\"https://movies-mock-server.vercel.app/\"")
        }
    }
}
