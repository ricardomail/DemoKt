plugins {
    alias(libs.plugins.android.library)
}
apply(from = "../dependencies.gradle")
android {
    namespace = "com.oasis.app_me"

}

dependencies {
    implementation(project(":app_common"))
    implementation(project(":app_network"))

}