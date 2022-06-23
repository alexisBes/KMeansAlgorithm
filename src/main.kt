import distance.EucledianDistance
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

fun main() {
    val originalImage = ImageIO.read(File("src/test.jpg"))

    val w = originalImage.width
    val h = originalImage.height
    val points:Array<Int> = IntArray(w * h) { 0 }.toTypedArray()
    var count = 0
    for (i in 0 until w) {
        for (j in 0 until h) {
            points[count] = originalImage.getRGB(i, j)
            count++
        }
    }
    val algo = KMeans(30, 10, EucledianDistance())
    val newPoints = algo.execute(points)
    println("C'est partit pour l'algo ....")
    val kmeansImage =  BufferedImage(w, h, originalImage.type)
    count = 0
    for (i in 0 until w) {
        for (j in 0 until h) {
            kmeansImage.setRGB(i, j, newPoints[count++])
        }
    }
    println("Printing Image.....")
    ImageIO.write(
        kmeansImage,
        "jpg",
        File("decompressed.jpg")
    )
}