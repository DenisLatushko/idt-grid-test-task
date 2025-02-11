package net.idt.testtask.buildplugin

import net.idt.testtask.buildplugin.config.kotlinJavaLibConfig
import net.idt.testtask.buildplugin.utils.apply
import net.idt.testtask.buildplugin.utils.javaLibraryPluginId
import net.idt.testtask.buildplugin.utils.kotlinJvm
import net.idt.testtask.buildplugin.utils.versionCatalog
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension

/**
 * Set up the kotlin/java library module
 */
class KotlinJavaGradlePlugin: Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply(
                versionCatalog.javaLibraryPluginId,
                versionCatalog.kotlinJvm.pluginId
            )

            extensions.configure<KotlinJvmProjectExtension>(kotlinJavaLibConfig)
        }
    }
}
