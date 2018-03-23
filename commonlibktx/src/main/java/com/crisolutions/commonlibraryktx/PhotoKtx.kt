package com.crisolutions.commonlibraryktx

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import android.support.annotation.RequiresApi
import java.io.InputStream

@RequiresApi(Build.VERSION_CODES.N)

fun Uri.getRotatedBitmap(context: Context): Bitmap? {
    try {
        val inputStream: InputStream = context.contentResolver.openInputStream(this)
        if (inputStream.markSupported()) {
            inputStream.mark(inputStream.available())
        }
        val exifInterface = ExifInterface(inputStream)
        val orientation = exifInterface.getAttributeInt(
                ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_NORMAL
        )
        var rotation = 0f
        when (orientation) {
            ExifInterface.ORIENTATION_ROTATE_90 -> rotation = 90f
            ExifInterface.ORIENTATION_ROTATE_180 -> rotation = 180f
            ExifInterface.ORIENTATION_ROTATE_270 -> rotation = 270f
        }

        var bitmap: Bitmap?

        if (inputStream.markSupported()) {
            inputStream.reset()
            bitmap = BitmapFactory.decodeStream(inputStream)
        } else {
            bitmap = BitmapFactory.decodeStream(context.contentResolver.openInputStream(this))
        }
        if (rotation > 0) {
            val matrix = Matrix()
            matrix.postRotate(rotation)
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
        }
        return bitmap
    } catch (e: Exception) {
        e.printStackTrace()
        return null
    }
}
