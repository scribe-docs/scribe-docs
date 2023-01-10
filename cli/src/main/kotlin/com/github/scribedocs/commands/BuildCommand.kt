package com.github.scribedocs.commands

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.path
import org.slf4j.kotlin.*
import java.nio.file.Paths
import kotlin.io.path.Path
import kotlin.io.path.createDirectories
import kotlin.io.path.exists
import kotlin.io.path.extension
import kotlin.io.path.listDirectoryEntries
import kotlin.io.path.name
import kotlin.io.path.readText

class BuildCommand : CliktCommand(
        name = "build",
        help = """
            Build the ScribeDocs documentation
        """.trimIndent()
                                 ) {
    private val logger by getLogger()
    private val version by option(help = "The docs version", metavar = "VERSION")
            .default("0.1.0")
    
    private val input by option(help = "The input directory to use for the build")
            .path()
            .default(Path("input"))
    
    private val output by option(help = "The directory to output the result of the build")
            .path()
            .default(Path("out"))
    
    override fun run() {
        if (!input.exists())
            input.createDirectories()
        
        if (!output.exists())
            output.createDirectories()
        
        val placeholders = mapOf(
                "{%version%}" to version,
                "{%commit%}" to "somecommithash",
                                )
        
        for (file in input.listDirectoryEntries()) { // TODO: 2023-01-10 Maybe use Files.list(Path)?
            logger.info { "Processing ${file.name}" }
            
            if (file.extension == "md" && !file.name.startsWith(".")) {
                val f = Paths.get("./out/" + file.name).toAbsolutePath().toFile()
                f.writeText(processPlaceholders(file.readText(), placeholders))
            }
        }
    }
    
    private fun processPlaceholders(input: String, placeholders: Map<String, String>): String {
        var out = input
        println(placeholders)
        if (out.contains("{%") && out.contains("%}")) {
            for (k in placeholders) {
                out = out.replace(k.key, k.value)
            }
        }
        println(out)
        return out
    }
}
