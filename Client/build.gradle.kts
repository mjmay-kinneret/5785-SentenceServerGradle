plugins {
    id("java")
    id("application")
}

group = "kinneret.sentenceServer"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = "kinneret.sentenceServer.ClientMain"
    }
}

application{
    mainClass.set("kinneret.sentenceServer.ClientMain")
}