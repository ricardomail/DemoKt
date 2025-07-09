plugins {
    alias(libs.plugins.android.library)
}
apply(from = "../dependencies.gradle")
android {
    namespace = "com.oasis.app_home"

}

dependencies {
    implementation(project(":app_common"))
}