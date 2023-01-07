import com.google.protobuf.gradle.id
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "3.0.0"
	id("io.spring.dependency-management") version "1.1.0"
	kotlin("jvm") version "1.7.21"
	kotlin("plugin.spring") version "1.7.21"
	id("com.google.protobuf") version "0.9.1"
	id("java")
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
	mavenCentral()
}

dependencies {
	implementation("io.grpc:grpc-kotlin-stub:1.3.0")
	implementation("io.grpc:grpc-protobuf:1.51.0")
	implementation("com.google.protobuf:protobuf-kotlin:3.21.12")
	implementation("io.grpc:grpc-okhttp:1.51.1")
	implementation("io.grpc:grpc-netty:1.51.1")



	implementation("org.springframework.boot:spring-boot-starter-webflux")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
	implementation("org.springframework.kafka:spring-kafka")
	implementation("io.grpc:grpc-protobuf:1.51.1")

	implementation("com.google.code.gson:gson:2.10")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("io.projectreactor:reactor-test")
	testImplementation("org.springframework.kafka:spring-kafka-test")
	testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.1")
	testImplementation("org.junit.jupiter:junit-jupiter-engine:5.9.1")
	testImplementation("org.junit.jupiter", "junit-jupiter-params")
	testImplementation("io.projectreactor.kotlin", "reactor-kotlin-extensions")
	testImplementation("org.jetbrains.kotlin", "kotlin-test-junit")

	testRuntimeOnly("org.junit.platform:junit-platform-launcher:1.9.0")
	testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.1")
	testRuntimeOnly("org.junit.vintage:junit-vintage-engine:5.9.1")

}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "17"
	}
}

protobuf {
	protoc {
		artifact = "com.google.protobuf:protoc:3.21.9"
	}
	plugins {
		id("grpc") {
			artifact = "io.grpc:protoc-gen-grpc-java:1.51.1"
		}
		id("grpckt") {
			artifact = "io.grpc:protoc-gen-grpc-kotlin:1.3.0:jdk8@jar"
		}
	}
	generateProtoTasks {
		all().forEach {
			it.plugins {
				id("grpc")
				id("grpckt")
			}
			it.builtins {
				id("kotlin")
			}
		}
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
