package zip_collate

import java.io.File
import scopt.OptionParser

case class ArgsConfig(zipFile: Option[File] = None,
                      baseFileName: String = "file",
                      fileExtension: String = "",
                      startNum: Long = 0,
                      delim: Option[String] = None) {
  def toArgs(): Args = {
    assert(zipFile.isDefined)
    Args(zipFile.get,
         baseFileName,
         fileExtension,
         startNum,
         delim)
  }
}

case class Args(zipFile: File,
                baseFileName: String,
                fileExtension: String,
                startNum: Long,
                delim: Option[String])

object ArgsParser extends scopt.OptionParser[ArgsConfig]("zip-collate") {
  head("zip-collate")

  opt[String]('b', "base_filename")
    .action((b, c) => c.copy(baseFileName = b))
    .text("base filename")

  opt[String]('e', "file_extension")
    .action((e, c) => c.copy(fileExtension = e))
    .text("file extension")

  opt[Long]('s', "start_num")
    .action((l, c) => c.copy(startNum = l))
    .text("starting file number")

  opt[String]('d', "delimiter")
    .action((d, c) => c.copy(delim = Some(d)))
    .text("delimiter between files")

  arg[File]("zip file")
    .required
    .action((f, c) => c.copy(zipFile = Some(f)))

  def parse(args: Array[String]): Option[Args] = {
    parse(args, ArgsConfig()).map(_.toArgs)
  }
}
