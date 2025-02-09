plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    id("org.springframework.boot") version "3.3.8"
    id("io.spring.dependency-management") version "1.1.7"
    kotlin("plugin.jpa") version "1.9.25"
}

group = "com.jaksimsam1"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

extra["springCloudVersion"] = "2023.0.5"
extra["kotlinJDSLVersion"] = "3.5.2"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-validation")

    /* Spring Cloud */
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")

    /* Kotlin */
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")

    /* Kotlin JDSL */
    implementation("com.linecorp.kotlin-jdsl:jpql-dsl:${property("kotlinJDSLVersion")}")
    implementation("com.linecorp.kotlin-jdsl:jpql-render:${property("kotlinJDSLVersion")}")
    implementation("com.linecorp.kotlin-jdsl:spring-data-jpa-support:${property("kotlinJDSLVersion")}")

    /* Spring Data JPA */
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    /* CommonDto */
    implementation(":common-dto")

    /* Swagger */
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.0.2")

    /* Lombok */
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    /* PostgreSQL */
    runtimeOnly("org.postgresql:postgresql")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testImplementation("org.springframework.security:spring-security-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
    }
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.Embeddable")
}

noArg {
    annotation("javax.persistence.Entity")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
