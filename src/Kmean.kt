import java.awt.image.BufferedImage
import java.io.File
import java.io.IOException
import java.util.*
import javax.imageio.ImageIO


object KMean {
    var rgb = IntArray(1920 * 1080)
    var r = IntArray(1920 * 1080)
    var g = IntArray(1920 * 1080)
    var b = IntArray(1920 * 1080)

    @JvmStatic
    fun main(args: Array<String>) {
        try {
            val originalImage = ImageIO.read(File("D:\\dev_mobile\\compressionTest\\src\\test.bmp"))
            val k = 43
            val kmeansJpg = kmeans_helper(originalImage, k)
            println("Printing Image.....")
            ImageIO.write(
                kmeansJpg,
                "jpg",
                File("D:\\dev_mobile\\compressionTest\\decompressed.jpg")
            )
        } catch (e: IOException) {
            println(e.message)
        }
    }

    private fun kmeans_helper(originalImage: BufferedImage, k: Int): BufferedImage {
        val w = originalImage.width
        val h = originalImage.height
        val kmeansImage = BufferedImage(w, h, originalImage.type)
        val g1 = kmeansImage.createGraphics()
        g1.drawImage(originalImage, 0, 0, w, h, null)
        // Read rgb values from the image
        var count = 0
        for (i in 0 until w) {
            for (j in 0 until h) {
                rgb[count] = kmeansImage.getRGB(i, j)
                count++
            }
        }

        //Create K buckets or classes
        var kpoints = Selector(k, w, h)
        // Call kmeans algorithm: update the rgb values
        for (i in 0..49) kpoints = kmeans(kpoints, k, w, h)
        // update RGB array from R G and B
        val l = 0
        for (i in 0 until w * h) {
            rgb[i] = r[i] and 0x0ff shl 16 or (g[i] and 0x0ff shl 8) or (b[i] and 0x0ff)
            println("Pixel number=" + i + "    RGB value=" + rgb[i])
        }

        // Write the new rgb values to the image
        count = 0
        for (i in 0 until w) {
            for (j in 0 until h) {
                kmeansImage.setRGB(i, j, rgb[count++])
            }
        }
        println("Returning Object")
        return kmeansImage
    }

    // Your k-means code goes here
    // Update the array rgb by assigning each entry in the rgb array to its cluster center
    private fun kmeans(kpoints: IntArray, k: Int, w: Int, h: Int): IntArray {
        var newk = IntArray(kpoints.size)
        newk = kpoints
        for (count in 0 until w * h) {
            r[count] = rgb[count] shr 16 and 0xff
            g[count] = rgb[count] shr 8 and 0xff
            b[count] = rgb[count] and 0xff
        }

        // Calculating 3d distances
        val distances = Array(k) {
            DoubleArray(
                w * h
            )
        }
        for (i in 0 until w * h) for (j in 0 until k) {
            distances[j][i] = Math.sqrt(
                Math.abs(r[kpoints[j]] - r[i] xor 2 + (g[kpoints[j]] - g[i]) xor 2 + (b[kpoints[j]] - b[i]) xor 2)
                    .toDouble()
            )
            //System.out.println(distances[j][i]);
        }
        val answer = FindLeastDist(distances, w, h, k)
        val ti = answer[1]
        val tj = answer[2]
        var temp = 0
        temp = kpoints[tj]
        kpoints[tj] = ti
        r[temp] = r[ti + 100]
        g[temp] = g[ti + 100]
        b[temp] = b[ti + 100]
        //r[temp]=0;
        //g[temp]=0;
        //b[temp]=0;

        //System.out.println(Arrays.toString(kpoints));
        return kpoints
    }

    private fun Selector(k: Int, w: Int, h: Int): IntArray {
        // Select K points
        var i1 = 0
        val kpoints = IntArray(k)
        while (i1 < k) {
            val rand = Random()
            val min = 0
            val max = w * h
            kpoints[i1] = rand.nextInt(max - min + 1) + min
            i1++
        }
        return kpoints
    }

    private fun FindLeastDist(distances: Array<DoubleArray>, w: Int, h: Int, k: Int): IntArray {
        var ti = 0
        var tj = 0
        var temp = 100000.0
        for (i in 0 until w * h) for (j in 0 until k) {
            if (distances[j][i].toInt() != 0 && distances[j][i] < temp) {
                temp = distances[j][i]
                ti = i
                tj = j
                //System.out.println(temp+"     j="+j+"     i="+i);
            }
        }
        val answer = IntArray(3)
        answer[0] = temp.toInt()
        answer[1] = ti
        answer[2] = tj
        return answer
    }
}