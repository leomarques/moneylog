plugins {
    id ("org.jetbrains.kotlin.jvm")
    alias(libs.plugins.jlleitschuh.gradle.ktlint)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.junit)
}