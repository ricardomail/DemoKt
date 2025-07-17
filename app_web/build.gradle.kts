plugins {
    alias(libs.plugins.android.library)
}
apply(from = "../dependencies.gradle")
android {
    buildFeatures {
        viewBinding = true
    }
    namespace = "com.oasis.app_web"

}

dependencies {
    implementation(project(":app_common"))
    implementation(project(":app_network"))

}