plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}
apply(from = "../dependencies.gradle")

android {
    namespace = "com.oasis.mydemokt"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.oasis.mydemokt"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}

dependencies {

    implementation(project(":app_common"))
    implementation(project(":app_home"))
    implementation(project(":app_user"))
    implementation(project(":app_me"))
    implementation(project(":app_project"))
    implementation(project(":app_navigation"))
    implementation(project(":app_network"))
    implementation(project(":app_web")) // 忘记引入了，导致一直匹配不到路由
}