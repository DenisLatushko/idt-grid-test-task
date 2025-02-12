package net.idt.testtask.buildplugin.utils

import org.gradle.api.JavaVersion
import org.gradle.jvm.toolchain.JavaLanguageVersion

fun JavaVersion.toInt(): Int = JavaLanguageVersion.of(majorVersion).asInt()
