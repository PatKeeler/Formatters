package src

import scala.io.Source

/**
 * Created by Pat on 10/27/2015.
 */
object ScalaFileReader {

  // Main for testing
  def main(args: Array[String]) = {
    val fileName = "C:\\Dev-Intellij-Scala\\tutorials\\src\\PatsUtils.scala"
    printFile(fileName)
  }


  def printFile(fileName : String): Unit = {

    println("\nfileName: " + fileName + "\n")

    val bufferedSource = Source.fromFile(fileName)
    for (line <- bufferedSource.getLines) {
      println(line)
    }

    bufferedSource.close()
  }

}
