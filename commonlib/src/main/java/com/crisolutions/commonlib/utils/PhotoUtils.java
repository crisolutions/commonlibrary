package com.crisolutions.commonlib.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.media.ExifInterface;
import timber.log.Timber;

import java.io.IOException;
import java.io.InputStream;

public final class PhotoUtils {

    private PhotoUtils() {
    }

    @SuppressWarnings("MagicNumber")
    @Nullable
    public static Bitmap getRotatedBitmap(@NonNull Context context, @NonNull Uri uri) {
        try {
            InputStream is = context.getContentResolver().openInputStream(uri);
            if (is != null) {
                if (is.markSupported()) {
                    is.mark(is.available());
                }
                ExifInterface exifInterface = new ExifInterface(is);
                int orientation = exifInterface.getAttributeInt(
                        ExifInterface.TAG_ORIENTATION,
                        ExifInterface.ORIENTATION_NORMAL);
                int rotation = 0;
                switch (orientation) {
                    case ExifInterface.ORIENTATION_ROTATE_90:
                        rotation = 90;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_180:
                        rotation = 180;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_270:
                        rotation = 270;
                        break;
                }
                Bitmap bitmap;
                if (is.markSupported()) {
                    is.reset();
                    bitmap = BitmapFactory.decodeStream(is);
                } else {
                    bitmap = BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri));
                }
                if (rotation > 0) {
                    Matrix matrix = new Matrix();
                    matrix.postRotate(rotation);
                    bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                }
                return bitmap;
            } else {
                return null;
            }
        } catch (IOException e) {
            Timber.e(e, "Error getting rotated bitmap");
            return null;
        }
    }
}
