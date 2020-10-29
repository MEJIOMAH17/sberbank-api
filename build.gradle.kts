plugins {
    id("maven-publish")
    java
}

repositories {
    jcenter()
}

dependencies {
    implementation("io.github.rybalkinsd:kohttp:0.11.1")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.3.71")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:2.9.8")
    testImplementation("junit:junit:4.13.1")
}

group = "ru.mekosichkin"
version = "1.0-SNAPSHOT"
description = "sberbank-api"
java.sourceCompatibility = JavaVersion.VERSION_1_8

java {
    withSourcesJar()
}

publishing{
    repositories {
        maven {
            url = uri("https://maven.pkg.github.com/MEJIOMAH17/sberbank-api")
            credentials {
                username = "MEJIOMAH17"
                password = githubToken
            }
        }
    }
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }
}
