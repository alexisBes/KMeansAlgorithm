import java.awt.Color

class Pixel(var R: Int, var G: Int, var B: Int) {
    constructor(color: Color) : this(color.red, color.green, color.blue)

    fun sum(): Int {
        return R + G + B
    }

    fun add(px: Pixel) {
        this.R += px.R
        this.G += px.G
        this.B += px.B
    }
    fun add(col: Color) {
        this.R += col.red
        this.G += col.green
        this.B += col.blue
    }
    fun multiply(coef: Double){
        this.R= (coef *this.R).toInt()
        this.G = (coef * this.G).toInt()
        this.B = (coef * this.B).toInt()
    }
    public fun isPixelEquals(px: Pixel) : Boolean{
        return this.R == px.R && this.B == px.B && this.G == px.G
    }

    fun getRGB() : Int{
        return Color(R,G,B).rgb
    }

    override fun toString(): String {
        return "Color : red = $R, green = $G, blue = $B  " +super.toString()
    }
}