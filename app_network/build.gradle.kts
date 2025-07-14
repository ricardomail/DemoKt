plugins {
    alias(libs.plugins.android.library)
}
apply(from = "../dependencies.gradle")

android {
    namespace = "com.oasis.app_network"
    defaultConfig {
        minSdk = 21
    }
}

dependencies {

    //oKHttp
    implementation(libs.okhttp)
    //noinspection UseTomlInstead
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.0")
    //retrofit
    api(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(project(":app_common"))
}