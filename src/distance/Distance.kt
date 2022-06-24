package distance

// interface pour le calcul des distance
interface Distance {
    // calcul en fonction d'une composante et de la composante d'un centre
    fun calculateDistance(point: Int, centroid: Int): Int
}