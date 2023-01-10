plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
}

repositories {
    maven("https://maven.solo-studios.ca/releases")
    mavenCentral()
}

dependencies {
    api(libs.kotlin.stdlib)
    
    api(libs.bundles.kotlinx.coroutines)
    api(libs.bundles.kotlinx.serialization)
    
    api(libs.slf4j)
    implementation(libs.slf4k)
    
    testImplementation(libs.kotlin.test)
    testImplementation(libs.bundles.junit)
}
