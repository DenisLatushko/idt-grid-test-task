plugins {
    alias(libs.plugins.build.kotlinJava.lib)
}

dependencies {
    implementation(libs.koin.core)

    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.kotlinx.coroutines.test)
}
