package zip_collate

import java.io._

class Zipper(val config: Args) {
  import java.util.zip.{ZipEntry, ZipOutputStream}

  // BEGIN CONSTRUCTOR
  private val output =
    new ZipOutputStream(
      new BufferedOutputStream(
	new FileOutputStream(config.zipFile)))
  private var curFile: Long = config.startNum

  private var closeCalled = false
  private val lock = new Object
  // END CONSTRUCTOR

  private def curFilenameAndInc(): String = {
    val retval =
      config.baseFileName + curFile.toString + config.fileExtension
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

