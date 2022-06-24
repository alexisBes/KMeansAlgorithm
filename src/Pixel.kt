import java.awt.Color

class Pixel(var R: Int, var G: Int, var B: Int) {
    constructor(color: Color) : this(color.red, color.green, color.blue)

    fun sum(): Int {
        return R + G + B
    }

    // ajoute les composante des pixel a un autre pixel
    fun add(px: Pixel) {
        this.R += px.R
        this.G += px.G
        this.B += px.B
    }
    // ajoute les composante d'une couleur a un pixel
    fun add(col: Color) {
        this.R += col.red
        this.G += col.green
        this.B += col.blue
    }

    // multiplication des composant d'un pixel par un coefficient
    fun multiply(coef: Double){
        this.R= (coef *this.R).toInt()
        this.G = (coef * this.G).toInt()
        this.B = (coef * this.B).toInt()
    }

    // is pixel equals?
    public fun isPixelEquals(px: Pixel) : Boolean{
        return this.R == px.R && this.B == px.B && this.G == px.G
    }

    // return l'hexadecimal des composant du pixel
    fun getRGB() : Int{
        return Color(R,G,B).rgb
    }

    override fun toString(): String {
        return "Color : red = $R, green = $G, blue = $B  " +super.toString()
    }
}