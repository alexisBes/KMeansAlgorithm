package distance

import kotlin.random.Random

class RandomDistance : Distance {
    override fun calculateDistance(point: Int, centroid: Int): Int {
        return Random.nextInt(0, Int.MAX_VALUE)
    }
}