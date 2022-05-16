import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
	id("org.springframework.boot") version "2.6.7"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	id("org.asciidoctor.convert") version "1.5.8"
	kotlin("jvm") version "1.6.21"
	kotlin("plugin.spring") version "1.6.21"
	kotlin("plugin.jpa") version "1.6.21"
	id("org.jetbrains.kotlin.plugin.allopen") version "1.3.61"
	id("com.epages.restdocs-api-spec") version "0.16.0"
}

group = "com.ddd"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

val snippetsDir by extra {file("build/generated-snippets")}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

	//JWT
	implementation("io.jsonwebtoken:jjwt-api:0.11.2")
	implementation("io.jsonwebtoken:jjwt-impl:0.11.2")
	implementation("io.jsonwebtoken:jjwt-jackson:0.11.2")

	//DEV TOOL
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

	//DB
	runtimeOnly("com.h2database:h2")
	runtimeOnly("mysql:mysql-connector-java")

	//Test
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
	testImplementation("org.springframework.security:spring-security-test")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}

tasks.build{
	dependsOn("copyDocs")
}


tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.test {
	outputs.dir(snippetsDir)
}

tasks.asciidoctor {
	inputs.dir(snippetsDir)
	dependsOn(tasks.test)
}

tasks.register<Copy>("copyDocs"){
	dependsOn(tasks.asciidoctor)
	destinationDir = file(".")
	from(tasks.asciidoctor.get().outputDir){
		into("src/main/resources/static/docs")
	}
}


tasks.register<Copy>("copyOasToSwagger") {
	delete("src/main/resources/static/swagger-ui/openapi3.yaml") // 기존 yaml 파일 삭제
	from("$buildDir/api-spec/openapi3.yaml") // 복제할 yaml 파일 타겟팅
	into("src/main/resources/static/swagger-ui/.") // 타겟 디렉토리로 파일 복제
	dependsOn("openapi3") // openapi3 task가 먼저 실행되도록 설정
}

tasks.named<BootJar>("bootJar") {
	launchScript()
}

tasks.named<Jar>("jar") {
	enabled = false
}
