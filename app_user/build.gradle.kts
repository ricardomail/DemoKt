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
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
}