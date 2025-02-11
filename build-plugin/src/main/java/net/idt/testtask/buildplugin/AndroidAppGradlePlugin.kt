package net.idt.testtask.buildplugin

import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import net.idt.testtask.buildplugin.config.androidAppConfig
import net.idt.testtask.buildplugin.utils.apply
import net.idt.testtask.buildplugin.utils.getDefaultAppPluginsIds
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

/**
 * Set up the "app" module
 */
class AndroidAppGradlePlugin: Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply(*getDefaultAppPluginsIds())

            extensions.configure<BaseAppModuleExtension>(androidAppConfig)
        }
    }
}
