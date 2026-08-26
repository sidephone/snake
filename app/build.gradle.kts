plugins {
	alias(libs.plugins.android.application)
	alias(libs.plugins.kotlin.compose)
}

apply(from="./version-tools.gradle.kts")

android {
	namespace = "com.sidephone.snake"
	compileSdk {
		version = release(37)
	}

	defaultConfig {
		applicationId = "com.sidephone.snake"
		minSdk = 31
		targetSdk = 37
		versionCode = project.extra["gitVersionCode"] as Int
		versionName = project.extra["gitVersionName"] as String

		testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
	}

	buildTypes {
		release {
			optimization {
				enable = true
			}
		}
	}
	compileOptions {
		sourceCompatibility = JavaVersion.VERSION_11
		targetCompatibility = JavaVersion.VERSION_11
	}
	buildFeatures {
		compose = true
	}
}

dependencies {
	implementation(platform(libs.androidx.compose.bom))
	implementation(libs.androidx.activity.compose)
	implementation(libs.androidx.compose.material3)
	implementation(libs.androidx.compose.ui)
	implementation(libs.androidx.compose.ui.graphics)
	implementation(libs.androidx.core.ktx)
	implementation(libs.androidx.lifecycle.runtime.ktx)
	testImplementation(libs.junit)
	androidTestImplementation(platform(libs.androidx.compose.bom))
	androidTestImplementation(libs.androidx.compose.ui.test.junit4)
	androidTestImplementation(libs.androidx.espresso.core)
	androidTestImplementation(libs.androidx.junit)
	debugImplementation(libs.androidx.compose.ui.test.manifest)
	debugImplementation(libs.androidx.compose.ui.tooling)
	debugImplementation(libs.androidx.compose.ui.tooling.preview)
}


fun execThing(cmd: String): String {
	val output = providers.exec {
		commandLine(cmd.split(" "))
	}

	val exitCode = output.result.get().exitValue
	if (exitCode != 0) {
		val stderr = output.standardError.asText.get().trim()
		throw GradleException("execThing('$cmd') failed. $stderr")
	}

	return output.standardOutput.asText.get().trim()
}

fun getCurrentGitHash(): String {
	return execThing("git rev-parse --short=8 HEAD")
}

fun getVersionCode(): Int {
	return execThing("git rev-list --count HEAD").toInt()
}

fun generateVersionName(): String {
	val versionTagsRaw = execThing("git tag --list v[0-9]*")
	val versionTagsCount = if (versionTagsRaw.isEmpty()) 0 else versionTagsRaw.split("\n").size

	var commitsSinceLastTag = "0"
	if (versionTagsCount > 1) {
		val lastVersionTag = execThing("git describe --match v[0-9]* --tags --abbrev=0")
		val gitLogResult = execThing("git log $lastVersionTag..HEAD --oneline")
		commitsSinceLastTag = if (gitLogResult.isEmpty()) "0" else gitLogResult.split("\n").size.toString()
	}

	val allTags = execThing("git tag --list")
	val lastTagName = if (allTags.isEmpty()) "" else execThing("git describe --tags --abbrev=0")
	val lastTagHash = if (lastTagName.isEmpty()) "" else execThing("git log -1 --format=%h $lastTagName")
	val betaString = if (lastTagHash == getCurrentGitHash() && lastTagName.contains("-beta")) "-beta" else ""

	return "$versionTagsCount.$commitsSinceLastTag$betaString"
}
