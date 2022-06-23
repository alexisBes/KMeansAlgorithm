import distance.Distance
import kotlin.random.Random

class KMeans(val K: Int, val maxIter: Int, val distance: Distance) {
    var centroids: ArrayList<Pixel> = ArrayList<Pixel>()
    private fun initializeCentroids(points: Array<Pixel>) {
        for (i in 0 until K) {
            val randomPoint = Random.nextInt(0, points.size)
            centroids.add(points[randomPoint])
        }
    }

    private fun findMinimalDistanceInPixel(point: Pixel): Int {
        var index = Int.MAX_VALUE
        var minDistance = Pixel(Int.MAX_VALUE, Int.MAX_VALUE, Int.MAX_VALUE)
        for (i in 0 until centroids.size) {
            val distR = distance.calculateDistance(point.R, centroids[i].R)
            val distG = distance.calculateDistance(point.G, centroids[i].G)
            val distB = distance.calculateDistance(point.B, centroids[i].B)
            val distTotal = distR + distB + distG
            if (distTotal < minDistance.sum()) {
                index = i
                minDistance = centroids[i]
            }
        }
        return index
    }

    private fun movePixel(points: Array<Pixel>): Array<Pixel> {
        val newPoints = points
        for (i in points.indices) {
            val centroidIndex = findMinimalDistanceInPixel(points[i])
            newPoints[i] = centroids[centroidIndex]
        }
        return newPoints
    }

    fun execute(points: Array<Pixel>): Array<Pixel> {
        var newPoints: Array<Pixel> = emptyArray()
        initializeCentroids(points)
        for (i in 0 until maxIter) {
            println("iteration num $i")
            centroids = moveCentroids(points)
            newPoints = movePixel(points)
        }
        return newPoints
    }

    private fun moveCentroids(points: Array<Pixel>): ArrayList<Pixel> {

        return ArrayList();
    }
}