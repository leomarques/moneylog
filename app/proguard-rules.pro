# Kotlin Coroutines
-keep class kotlinx.coroutines.** { *; }
-keepclassmembers class kotlinx.coroutines.** { *; }

# Compose
-keep class androidx.compose.** { *; }
-keep class androidx.activity.compose.** { *; }
-keep class ** {
    @androidx.compose.runtime.Composable <methods>;
}

-keep class androidx.compose.ui.tooling.preview.Preview {}

# Koin (Dependency Injection)
-keep class org.koin.** { *; }
-keepclassmembers class * {
    @org.koin.* <methods>;
}

# JUnit, Mockk, and Core Testing (exclude if building in release mode)
-dontwarn junit.**
-dontwarn io.mockk.**
-dontwarn androidx.arch.core.executor.testing.*
-dontwarn kotlinx.coroutines.test.*
