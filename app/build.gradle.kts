import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.detekt)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.jlleitschuh.gradle.ktlint)
    alias(libs.plugins.kotlin.compose)
}

val keystoreProperties = Properties()
file("../local.properties").inputStream().use { keystoreProperties.load(it) }

android {
    namespace = "lmm.moneylog"
    //noinspection GradleDependency
    compileSdk = 34

    defaultConfig {
        applicationId = "lmm.moneylog"
        minSdk = 28
        //noinspection OldTargetApi
        targetSdk = 34
        versionCode = 1
        versionName = "0.2.4b"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    signingConfigs {
        create("release") {
            storeFile = file(keystoreProperties["key.store"] as String)
            storePassword = keystoreProperties["key.storePassword"] as String
            keyAlias = keystoreProperties["key.alias"] as String
            keyPassword = keystoreProperties["key.password"] as String
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            signingConfig = signingConfigs.getByName("release")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.14"
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

detekt {
    buildUponDefaultConfig = true
    allRules = false
    config.setFrom(
        files(
            "$rootDir/gradle/detekt.yml",
            "$rootDir/gradle/detekt-compose.yml"
        )
    )
}

dependencies {
    implementation(project(":data"))
    implementation(platform(libs.compose.bom))
    implementation(libs.bundles.compose)
    implementation(libs.koin.androidx.compose)
    detektPlugins(libs.compose.rules)
    androidTestImplementation(libs.bundles.android.test)
    testImplementation(libs.bundles.test)
}
