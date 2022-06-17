import kotlin.math.pow
import kotlin.math.sqrt

class EuclidianDistance : Distance {
    override fun calculateDistance(f1: Map<String, Double>, f2: Map<String, Double>): Double {
        var sum = 0.0
        for (key: String in f1.keys) {
            val v1 = f1.get(key)
            val v2 = f2.get(key)

            if (v1 != null && v2 != null) {
                sum += (v1 - v2).pow(2);
            }
        }
        return sqrt(sum)
    }

}