import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.detekt)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.jlleitschuh.gradle.ktlint)
    alias(libs.plugins.kotlin.compose)
}

val localPropertiesFile = file("../local.properties")
val keystoreProperties = Properties()
if (localPropertiesFile.exists()) {
    localPropertiesFile.inputStream().use { keystoreProperties.load(it) }
}

android {
    namespace = "lmm.moneylog"
    //noinspection GradleDependency
    compileSdk = 36

    defaultConfig {
        applicationId = "lmm.moneylog"
        minSdk = 30
        targetSdk = 36
        versionCode = 1
        versionName = "0.4.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    signingConfigs {
        if (keystoreProperties["key.store"] != null) {
            create("release") {
                storeFile = file(keystoreProperties["key.store"] as String)
                storePassword = keystoreProperties["key.storePassword"] as String
                keyAlias = keystoreProperties["key.alias"] as String
                keyPassword = keystoreProperties["key.password"] as String
            }
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            if (signingConfigs.names.contains("release")) {
                signingConfig = signingConfigs.getByName("release")
            }
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
    implementation(project(":ui"))
    implementation(platform(libs.compose.bom))
    implementation(libs.bundles.compose)
    implementation(libs.koin.androidx.compose)
    detektPlugins(libs.compose.rules)
    androidTestImplementation(libs.bundles.android.test)
    testImplementation(libs.bundles.test)
}
