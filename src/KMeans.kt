import distance.Distance
import kotlin.collections.ArrayList
import kotlin.random.Random

class KMeans(val K: Int, val maxIter: Int, val distance: Distance) {
    var centroids: ArrayList<Pixel> = ArrayList<Pixel>()
    // initialisation du nombre de cluster en fonction de leur centre.
    // il s'agit juste de prendre K centre parmis le tableau de point
    private fun initializeCentroids(points: Array<Pixel>) {
        for (i in 0 until K) {
            val randomPoint = Random.nextInt(0, points.size)
            centroids.add(points[randomPoint])
        }
    }

    // recherche de la difference la plus petit entre le point et les centroid
    private fun findMinimalDistanceInPixel(point: Pixel): Int {
        var index = Int.MAX_VALUE
        var minDistance = Int.MAX_VALUE
        for (i in centroids.indices) {
            val distR = distance.calculateDistance(point.R, centroids[i].R)
            val distG = distance.calculateDistance(point.G, centroids[i].G)
            val distB = distance.calculateDistance(point.B, centroids[i].B)
            val distTotal = distR + distB + distG
            if (distTotal < minDistance) {
                index = i
                minDistance = distTotal
            }
        }
        return index
    }

    // changement du pixel par son centroid le plus proche
    private fun movePixel(points: Array<Pixel>): Array<Pixel> {
        for (i in points.indices) {
            val centroidIndex = findMinimalDistanceInPixel(points[i])
            points[i] = centroids[centroidIndex]
        }
        return points
    }

    fun execute(points: Array<Pixel>): Array<Pixel> {
        var newPoints: Array<Pixel> = emptyArray()
        initializeCentroids(points)
        for (i in 0 until maxIter) {
            println("iteration num $i")
            newPoints = movePixel(points)
            centroids = moveCentroids(points)
        }
        return newPoints
    }

    // modification des centroid par la moyenne des pixel crér sur le meme centroid (attend ca sert a r en fait ....)
    private fun moveCentroids(points: Array<Pixel>): ArrayList<Pixel> {
        val newCentroid: Array<Pixel> = Array(K) { Pixel(0, 0, 0) }
        val nbCentroid: Array<Int> = Array(K) { 0 }

        for (i in points.indices) {
            for (j in 0 until 6) {
                if (centroids[j].isPixelEquals(points[i])) {
                    newCentroid[j].add(points[i])
                    nbCentroid[j]++
                }
            }
        }

        for (i in newCentroid.indices) {
            newCentroid[i].multiply(1.0 / nbCentroid[i])
        }
        return newCentroid.toCollection(ArrayList<Pixel>())
    }
}