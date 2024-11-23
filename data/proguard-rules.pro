-keepclassmembers @androidx.room.Entity class * { *; }
-keepclassmembers @androidx.room.Dao class * { *; }

-keepclassmembers class * {
    @androidx.room.* <methods>;
}

-keep class lmm.moneylog.data.** { *; }

-dontwarn java.lang.invoke.StringConcatFactory
