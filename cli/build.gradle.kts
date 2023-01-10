plugins {
    
    application
    distribution
    
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
    
    alias(libs.plugins.shadow)
}

application {
    mainClass.set("com.github.scribedocs.MainKt")
}

repositories {
    maven("https://maven.solo-studios.ca/releases")
    mavenCentral()
}

dependencies {
    api(project(":api"))
    
    api(libs.kotlin.stdlib)
    
    api(libs.bundles.kotlinx.coroutines)
    api(libs.bundles.kotlinx.serialization)
    
    api(libs.clikt)
    
    api(libs.slf4j)
    implementation(libs.slf4k)
    
    testImplementation(libs.kotlin.test)
    testImplementation(libs.bundles.junit)
}

tasks {
    named<JavaExec>("run") {
        args = listOf(
                "build",
                "--help",
                     )
    }
}
