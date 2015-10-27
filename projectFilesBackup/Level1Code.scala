object Level1Code {


  // Returns "Hello World!"
  def helloWorld(): String = "Hello World!"


  // Return the sum of two numbers
  def sum(a:Int, b:Int): Int = a + b


  // Return the product of two numbers
  def product(a:Int, b:Int): Int = a * b

  
  // Return the square of a number
  def square(a:Int): Int = a * a

  
  // return the cube of a number
  def cube(a:Int):Int = a * a * a

  
  // return the Int quotient of a fraction
  def quotient(numerator:Int, denominator:Int):Int = {
    numerator / denominator
  }

  
  // return the Int remainder of a fraction
  def remainder(numerator:Int, denominator:Int):Int = {
    numerator % denominator
  }

  
  // return the square root of perfect squares only.
  def squareRootOfPerfectSquare(a:Int):Option[Int] = {

    //Verify data
    if (a == 0) Some(a)
    else if (a == 1) Some(a)
    else if (a < 0) None
    else {

      // Work
      var workRoot = 0
      var squareRoot = 0

      // Use divide and average formula
      squareRoot = (a / 2)
      while ((workRoot - squareRoot) != 0) {
        workRoot = squareRoot
        squareRoot = (workRoot + (a / workRoot)) /2
      }

      // Determine result
      if (squareRoot * squareRoot == a) Some(squareRoot)
      else None
    }
  }

  
  // return an array containing the square of each number
  // in the source array in the same order
  def squareAll(as:Array[Int]): Array[Int] = {
    for (e <- as) yield e * e
  }

  
  // return an array containing the cube of each number
  // in the source array in the same order
  def cubeAll(as:Array[Int]): Array[Int] = {
    for (e <- as) yield e * e * e
  }

  
  // return an array containing the product of an 'a' in array 'as' with
  // its respective 'b' in array 'bs'
  def productAll(as:Array[Int], bs:Array[Int]) : Array[Int] = {
    val result: Array[Int] = new Array[Int](as.length)
    for (i <- 0 until as.length) result(i) = product(as(i), bs(i))
    result
  }

  
  // sum all of the elements in the array and return the result
  def sumAll(as:Array[Int]): Int = {
    val sum = as.reduceLeft[Int](_ + _)
    sum
  }

  // reverse the array
  def reverse(as:Array[Int]): Array[Int] = as.reverse

}