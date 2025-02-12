plugins {
    alias(libs.plugins.build.kotlinJava.lib)
}

dependencies {
    implementation(projects.domain)
    implementation(libs.koin.core)
    implementation(libs.kotlinx.coroutines.core)

    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.kotlinx.coroutines.test)
}
