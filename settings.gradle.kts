plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}
rootProject.name = "study-kotlin"
include("advanced-kotlin:untitled")
findProject(":advanced-kotlin:untitled")?.name = "untitled"
include("kotlin-for-java-beginners")
