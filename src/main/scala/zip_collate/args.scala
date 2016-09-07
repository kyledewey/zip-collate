package zip_collate

class ArgsHandler(val numRequired: Int,
		  val optional: List[String], // defaults
		  val usage: String) {
  assert(numRequired >= 0)

  // produces actual arguments list
  def parseArguments(args: Array[String]): Option[List[String]] = {
    if (args.length < numRequired) {
      println(usage)
      println("Missing required arguments")
      None
    } else if (args.length > numRequired + optional.size) {
      println(usage)
      println("Too many arguments")
      None
    } else {
      Some(args.toList ++ optional.drop(args.size - numRequired))
    }
  } ensuring(_.map(_.size == numRequired + optional.size).getOrElse(true))
}
