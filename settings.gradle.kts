pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
    plugins {
        id("dev.flutter.flutter-gradle-plugin") version "1.0.0" // 版本需匹配 Flutter SDK
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
    repositories {
        google()
        mavenCentral()
        maven {
            url = uri("https://jitpack.io")
        }
        maven("https://storage.googleapis.com/download.flutter.io")
    }
}

rootProject.name = "MyDemoKt"
include(":app")
include(":app_common")
include(":app_home")
include(":app_user")
include(":app_me")
include(":app_network")
include(":app_project")
include(":app_navigation")
include(":app_web")
apply(from = "../hybird_module/.android/include_flutter.groovy")
