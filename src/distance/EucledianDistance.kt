package distance

import kotlin.math.abs
import kotlin.math.sqrt

class EucledianDistance : Distance {
    // calcule en fonction de la distance euclidienne
    override fun calculateDistance(point: Int, centroid: Int): Int {
        return sqrt(
            abs(point - centroid).toDouble()
        ).toInt()
    }
}