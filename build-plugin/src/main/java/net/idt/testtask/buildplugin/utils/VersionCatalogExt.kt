package net.idt.testtask.buildplugin.utils

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.plugin.use.PluginDependency

internal val Project.versionCatalog: VersionCatalog
    get() = extensions.getByType(VersionCatalogsExtension::class.java).named("libs")

internal val VersionCatalog.androidApplicationPlugin: PluginDependency
    get() = findPlugin("android.application").get().get()

internal val VersionCatalog.kotlinAndroidPlugin: PluginDependency
    get() = findPlugin("kotlin.android").get().get()

internal val VersionCatalog.kotlinCompose: PluginDependency
    get() = findPlugin("kotlin.compose").get().get()

internal val VersionCatalog.javaLibraryPluginId: String
    get() = "java-library"

internal val VersionCatalog.kotlinJvm: PluginDependency
    get() = findPlugin("jetbrains.kotlin.jvm").get().get()
