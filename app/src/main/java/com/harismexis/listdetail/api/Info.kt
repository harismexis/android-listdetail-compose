package com.harismexis.listdetail.api

data class Info(
    val count: Int,
    val pages: Int,
    val next: String?, // "https://rickandmortyapi.com/api/character/?page=2",
    val prev: String?,
)


