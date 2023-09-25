plugins {
    id("mercadolibre.gradle.config.app")
}
/*
    These configurations are repository customizations, the variables needed to compile are provided by the
    Gradle Plugin through the AndroidConfigurer class.

    https://furydocs.io/mobile-gradle-android/latest/guide/#/
 */
val appId = properties["applicationId"].toString()
val appVersionCode = properties["versionCode"].toString().toInt()
val appVersionName = properties["versionName"].toString()

android {
    namespace = "com.mercadolibre.android.pipsearch"

    defaultConfig {
        applicationId = appId
        versionCode = appVersionCode
        versionName = appVersionName
    }

    resourcePrefix = "pip_search_app"

    buildTypes {
        getByName("debug") {
            // You can set different values for the same variable depending on the build type. These can be accessed by BuildConfig class
        }

        create("mds") {
            initWith(buildTypes.getByName("debug"))
        }

        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }

    testOptions {
        unitTests.apply {
            isIncludeAndroidResources = true
            isReturnDefaultValues = true
        }
    }

}

dependencies {
    implementation(libs.misc.appCompat)

    implementation(libs.meli.commonsCore)
    implementation(libs.meli.commonsUtils)
    implementation(libs.meli.configurationManager)
    implementation(libs.meli.ui)

    // Everest force
    api(enforcedPlatform(libs.meli.everest))
    // Everest bundles
    implementation(everestLibs.bundles.ui)
    implementation(everestLibs.bundles.data)
    implementation(everestLibs.bundles.core)

    // Unit testing dependencies
    testImplementation(libs.meli.testingCore)
}
