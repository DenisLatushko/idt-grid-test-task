package net.idt.testtask.buildplugin.config

import org.gradle.api.JavaVersion
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

internal object JavaConfig {
    val jvmTarget = JvmTarget.JVM_17
    val javaVersion = JavaVersion.VERSION_17
}

internal object AndroidConfig {
    const val COMPILE_SDK = 35
    const val MIN_SDK = 26
}
