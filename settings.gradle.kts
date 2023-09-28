// This is used to get the version for everest that is defined in the toml but it has to be used in the gradle configuration, it is a solution that works perfect.
// Created by: Agustin Ferreira Guarroz.
fun getVersionForEverest(): String {
    val tomlFile = File("$rootDir/gradle/libs.versions.toml")
    val versionLine = tomlFile.readLines().find { it.contains("everestVersion") }
    val versionValue = versionLine?.split("=")?.get(1)?.trim()?.replace("\"", "")

    return versionValue
        ?: throw IllegalStateException("everestVersion not found in the TOML file.")
}

// Get the everest version using the getEverestVersion() function
val everestVersion = getVersionForEverest()

dependencyResolutionManagement {
    repositories {
        apply(from = "$rootDir/gradle/repositories.gradle", to = dependencyResolutionManagement)
    }
    versionCatalogs {
        create("everestLibs") {
            from("com.mercadolibre.android.everest:catalog:$everestVersion")
        }
    }
}


buildscript {
    // have the same repositories applied to buildscript…
    apply(from = "$rootDir/gradle/repositories.gradle", to = buildscript)

    // This function is used to get any version value from the toml.
    fun getVersionFromTomlFile(propertyName: String): String {
        val tomlFile = File("$rootDir/gradle/libs.versions.toml")
        val versionLine = tomlFile.readLines().find { it.contains(propertyName) }
        val versionValue = versionLine?.split("=")?.get(1)?.trim()?.replace("\"", "")

        return versionValue
            ?: throw IllegalStateException("$propertyName not found in the TOML file.")
    }

    dependencies {
        val meliGradleVersion = getVersionFromTomlFile("meliPluginGradleVersion")
        classpath("com.mercadolibre.android.gradle:baseplugin:$meliGradleVersion")
    }
}

include(":app")
rootProject.name = "pip-search-meli-app"

apply(plugin = "mercadolibre.gradle.config.settings")
