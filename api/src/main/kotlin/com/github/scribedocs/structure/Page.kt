package com.github.scribedocs.structure

import java.nio.file.Path

data class Page(
    val file: Path,
    val title: String?,
    val canonicalUrl: String,
    val baseUrl: String,
    val isHomePage: Boolean,
    val meta: Map<String, Any>,
               )
