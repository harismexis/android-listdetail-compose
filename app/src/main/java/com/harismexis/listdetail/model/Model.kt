package com.harismexis.listdetail.model

data class Model(
    val date: String?, // "2009-04-20"
    val explanation: String?, // "When does Mars act like a liquid?  Although liquids bla bla...
    val hdurl: String?, // "https://apod.nasa.gov/apod/image/0904/sandmars_mro_big.jpg"
    val mediaType: String?, // "image"
    val serviceVersion: String?, // "v1"
    val title: String?, // "Flowing Barchan Sand Dunes on Mars"
    val url: String? // "https://apod.nasa.gov/apod/image/0904/sandmars_mro.jpg"
)

fun String?.extractYouTubeVideoId(): String? {
    if (this == null) return null
    if (!this.contains("youtube")) return null
    val embed = "/embed/"
    val startIndex: Int = this.lastIndexOf(embed) + embed.length
    if (startIndex <= 0) return null
    val endIndex: Int = this.indexOf("?")
    if (endIndex <= 0) return null
    return this.substring(startIndex, endIndex)
}

fun Model?.isImage(): Boolean? {
    return this?.url?.startsWith("https://apod.nasa.gov/apod/image/") == true
}

fun Model?.isVideo(): Boolean? {
    return this?.url?.startsWith("https://www.youtube.com/embed/") == true
}
