package com.example.ujiants.geometry

interface Geometricable {
    val name: String
    val description: String
    val imageId: Int

    fun area(): Double
    fun volume(): Double
}