import org.jetbrains.dokka.gradle.DokkaTask
import kotlin.math.max

plugins {
    java
    signing
    `maven-publish`
    `java-library`
    
    alias(libs.plugins.kotlin.jvm) //apply false
    
    alias(libs.plugins.dokka)
    
    alias(libs.plugins.axion.release)
}

scmVersion {
    // configure scmVersion here
}

allprojects {
    project.version = rootProject.scmVersion.version
    
    repositories {
        maven("https://maven.solo-studios.ca/releases")
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "java")
    apply(plugin = "signing")
    apply(plugin = "maven-publish")
    apply(plugin = "java-library")
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "org.jetbrains.dokka")
    
    group = "com.github.scribedocs"
    
    repositories {
        mavenCentral()
        maven("https://maven.solo-studios.ca/releases")
    }
    
    java {
        withJavadocJar()
        withSourcesJar()
    }
    
    kotlin {
        target.compilations.configureEach {
            kotlinOptions {
                jvmTarget = "1.8"
                apiVersion = "1.8"
                languageVersion = "1.8"
            }
        }
    }
    
    tasks {
        test {
            failFast = false
            maxParallelForks = max(Runtime.getRuntime().availableProcessors() - 1, 1)
        
            useJUnitPlatform()
        }
    
        withType<Javadoc>().configureEach {
            options {
                encoding = "UTF-8"
            }
        }
    
        withType<Jar>().configureEach {
            metaInf {
                from(rootProject.file("LICENSE"))
            }
        }
    
        val jar by getting(Jar::class)
        val dokkaHtml by getting(DokkaTask::class)
        val javadocJar by getting(Jar::class) {
            dependsOn(dokkaHtml)
            archiveClassifier.set("javadoc")
            from(dokkaHtml.outputDirectory)
        }
        val sourcesJar by getting(Jar::class) {
            archiveClassifier.set("sources")
            from(sourceSets["main"].allSource)
        }
        
        build {
            dependsOn(jar, javadocJar, sourcesJar)
            dependsOn(withType<Jar>())
        }
    }
    
    publishing {
        publications {
            create<MavenPublication>("maven") {
                from(components["java"])
                
                version = version.toString()
                groupId = group.toString()
                artifactId = "${rootProject.name}-${project.name}"
                
                pom {
                    val projectOrg = "scribe-docs"
                    val projectRepo = "scribe-docs"
                    val githubBaseUri = "github.com/$projectOrg/$projectRepo"
                    val githubUrl = "https://$githubBaseUri"
    
                    name.set("Scribe Docs")
                    description.set("Scribe Docs is a documentation website generator") // TODO better description
                    url.set(githubUrl)
    
                    inceptionYear.set("2023")
    
                    licenses {
                        license {
                            name.set("MIT License")
                            url.set("https://mit-license.org/")
                        }
                    }
                    
                    developers {
                        developer {
                            id.set("solonovamax")
                            name.set("solonovamax")
                            email.set("solonovamax@12oclockpoint.com")
                            url.set("https://github.com/solonovamax")
                        }
                    }
                    issueManagement {
                        system.set("GitHub")
                        url.set("$githubUrl/issues")
                    }
                    scm {
                        connection.set("scm:git:$githubUrl.git")
                        developerConnection.set("scm:git:ssh://$githubBaseUri.git")
                        url.set(githubUrl)
                    }
                }
            }
        }
    }
    
    repositories {
        // TODO: do we want to publish to maven central (sonatype)
        //       if so, we need to either
        //          1. Apply for a group
        //          2. Use an existing group from a contributor
        //             (eg. `ca.solo-studios`, or `gay.solonovamax`, both owned by solonovamax)
        // maven {
        //     name = "Sonatype"
        //
        //     val releasesUrl = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/") // releases repo
        //     val snapshotUrl = uri("https://s01.oss.sonatype.org/content/repositories/snapshots/") // snapshot repo
        //     url = if (isSnapshot) snapshotUrl else releasesUrl
        //
        //     credentials(PasswordCredentials::class)
        // }
        
        maven {
            name = "SoloStudios"
            
            val releasesUrl = uri("https://maven.solo-studios.ca/releases/")
            val snapshotUrl = uri("https://maven.solo-studios.ca/snapshots/")
            url = if (isSnapshot) snapshotUrl else releasesUrl
            
            credentials(PasswordCredentials::class)
            authentication { // publishing doesn't work without this for some reason (reposilite)
                create<BasicAuthentication>("basic")
            }
        }
    }
    
    signing {
        useGpgCmd()
        sign(publishing.publications)
    }
}

val Project.isSnapshot: Boolean
    get() = version.toString().endsWith("-SNAPSHOT")
