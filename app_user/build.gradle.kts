plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}
apply(from = "../dependencies.gradle")
android {
    buildFeatures {
        viewBinding = true
    }
    namespace = "com.oasis.app_user"

}

dependencies {
    implementation(project(":app_common"))
    implementation(project(":app_network"))

}