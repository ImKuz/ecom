group = "io.kuz"

allprojects {
	repositories {
		mavenCentral()
	}
}

subprojects {
	// Only configure Java toolchain for projects that apply the Java plugin
	plugins.withType<JavaPlugin>().configureEach {
		project.extensions.configure<JavaPluginExtension>("java") {
			toolchain.languageVersion.set(JavaLanguageVersion.of(21))
		}
	}
}
