package net.idt.testtask.buildplugin.utils

import org.gradle.api.Project

internal fun Project.getDefaultAppPluginsIds(): Array<String> = arrayOf(
    versionCatalog.androidApplicationPlugin.pluginId,
    versionCatalog.kotlinAndroidPlugin.pluginId,
    versionCatalog.kotlinCompose.pluginId
)
