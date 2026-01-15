import java.util.Properties
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.sqldelight)
    alias(libs.plugins.kotlinSerialization)
}

kotlin {
    androidTarget {
        compilations.all {
            compilerOptions.configure {
                jvmTarget.set(JvmTarget.JVM_17)
            }
        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)

            implementation(libs.ktor.client.android)

            implementation(libs.sqldelight.driver.android)

            implementation(libs.androidx.navigation.compose)

            implementation(compose.materialIconsExtended)

            implementation(libs.koin.android)
            implementation(libs.koin.androidx.compose)

            implementation(libs.coil.compose)


        }

        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)

            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)

            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.ktor.client.logging)

            implementation(libs.sqldelight.runtime)
            implementation(libs.sqldelight.coroutines.extensions)
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

android {
    namespace = "eone.grim.moviebrowser"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    buildFeatures {
        buildConfig = true
    }

    defaultConfig {
        applicationId = "eone.grim.moviebrowser"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"

        val props = Properties()
        val localPropsFile = rootProject.file("local.properties")
        if (localPropsFile.exists()) {
            props.load(localPropsFile.inputStream())
        }

        val token = props.getProperty("TMDB_TOKEN") ?: ""
        val baseUrl = props.getProperty("TMDB_BASE_URL") ?: ""
        buildConfigField("String", "TMDB_TOKEN", "\"$token\"")
        buildConfigField("String", "TMDB_BASE_URL", "\"$baseUrl\"")
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }


    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

sqldelight {
    databases {
        create("AppDatabase") {
            packageName.set("eone.grim.moviebrowser.data.db")

            srcDirs("src/androidMain/sqldelight")
        }
    }
}

dependencies {
    debugImplementation(compose.uiTooling)

}
