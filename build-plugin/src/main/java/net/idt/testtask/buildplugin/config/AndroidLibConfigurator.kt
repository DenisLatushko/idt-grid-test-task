package net.idt.testtask.buildplugin.config

import com.android.build.api.dsl.LibraryExtension
import net.idt.testtask.buildplugin.config.AndroidConfig.COMPILE_SDK
import net.idt.testtask.buildplugin.config.AndroidConfig.MIN_SDK
import net.idt.testtask.buildplugin.config.JavaConfig.javaVersion
import net.idt.testtask.buildplugin.config.JavaConfig.jvmTarget
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.kotlinExtension

/**
 * Set up common settings to Android Library gradle modules
 */
internal val Project.androidLibConfig: (LibraryExtension) -> Unit
    get() = { ext ->
        ext.apply {
            compileSdk = COMPILE_SDK

            defaultConfig {
                minSdk = MIN_SDK
                targetSdk = COMPILE_SDK
                testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
            }

            buildFeatures {
                buildConfig = true
            }

            buildTypes {
                debug {
                    isMinifyEnabled = false
                    isJniDebuggable = true
                    isDefault = true
                }

                release {
                    isMinifyEnabled = true
                    proguardFiles(
                        getDefaultProguardFile("proguard-android-optimize.txt"),
                        "proguard-rules.pro"
                    )
                }
            }

            compileOptions {
                sourceCompatibility = javaVersion
                targetCompatibility = javaVersion
            }

            kotlinExtension.jvmToolchain(jvmTarget.target.toInt())
        }
    }
