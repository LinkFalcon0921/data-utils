
plugins {
    `java-library`
    `maven-publish`
}

group = "com.flintCore"
version = "1.2.2"

// Variables
val javaVersion = JavaVersion.VERSION_1_9

java {
    sourceCompatibility = javaVersion
    targetCompatibility = javaVersion
}

repositories {
    mavenLocal()
    google()
    mavenCentral()
}

publishing {
    repositories {
        mavenLocal()
        //.run {
        //            println("URl of repo: ${this.url}")
        //        }
    }

    publications {
        create<MavenPublication>("utilsLocal") {
            groupId = project.group.toString()
            version = project.version as String
            pom.packaging = "jar"
            from(components["java"])
        }
    }
}

dependencies {
    api("org.apache.commons:commons-lang3:3.14.0")

    // LOMBOK
    compileOnly("org.projectlombok:lombok:1.18.32")
    annotationProcessor("org.projectlombok:lombok:1.18.32")
    testCompileOnly("org.projectlombok:lombok:1.18.32")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.32")

    // Test Implementations
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}