buildscript {
    // These repositories are used when building your project.
    // They are NOT used to find your module's dependencies.
    apply(from = "$rootDir/gradle/repositories.gradle", to = buildscript)

    dependencies {
        classpath(libs.misc.buildGradle)
        classpath(libs.misc.meliBasePlugin)
        classpath(libs.misc.meliBaseApp)
        classpath(libs.misc.mavenGradle)
        classpath(libs.misc.kotlinGradle)
        classpath(libs.misc.dexCountGradlePlugin)
    }
}

plugins {
    id("mercadolibre.gradle.config.base")
}
apply(from = "$rootDir/gradle/dependencies-substitution.gradle")

// Variables
val appId = properties["applicationId"].toString()
val appVersion = properties["versionName"].toString()

allprojects {
    group = appId
    version = appVersion
}
