package net.idt.testtask.buildplugin

import com.android.build.api.dsl.LibraryExtension
import net.idt.testtask.buildplugin.config.androidLibConfig
import net.idt.testtask.buildplugin.utils.androidLibraryPlugin
import net.idt.testtask.buildplugin.utils.apply
import net.idt.testtask.buildplugin.utils.kotlinAndroidPlugin
import net.idt.testtask.buildplugin.utils.kotlinCompose
import net.idt.testtask.buildplugin.utils.versionCatalog
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

/**
 * Set up an android library module
 */
class AndroidLibGradlePlugin: Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply(
                versionCatalog.androidLibraryPlugin.pluginId,
                versionCatalog.kotlinAndroidPlugin.pluginId
            )

            extensions.configure<LibraryExtension>(androidLibConfig)
        }
    }
}
