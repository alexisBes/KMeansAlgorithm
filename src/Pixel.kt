import java.awt.Color

class Pixel(val R : Int,val G:Int,val B:Int) {
   constructor(color: Color) : this(color.red,color.green,color.blue)
   public fun sum() : Int{
      return R+G+B
   }
}