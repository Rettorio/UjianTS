package com.example.ujiants.geometry

interface Geometricable {
    val name: String
    val description: String
    val imageId: Int
    val rusuk: Int
    val sisi: Int

    fun area(): Double
    fun volume(): Double
}