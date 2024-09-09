import org.jetbrains.dokka.DokkaConfiguration.Visibility.*
import org.jetbrains.dokka.base.DokkaBase
import org.jetbrains.dokka.base.DokkaBaseConfiguration
import org.jetbrains.dokka.gradle.DokkaTask

plugins {
    id("java")
    id("maven-publish")
    id("org.jetbrains.dokka") version "1.9.20"
    kotlin("jvm")
}

buildscript {
    dependencies {
        classpath("org.jetbrains.dokka:dokka-base:1.9.20")
    }
}

group = "com.tecknobit"
version = "2.2.4"

repositories {
    mavenCentral()
    maven("https://jitpack.io")
    maven("https://repo.clojars.org")
}

dependencies {
    implementation("commons-codec:commons-codec:1.13")
    implementation("com.squareup.okhttp3:okhttp:3.14.6")
    implementation("com.google.zxing:core:3.3.1")
    implementation("com.google.zxing:javase:3.3.1")
    implementation("org.json:json:20231013")
}

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("maven") {
                groupId = "com.tecknobit.apimanager"
                artifactId = "APIManager"
                version = "2.2.4"
                from(components["java"])
            }
        }
    }
}

tasks.withType<DokkaTask>().configureEach {
    outputDirectory.set(layout.projectDirectory.dir("docs"))
    dokkaSourceSets.configureEach {
        sourceRoots.from(file("src/main/java"))
        includeNonPublic.set(true)
        documentedVisibilities.set(setOf(PUBLIC, PROTECTED, PRIVATE))
    }
    pluginConfiguration<DokkaBase, DokkaBaseConfiguration> {
        footerMessage = "(c) 2024 Tecknobit"
    }
}