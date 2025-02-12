package net.idt.testtask.buildplugin.utils

import org.gradle.api.plugins.PluginManager

internal fun PluginManager.apply(vararg pluginIds: String) =
    pluginIds.forEach { pluginId -> apply(pluginId) }
