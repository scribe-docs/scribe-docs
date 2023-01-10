package com.github.scribedocs.commands

import com.github.ajalt.clikt.core.NoOpCliktCommand
import com.github.ajalt.clikt.core.context
import com.github.ajalt.clikt.output.CliktHelpFormatter
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.versionOption
import com.github.scribedocs.ScribeVersion
import org.slf4j.kotlin.*

class ScribeCommand : NoOpCliktCommand(
        name = "scribe-cli",
        help = """
            Base command for scribe-cli
        """.trimIndent(),
        printHelpOnEmptyArgs = true
                                      ) {
    private val logger by getLogger()
    
    val verbose by option("--verbose", "-v", help = "Enable verbose logging output")
            .flag(default = false)
    
    init {
        versionOption(version = ScribeVersion.VERSION, names = setOf("-V", "--version"))
        context {
            allowInterspersedArgs = false
            
            helpFormatter = CliktHelpFormatter(
                    showDefaultValues = true,
                    showRequiredTag = true,
                    indent = "    ",
                                              )
        }
    }
}
