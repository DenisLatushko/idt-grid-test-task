package net.idt.testtask.buildplugin.config

import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import net.idt.testtask.buildplugin.config.AndroidConfig.COMPILE_SDK
import net.idt.testtask.buildplugin.config.AndroidConfig.MIN_SDK
import net.idt.testtask.buildplugin.config.JavaConfig.javaVersion
import net.idt.testtask.buildplugin.config.JavaConfig.jvmTarget
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.kotlinExtension

/**
 * Set up common settings to Android gradle modules
 */
internal val Project.androidAppConfig: (BaseAppModuleExtension) -> Unit
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
                compose = true
            }

            packaging {
                resources {
                    excludes += "/META-INF/{AL2.0,LGPL2.1}"
                }
            }

            buildTypes {
                debug {
                    isMinifyEnabled = false
                    isDebuggable = true
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
