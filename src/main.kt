import distance.EucledianDistance
import distance.RandomDistance
import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

fun main() {
    val originalImage = ImageIO.read(File("src/test.jpg"))

    val w = originalImage.width
    val h = originalImage.height
    val points:Array<Pixel> = Array(w * h) {Pixel(0,0,0)}
    var count = 0
    for (i in 0 until w) {
        for (j in 0 until h) {
            points[count].add(Color(originalImage.getRGB(i, j)))
            count++
        }
    }
    val algo = KMeans(40, 10, EucledianDistance())
    println("C'est partit pour l'algo ....")
    val newPoints = algo.execute(points)
    val kmeansImage =  BufferedImage(w, h, originalImage.type)
    count = 0
    for (i in 0 until w) {
        for (j in 0 until h) {
            kmeansImage.setRGB(i, j, newPoints[count++].getRGB())
        }
    }
    println("Printing Image.....")
    ImageIO.write(
        kmeansImage,
        "jpg",
        File("decompressed.jpg")
    )
}