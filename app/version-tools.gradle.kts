import org.gradle.kotlin.dsl.extra

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

project.extra["gitVersionCode"] = getVersionCode()
project.extra["gitVersionName"] = generateVersionName()
