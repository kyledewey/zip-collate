package zip_collate

object Main {
  def readUntilDelim(delim: String, readLine: () => String): Option[String] = {
    val buffer = scala.collection.mutable.Buffer[String]()
    var line = readLine()
    while ((line ne null) && line != delim) {
      buffer += line + "\n"
      line = readLine()
    }

    if (buffer.isEmpty) {
      None
    } else {
      Some(buffer.mkString)
    }
  }

  def readWithDelim(delim: Option[String], readLine: () => String): Option[String] = {
    delim match {
      case None => Option(readLine())
      case Some(d) => readUntilDelim(d, readLine)
    }
  }

  def readWithDelim(delim: Option[String]): Option[String] = {
    import scala.io.StdIn.readLine
    readWithDelim(delim, () => readLine())
  }

  def runZipper(args: Args) {
    val zipper = new Zipper(args)
    Runtime.getRuntime.addShutdownHook(
      new Thread(
	new Runnable {
	  def run() {
	    zipper.close()
	  }
	}))

    def readOne(): Option[String] = {
      readWithDelim(args.delim)
    }

    var item: Option[String] = readOne()
    while (item.isDefined) {
      zipper.addFile(item.get)
      item = readOne()
    }

    zipper.close()
  } // runZipper
    
  def main(args: Array[String]) {
    ArgsParser.parse(args).map(runZipper)
  }
} // Main
