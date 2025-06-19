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

fun Character.nameOrEmpty() = name ?: ""

fun Character.statusOrEmpty() = status ?: ""

fun Character.speciesOrEmpty() = species ?: ""

fun Character.typeOrEmpty() = type ?: ""

fun Character.genderOrEmpty() = gender ?: ""

fun Character.imageOrEmpty() = image ?: ""

