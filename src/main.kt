import distance.EucledianDistance
import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

fun main() {
    /*
     LECTURE DE L'IMAGE
     */
    val originalImage = ImageIO.read(File("src/test.bmp"))

    val w = originalImage.width
    val h = originalImage.height

    //création des point, initialisation a 0
    val points:Array<Pixel> = Array(w * h) {Pixel(0,0,0)}
    var count = 0

    // remplissage du tableau
    for (i in 0 until w) {
        for (j in 0 until h) {
            // en vrai je fais un ajout des couleurs. 0+x = x, je suis pas sur que l'assignation fonctionne
            points[count].add(Color(originalImage.getRGB(i, j)))
            count++
        }
    }
    // initalisation de KMean.K = nom de cluster qui sera crer 'impact la taille de compression)
    // maxIter = nombre d'itération( permet d'éviter que des couleur a supprimer reste)
    // Distance = interface a implementer pour calculer les distance
    val algo = KMeans(40, 10, EucledianDistance())
    println("C'est partit pour l'algo ....")
    val newPoints = algo.execute(points)
    // ecriture de l'image
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
    ImageIO.write(
        originalImage,
        "jpg",
        File("uncompressed.jpg")
    )
}