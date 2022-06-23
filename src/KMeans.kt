import distance.Distance
import kotlin.random.Random

class KMeans(val K: Int, val maxIter: Int, val distance: Distance) {
    val centroids: ArrayList<Int> = ArrayList<Int>()
    private fun initializeCentroids(points: Array<Int>) {
        for (i in 0 until K) {
            val randomPoint = Random.nextInt(0, points.size)
            centroids.add(points[randomPoint])
        }
    }

    private fun findMinimalDistanceInPixel(point: Int): Int {
        var index = Int.MAX_VALUE
        var minDistance = Int.MAX_VALUE
        for (i in 0 until centroids.size) {
            val dist = distance.calculateDistance(point, centroids[i])
            if (dist < minDistance) {
                index = i
                minDistance = dist
            }
        }
        return index
    }

    private fun movePixel(points: Array<Int>): Array<Int> {
        val newPoints= points
        for (i in points.indices){
            val centroidIndex= findMinimalDistanceInPixel(points[i])
            newPoints[i] = centroids[centroidIndex]
        }
        return newPoints
    }

    fun execute(points: Array<Int>):Array<Int>{
        var newPoints : Array<Int> = IntArray(points.size) { 0 }.toTypedArray()
        for(i in 0 until maxIter){
            println("iteration num $i")
            initializeCentroids(points)
            newPoints = movePixel(points)
        }
        return newPoints
    }

}