plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
    
    java
    application
    distribution
    
    // alias(libs.plugins.axion.release)
    
    alias(libs.plugins.shadow)
}

// scmVersion {
//     // configure scmVersion here
// }

// allprojects {
//     project.version = scmVersion.version
// }

repositories {
    mavenCentral()
    // No jitpack. Jitpack bad.
    // maven("https://jitpack.io/")
    // It's down rn because I forgor 💀 to add a systemd service, I'll get it up later lol (tho idk if we even need it)
    // maven("https://maven.solo-studios.ca/release")
}

dependencies {
    api(libs.bundles.kotlin.base)
    
    api(libs.bundles.kotlinx.serialization.base)
    
    
    testImplementation(libs.bundles.kotlin.test)
    testImplementation(libs.bundles.junit)
}
