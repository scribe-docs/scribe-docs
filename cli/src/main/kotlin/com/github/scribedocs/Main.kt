package com.github.scribedocs

import com.github.ajalt.clikt.core.subcommands
import com.github.scribedocs.commands.BuildCommand
import com.github.scribedocs.commands.ScribeCommand

fun main(vararg args: String) {
    ScribeCommand()
            .subcommands(BuildCommand())
            .main(args)
}
