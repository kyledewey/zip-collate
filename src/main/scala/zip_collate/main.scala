package zip_collate

object Main {
  def runZipper(args: Args) {
    val zipper = new Zipper(args)
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
  } // runZipper
    
  def main(args: Array[String]) {
    ArgsParser.parse(args).map(runZipper)
  }
} // Main
