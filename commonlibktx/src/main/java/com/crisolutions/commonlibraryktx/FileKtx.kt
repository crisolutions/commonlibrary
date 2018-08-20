package com.crisolutions.commonlibraryktx

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.support.v4.content.FileProvider
import com.crisolutions.commonlibraryktx.FileTools.JPEG_QUALITY
import java.io.BufferedInputStream
import java.io.BufferedWriter
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.FileWriter
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object FileTools {
    const val JPEG_QUALITY = 80
    val FILE_TIMESTAMP_FORMATTER = SimpleDateFormat("yyyMMdd_HHmmss", Locale.getDefault())

    @Throws(IOException::class)
    fun writeInputToOutput(inputStream: InputStream, outputStream: OutputStream) {
        val buffer = ByteArray(4096)

        val read = inputStream.read(buffer)
        while (read != -1) {
            outputStream.write(buffer, 0, read)
        }
    }

    fun getSharedDirectory(context: Context): File {
        val shareDir = File(context.filesDir, "shared/")
        if (!shareDir.exists()) {
            shareDir.mkdirs()
        }
        return shareDir
    }

    fun getTempFile(context: Context, extension: String): File {
        return File(context.cacheDir, "${Date().toString().replace(" ", "")}$extension")
    }

    fun getShareableFile(context: Context, fileName: String): File {
        val shareDir = getSharedDirectory(context)
        return File(shareDir, fileName)
    }
}

fun Context.writeBytesToTempFile(byteArray: ByteArray, extension: String): File? {
    val tempFile = FileTools.getTempFile(this, extension)
    val outputStream: OutputStream?

    try {
        outputStream = FileOutputStream(tempFile)
        outputStream.run {
            write(byteArray)
            flush()
            close()
        }
    } catch (e: Exception) {
        e.printStackTrace()
        return null
    }
    return tempFile
}

fun Context.getPhotoOutputFile(): Uri {
    val file = FileTools.getShareableFile(this, "${FileTools.FILE_TIMESTAMP_FORMATTER.format(Date())}.jpg")
    return FileProvider.getUriForFile(this, "${applicationContext.packageName}.fileprovider", file)
}

fun String.textToHtml(context: Context, fileName: String): Uri? {
    var fileUri: Uri? = null

    try {
        val tempFile = FileTools.getShareableFile(context, fileName)
        val writer = BufferedWriter(FileWriter(tempFile))

        writer.run {
            write(this@textToHtml)
            flush()
            close()
        }
        try {
            fileUri = FileProvider.getUriForFile(context,
                    "${context.packageName}.fileprovider",
                    tempFile)
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        }
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return fileUri
}

//missing parseResponseToFile

fun Bitmap.writeToFile(context: Context): File {
    val bitmapFile = FileTools.getTempFile(context, ".jpg")
    FileOutputStream(bitmapFile).use { fos ->
        compress(Bitmap.CompressFormat.JPEG, JPEG_QUALITY, fos)
    }
    return bitmapFile
}

fun File.bytesFromFile(): ByteArray? {
    val size = length().toInt()
    val result = ByteArray(size)
    BufferedInputStream(FileInputStream(this)).use { bis ->
        if (bis.read(result, 0, result.size) != -1) return result
    }
    return null
}
