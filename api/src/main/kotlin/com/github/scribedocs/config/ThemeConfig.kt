package com.github.scribedocs.config

import kotlinx.serialization.Serializable
import java.nio.file.Path

@Serializable
data class ThemeConfig(
    val name: String = "default",
    val favicon: Path? = null,
    
    )
