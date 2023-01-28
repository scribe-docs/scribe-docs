import com.github.gradle.node.pnpm.task.PnpmTask

plugins {
    base
    distribution
    alias(libs.plugins.node.gradle)
}

node {
    version.set("19.5.0")
    download.set(true)
    pnpmVersion.set("7.26.1")
}

val parcelDevBuild by tasks.creating(PnpmTask::class) {
    dependsOn(tasks.pnpmInstall)
    
    args.set(listOf("run", "dev"))
    inputs.dir("src")
    inputs.files(
            "package.json",
            "pnpm-lock.yaml",
            "sharp.config.json",
            "tsconfig.json",
            ".htmlnanorc",
            ".posthtmlrc",
            ".browserslistrc",
                )
    outputs.dir("${buildDir}/dev")
}

val parcelProdBuild by tasks.creating(PnpmTask::class) {
    dependsOn(tasks.pnpmInstall)
    
    args.set(listOf("run", "build"))
    inputs.dir("src")
    inputs.files(
            "package.json",
            "pnpm-lock.yaml",
            "sharp.config.json",
            "tsconfig.json",
            ".htmlnanorc",
            ".posthtmlrc",
            ".browserslistrc",
                )
    outputs.dir("${buildDir}/prod")
}

val parcelServe by tasks.creating(PnpmTask::class) {
    dependsOn(tasks.pnpmInstall)
    
    args.set(listOf("run", "serve"))
    inputs.dir("src")
    inputs.files(
            "package.json",
            "pnpm-lock.yaml",
            "sharp.config.json",
            "tsconfig.json",
            ".htmlnanorc",
            ".posthtmlrc",
            ".browserslistrc",
                )
    outputs.dir("${buildDir}/prod")
}

val browserDist: Configuration by configurations.creating {
    isCanBeConsumed = true
    isCanBeResolved = false
}

val browserDevDist: Configuration by configurations.creating {
    isCanBeConsumed = true
    isCanBeResolved = false
}

artifacts {
    add(browserDist.name, parcelProdBuild.outputs.files.singleFile) {
        builtBy(parcelProdBuild)
    }
    add(browserDevDist.name, parcelDevBuild.outputs.files.singleFile) {
        builtBy(parcelDevBuild)
    }
}

val isProduction: Boolean
    get() = project.hasProperty("isProduction")
