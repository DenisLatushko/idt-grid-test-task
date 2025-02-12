package net.idt.testtask.buildplugin.config

import net.idt.testtask.buildplugin.utils.toInt
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension

/**
 * Set up common settings to kotlin/java lib gradle modules
 */
internal val kotlinJavaLibConfig: (KotlinJvmProjectExtension) -> Unit
    get() = { ext ->
        ext.apply {
            jvmToolchain(jdkVersion = JavaConfig.javaVersion.toInt())
        }
    }
