plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
}

dependencies {
    dependencies {
        compileOnly(libs.gradle.plugin.android)
        compileOnly(libs.gradle.plugin.android.kotlin)
        compileOnly(libs.gradle.plugin.compose)
    }
}

gradlePlugin {
    plugins.register("android-app-build-gradle-plugin") {
        id = "android-app-build-gradle-plugin"
        version = "1.0.0"
        group = "net.idt.testtask.plugin"
        implementationClass = "net.idt.testtask.buildplugin.AndroidAppGradlePlugin"
    }

    plugins.register("kotlin-java-lib-build-gradle-plugin") {
        id = "kotlin-java-lib-build-gradle-plugin"
        version = "1.0.0"
        group = "net.idt.testtask.plugin"
        implementationClass = "net.idt.testtask.buildplugin.KotlinJavaGradlePlugin"
    }
}
