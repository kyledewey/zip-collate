package zip_collate

object Main {
  val USAGE =
    "Usage: zip_collate zipfileName [baseFilename] [fileExtension] [startNum]"
  val ARGS_HANDLER =
    new ArgsHandler(1, List("file", "", "0"), USAGE)


  // throws NumberFormatException if parsing failed
  def parseNonNegative(string: String): Long = {
    val num = string.toLong
    if (num < 0L) {
      throw new NumberFormatException("Expected non-negative long")
    }
    num
  }

  def main(args: Array[String]) {
    ARGS_HANDLER.parseArguments(args) match {
      case Some(zipfileName :: baseFilename :: fileExtension :: startNum :: rest) => {
	assert(rest.isEmpty)
	try {
	  
	  val zipper =
	    new Zipper(
	      ZipperConfig(zipfileName,
			   baseFilename,
			   fileExtension,
			   parseNonNegative(startNum)))
	  Runtime.getRuntime.addShutdownHook(
	    new Thread(
	      new Runnable {
		def run() {
		  zipper.close()
		}
	      }))
	  import scala.io.StdIn.readLine
	  var line: String = readLine()
	  while (line ne null) {
	    zipper.addFile(line)
	    line = readLine()
	  }
	  zipper.close()
	} catch {
	  case _: NumberFormatException => {
	    println("Starting number must be a non-negative integer")
	  }
	}
      }
      case Some(_) => {
	assert(false)
	// argument parsing is faulty
      }
      case None => () // invalid arguments
    }
  } // main
} // Main
