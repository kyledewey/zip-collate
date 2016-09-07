package zip_collate

case class ZipperConfig(zipfileName: String,
			innerFilenameBase: String,
			innerFilenameExtension: String,
			innerFilenameStart: Long) {
  assert(innerFilenameStart >= 0L)
}

class Zipper(val config: ZipperConfig) {
  import java.io._
  import java.util.zip.{ZipEntry, ZipOutputStream}

  // BEGIN CONSTRUCTOR
  private val output =
    new ZipOutputStream(
      new BufferedOutputStream(
	new FileOutputStream(config.zipfileName)))
  private var curFile: Long = config.innerFilenameStart

  private var closeCalled = false
  private val lock = new Object
  // END CONSTRUCTOR

  private def curFilenameAndInc(): String = {
    val retval =
      config.innerFilenameBase + curFile.toString + config.innerFilenameExtension
    curFile += 1L
    retval
  }

  def addFile(contents: String) {
    lock.synchronized {
      if (!closeCalled) {
	output.putNextEntry(new ZipEntry(curFilenameAndInc()))
	val writer = new PrintWriter(output)
	writer.print(contents)
	writer.flush();
	// we intentionally don't close the writer, which would close the
	// ZipOutputStream
	output.closeEntry()
      }
    }
  }

  def close() {
    if (!closeCalled) {
      lock.synchronized {
	if (!closeCalled) {
	  closeCalled = true
	  output.finish()
	  output.close()
	}
      }
    }
  }
}

