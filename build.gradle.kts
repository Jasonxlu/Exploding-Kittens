import com.github.spotbugs.snom.Confidence
import com.github.spotbugs.snom.Effort

plugins {
    id("java")
    id("com.github.spotbugs") version "6.0.25"
    checkstyle
}

group = "nu.csse.sqe"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(11)
    }
}

tasks.compileJava {
    options.release = 11
}

tasks.test {
    useJUnitPlatform()
}

spotbugs {
    ignoreFailures = false
    showStackTraces = true
    showProgress = true
    effort = Effort.DEFAULT
    reportLevel = Confidence.DEFAULT
    visitors = listOf("FindSqlInjection", "SwitchFallthrough")
    omitVisitors = listOf("FindNonShortCircuit")
    reportsDir = layout.buildDirectory.dir("spotbugs").get().asFile
    includeFilter = file("include.xml")
    excludeFilter = file("exclude.xml")
    baselineFile = file("baseline.xml")
    onlyAnalyze = listOf("com.foobar.MyClass", "com.foobar.mypkg.*")
    maxHeapSize = "1g"
    extraArgs = listOf("-nested:false")
    jvmArgs = listOf("-Duser.language=ja")
}

checkstyle {
    toolVersion = "10.18.2"
}