package distance

import kotlin.math.abs
import kotlin.math.sqrt

class EucledianDistance : Distance {
    override fun calculateDistance(point: Int, centroid: Int): Int {
        return sqrt(
            abs(point - centroid).toDouble()
        ).toInt()
    }
}