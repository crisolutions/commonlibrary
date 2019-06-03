@file:JvmName("SharingUtils")

package com.crisolutions.commonlibraryktx

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_SEND
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import java.io.File
import java.io.FileOutputStream

fun Context.sendEmail(email: String) {
    val intent = Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", email, null))
    startActivity(Intent.createChooser(intent, null))
}

fun Context.openDialer(no: String) {
    val intent = Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", no, null)).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK
    }
    startActivity(intent)
}

fun Context.openMapWithLocation(lat: Double, lng: Double) {
    val url = "http://maps.google.com/maps?daddr=$lat,$lng&f=d&dirflg=d&nav=1"
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    startActivity(intent)
}

fun Context.openMapFromAddress(address: String) {
    startActivity(Intent(Intent.ACTION_VIEW, "http://maps.google.com/maps?q=$address".toUri()))
}

/**
 * This method opens the native sharing dialog for images.
 */
fun Context.shareImage(imageBytes: ByteArray) {
    val image = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
    val cachePath = File(cacheDir, "images")
    val imageFilename = "image.png"

    try {
        // Don't forget to make the directory.
        cachePath.mkdirs()
        val stream = FileOutputStream("$cachePath/$imageFilename")
        // Overwrite this image every time
        image.compress(Bitmap.CompressFormat.PNG, 100, stream)
        stream.close()
    } catch (e: Exception) {
        e.printStackTrace()
    }

    shareFile(File(cachePath, imageFilename))
}

fun Context.shareFile(file: File) {
    val contentUri = FileProvider.getUriForFile(this, "$packageName.fileprovider", file)

    contentUri?.let {
        val shareIntent = Intent()
        shareIntent.action = ACTION_SEND
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        shareIntent.setDataAndType(contentUri, contentResolver.getType(contentUri))
        shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri)
        startActivity(Intent.createChooser(shareIntent, "ShareFile"))
    }
}

fun String.copyToClipboard(context: Context, label: String) {
    val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clipData = ClipData.newPlainText(label, this)
    clipData?.let {
        clipboardManager.primaryClip = clipData
        Toast.makeText(context, "Copied to Clipboard", LENGTH_SHORT).show()
    } ?: run {
        Toast.makeText(context, "Error copying to Clipboard", LENGTH_SHORT).show()
    }
}
