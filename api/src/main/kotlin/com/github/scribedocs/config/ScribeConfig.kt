package com.github.scribedocs.config

import kotlinx.serialization.Serializable

@Serializable
data class ScribeConfig(
    val site: SiteConfig,
    val theme: ThemeConfig,
    val docsDir: String = "docs",
    val siteDir: String = "site",
    val copyright: String? = null,
                       )
