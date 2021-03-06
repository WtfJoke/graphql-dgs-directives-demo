import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.5.3"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	kotlin("jvm") version "1.5.21"
	kotlin("plugin.spring") version "1.5.21"
	id("com.netflix.dgs.codegen") version "5.0.5"
}

group = "com.github.wtfjoke"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	// GraphQL DGS
	implementation(platform("com.netflix.graphql.dgs:graphql-dgs-platform-dependencies:4.5.1"))
	implementation("com.netflix.graphql.dgs:graphql-dgs-spring-boot-starter")

	// GraphQL validation directives - dgs uses graphql-java 16 under the hood, so we use 16.x.x
	implementation("com.graphql-java:graphql-java-extended-validation:16.0.0")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
}

// Needed for graphql-java-extended-validation (spring boot brings 6.2.0.Final)
extra["hibernate-validator.version"] = "6.1.6.Final"

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.generateJava{
	packageName = "com.github.wtfjoke.generated"
	generateClient = true
}