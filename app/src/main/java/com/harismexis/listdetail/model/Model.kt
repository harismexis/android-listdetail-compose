package com.harismexis.listdetail.model

data class Model(
    val id: Int?,
    val name: String?,
    val status: String?,
    val species: String?,
    val type: String?,
    val gender: String?,
    val image: String?
)

fun Model.nameOrEmpty() = name ?: ""

fun Model.statusOrEmpty() = status ?: ""

fun Model.speciesOrEmpty() = species ?: ""

fun Model.typeOrEmpty() = type ?: ""

fun Model.genderOrEmpty() = gender ?: ""

fun Model.imageOrEmpty() = image ?: ""

