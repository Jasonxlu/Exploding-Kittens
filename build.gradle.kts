import com.github.spotbugs.snom.Confidence
import com.github.spotbugs.snom.Effort

plugins {
    id("java")
    id("com.github.spotbugs") version "6.0.25"
    checkstyle
    jacoco
    id("info.solidsoft.pitest") version "1.15.0"
}

group = "nu.csse.sqe"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    // https://mvnrepository.com/artifact/org.easymock/easymock
    testImplementation("org.easymock:easymock:5.4.0")

    // cucumber
    testImplementation(platform("io.cucumber:cucumber-bom:7.20.1"))
    testImplementation("io.cucumber:cucumber-java")
    testImplementation("io.cucumber:cucumber-junit-platform-engine")
    testImplementation("io.cucumber:cucumber-picocontainer:7.20.1")

    implementation("com.github.spotbugs:spotbugs-annotations:4.7.3")
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

tasks.withType<Checkstyle>().configureEach {
    reports {
        xml.required = false
        html.required = true
        html.stylesheet = resources.text.fromFile("config/xsl/checkstyle-noframes-severity-sorted.xsl")
    }
}

tasks.named<Checkstyle>("checkstyleMain") {
    enabled = true
}

tasks.named<Checkstyle>("checkstyleTest") {
    enabled = false
}


spotbugs {
    ignoreFailures = false
    showStackTraces = true
    showProgress = true
    effort = Effort.DEFAULT
    reportLevel = Confidence.DEFAULT
//    visitors = listOf("FindSqlInjection", "SwitchFallthrough")
//    omitVisitors = listOf("FindNonShortCircuit")
    reportsDir = layout.buildDirectory.dir("spotbugs").get().asFile
//    includeFilter = file("include.xml")
//    excludeFilter = file("exclude.xml")
//    baselineFile = file("baseline.xml")
//    onlyAnalyze = listOf("com.foobar.MyClass", "com.foobar.mypkg.*")
    maxHeapSize = "1g"
    extraArgs = listOf("-nested:false")
//    jvmArgs = listOf("-Duser.language=ja")
}

checkstyle {
    toolVersion = "10.18.2"
    isIgnoreFailures = false
}

configurations {}

val cucumberRuntime by configurations.creating {
    extendsFrom(configurations["testImplementation"])
}

task("cucumber") {
    dependsOn("assemble", "compileTestJava")
    doLast {
        javaexec {
            mainClass.set("io.cucumber.core.cli.Main")
            classpath = cucumberRuntime + sourceSets.main.get().output + sourceSets.test.get().output
            args = listOf("--plugin", "pretty",
                "--glue", "explodingwildcats",          // where the step definitions are.
                "src/test/resources")                   // where the feature files are.
            // Configure jacoco agent for the test coverage.
            val jacocoAgent = zipTree(configurations.jacocoAgent.get().singleFile)
                .filter { it.name == "jacocoagent.jar" }
                .singleFile
            jvmArgs = listOf("-javaagent:$jacocoAgent=destfile=$buildDir/results/jacoco/cucumber.exec,append=false")
        }
    }
}

tasks.spotbugsMain {
    reports.create("html") {
        required = true
        outputLocation = layout.buildDirectory.file("reports/spotbugs/spotbugs.html")
        setStylesheet("fancy-hist.xsl")
    }
}

tasks.jacocoTestReport {
    reports {
        xml.required = false
        csv.required = false
        html.outputLocation = layout.buildDirectory.dir("reports/jacoco")
    }
}

tasks.build {
    dependsOn("pitest")
}

tasks.test {
    finalizedBy(tasks.jacocoTestReport) // report is always generated after tests run
    finalizedBy(tasks.pitest)
}
tasks.jacocoTestReport {
    dependsOn(tasks.test) // tests are required to run before generating the report
}

pitest {
    targetClasses = setOf("explodingwildcats.*") //by default "${project.group}.*"
    targetTests = setOf("explodingwildcats.*")
    junit5PluginVersion = "1.2.1"
    pitestVersion = "1.15.0" //not needed when a default PIT version should be used

    threads = 4
    outputFormats = setOf("HTML")
    timestampedReports = false
    testSourceSets.set(listOf(sourceSets.test.get()))
    mainSourceSets.set(listOf(sourceSets.main.get()))
    jvmArgs.set(listOf("-Xmx2048m"))
    useClasspathFile.set(true) //useful with bigger projects on Windows
    fileExtensionsToFilter.addAll("xml")
    exportLineCoverage = true
}

