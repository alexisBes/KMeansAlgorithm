import java.util.*
import java.util.stream.Collectors.toList
import java.util.stream.Collectors.toSet
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class KMeans {
    private val random: Random = Random()

    fun fit(
        records: List<Record>,
        k: Int,
        distance: Distance,
        maxIteration: Int
    ): Map<Centroid, List<Record>> {
        var centroids = randomCentroids(records, k)
        var clusters: HashMap<Centroid, ArrayList<Record>> = HashMap()
        var lastState: HashMap<Centroid, ArrayList<Record>> = HashMap()

        repeat(maxIteration) repeatBlock@ {
            val isLastIteration: Boolean = it == maxIteration - 1
            for (record: Record in records) {
                val centroid: Centroid? = nearestCentroid(record, centroids, distance)
                if (centroid != null) {
                    assignToCluster(clusters, record, centroid)
                }
            }
            val shouldTerminate : Boolean = isLastIteration || clusters.equals(lastState)
            lastState = clusters
            if(shouldTerminate)
                return@repeatBlock;
            centroids = relocateCentroid(clusters)
            clusters = HashMap()
        }
        return lastState
    }

    private fun randomCentroids(records: List<Record>, k: Int): List<Centroid> {
        val centroid: MutableList<Centroid> = ArrayList()
        val mins: MutableMap<String, Double> = HashMap()
        val maxs: MutableMap<String, Double> = HashMap()

        for (record: Record in records) {
            record.features.forEach {
                maxs.compute(it.key) { k1, max -> if (max == null || it.value > max) it.value else max }

                mins.compute(it.key) { k2, min -> if (min == null || it.value > min) it.value else min }
            }
        }

        val attributes: Set<String> = records.stream().flatMap {
            it.features.keys.stream()
        }.collect(toSet())
        repeat(k) {
            val coordinates: MutableMap<String, Double> = HashMap()
            for (attribute: String in attributes) {
                val max = maxs.get(attribute)
                val min = mins.get(attribute)
                coordinates.put(attribute, (random.nextDouble() * (max!! - min!!)) + min)
            }
            centroid.add(Centroid(coordinates))
        }
        return centroid
    }

    private fun nearestCentroid(
        record: Record,
        centroids: List<Centroid>,
        distance: Distance
    ): Centroid? {
        var minimumDistance = Double.MAX_VALUE
        var nearest: Centroid? = null
        for (centroid: Centroid in centroids) {
            val currentDistance = distance.calculateDistance(record.features, centroid.coordinates)
            if (currentDistance < minimumDistance) {
                minimumDistance = currentDistance
                nearest = centroid
            }
        }
        return nearest
    }

    private fun assignToCluster(
        clusters: HashMap<Centroid, ArrayList<Record>>,
        record: Record,
        centroid: Centroid
    ) {
        clusters.compute(centroid) { key, list ->
            var list1 = list
            if (list1 == null) {
                list1 = ArrayList<Record>()
            }
            list1.add(record)
            list1
        }
    }

    private fun average(centroid: Centroid, records: List<Record>?): Centroid {
        if (records == null || records.isEmpty()) {
            return centroid
        }
        val average: MutableMap<String, Double> = centroid.coordinates as MutableMap<String, Double>
        records.stream().flatMap {
            it.features.keys.stream()
        }.forEach {
            average.put(it, 0.0)
        }

        average.forEach {
            average.put(it.key, it.value / records.size)
        }

        return Centroid(average)
    }

    private fun relocateCentroid (clusters : HashMap<Centroid,ArrayList<Record>>): List<Centroid>{
        return clusters.entries.stream().map {
            average(it.key,it.value)
        }.collect(toList())
    }
}