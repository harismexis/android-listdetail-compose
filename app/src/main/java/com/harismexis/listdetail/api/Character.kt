package com.harismexis.listdetail.api

data class Character(
    val id: Int?,
    val name: String?,
    val status: String?,
    val species: String?,
    val type: String?,
    val gender: String?,
    val image: String?
)

fun String?.getValueOrNa(): String {
    return if (!this.isNullOrEmpty()) {
        this
    } else {
        "N/A"
    }
}
