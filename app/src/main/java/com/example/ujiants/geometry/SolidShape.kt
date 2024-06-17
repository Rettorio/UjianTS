package com.example.ujiants.geometry

import kotlinx.serialization.Serializable
import kotlin.math.pow

@Serializable
sealed class SolidShape: Geometricable {
    @Serializable
    data class Cube(
        override val name: String = "Kubus",
        override val description: String,
        override val imageId: Int,
        var rusukLength: Double = 0.0,
        override val sisi: Int = 6,
        override val rusuk: Int = 12,
    ): SolidShape()
    {

        override fun area(): Double {
            return rusukLength.pow(2) * 6.0
        }

        override fun volume(): Double {
            return rusukLength.pow(3)
        }
    }

    @Serializable
    data class Cylinder(
        override val name: String = "Tabung",
        override val description: String,
        override val imageId: Int,
        override val sisi: Int = 3,
        override val rusuk: Int = 2,
        val phi: Double = Math.PI,
        var arc: Double = 0.0,
        var height: Double = 0.0
    ): SolidShape()
    {

        override fun area(): Double {
            val blanket = 2 * (phi * arc * height)
            return blanket + 2 * (phi * arc.pow(2))
        }

        override fun volume(): Double {
            return phi * arc.pow(2) * height
        }
    }

    @Serializable
    data class Cone(
        override val name: String = "Kerucut",
        override val description: String,
        override val imageId: Int,
        override val sisi: Int = 2,
        override val rusuk: Int = 1,
        val phi: Double = Math.PI,
        var arc: Double = 0.0,
        var hypot: Double = 0.0,
        var height: Double = 0.0
    ): SolidShape()
    {
        override fun area(): Double {
            val blanket = phi * arc * hypot 
            return blanket + (phi * arc.pow(2))
        }

        override fun volume(): Double {
            return (0.3333333333333333) * phi * arc.pow(2) * height
        }
    }

    @Serializable
    data class Sphere(
        override val name: String = "Bola",
        override val description: String,
        override val imageId: Int,
        override val sisi: Int = 1,
        override val rusuk: Int = 0,
        var arc: Double = 0.0,
        val phi: Double = Math.PI
    ): SolidShape()
    {

        override fun area(): Double {
            return 4.0 * (phi * arc.pow(2))
        }

        override fun volume(): Double {
            return (1.333333333333333) * (phi * arc.pow(3))
        }
    }

    @Serializable
    data class Cubeoid(
        override val name: String = "Balok",
        override val description: String,
        override val imageId: Int,
        override val sisi: Int = 6,
        override val rusuk: Int = 12,
        var width: Double = 0.0,
        var height: Double = 0.0,
        var length: Double = 0.0
    ): SolidShape()
    {

        override fun area(): Double {
            return (2 * width * length) +
                    (2 * width * height) +
                    (2 * length * height)
        }

        override fun  volume(): Double {
            return width * length * height
        }
    }

    @Serializable
    data class Pyramid(
        override val name: String = "Limas Segi-Empat",
        override val description: String,
        override val imageId: Int,
        override val sisi: Int = 5,
        override val rusuk: Int = 8,
        var height: Double = 0.0,
        var baseWidth: Double = 0.0,
    ): SolidShape()
    {
        var aob: Double = 0.0
        var hypot: Double = 0.0

        override fun area(): Double {
            return aob + ((0.5 * baseWidth * hypot) * 4)
        }

        override fun  volume(): Double {
            return (0.3333333333333333) * aob * height
        }
    }

    @Serializable
    data class OctaPrism(
        override val name: String = "Octagonal Prism",
        override val description: String,
        override val imageId: Int,
        override val sisi: Int = 10,
        override val rusuk: Int = 24,
        var baseEdge: Double = 0.0,
        var height: Double = 0.0
    ): SolidShape()
    {
        var lsa: Double = 0.0
        var mult: Double = 0.0

        override fun area(): Double {
            return 4 * mult + lsa
        }

        override fun volume(): Double {
            return 2 * mult * height
        }
    }
}