package com.github.scribedocs.config

import kotlinx.serialization.Serializable

@Serializable
data class SiteConfig(
    val name: String,
    val url: String? = null,
    val description: String? = null,
    val author: String? = null,
                     )
