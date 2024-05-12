plugins {
    `java-library`
    publishing
}

group = "com.flintCore"
version = "1.0.2"

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