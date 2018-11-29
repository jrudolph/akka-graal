package net.virtualvoid.akka.graal

import java.io.FileOutputStream

import scala.io.Source

object Blub extends App {
  val entries = Source.fromFile("entries").getLines().map(_.trim).toVector.distinct
    .filterNot(e =>
      e.startsWith("[") ||
        e.startsWith("sun.reflect.Generated") ||
        e.contains("$Lambda")
    )
  println(entries.size)

  val fos = new FileOutputStream("reflectconf.json")
  fos.write("[\n".getBytes())

  var first = true
  entries.sorted.foreach { e =>
    if (first) first = false
    else fos.write(",\n".getBytes())
    fos.write(
      s"""  {
         |    "name" : "$e",
         |    "allDeclaredConstructors" : true,
         |    "allPublicConstructors" : true
         |  }""".stripMargin.getBytes())
  }

  fos.write("\n]".getBytes())
  fos.close()
}
