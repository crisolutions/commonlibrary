package com.crisolutions.commonlib.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import io.reactivex.functions.Function;
import okhttp3.ResponseBody;
import timber.log.Timber;

public final class FileUtils {

    private static final int BUFFER_SIZE = 4096;
    private static final String SHARE_DIR = "shared/";
    private static final int JPEG_QUALITY = 80;
    private static final SimpleDateFormat FILE_TIMESTAMP_FORMATTER =
            new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());

    private FileUtils() {
    }

    @Nullable
    public static File writeBytesToTempFile(@NonNull Context context, byte[] bytes, String extension) {
        File tempFile = getTempFile(context, extension);
        OutputStream outputStream;
        try {
            outputStream = new FileOutputStream(tempFile);
            outputStream.write(bytes);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return tempFile;
    }

    public static Uri getPhotoOutputFile(@NonNull Context context) {
        @SuppressLint("SimpleDateFormat")
        File file = getShareableFile(context, FILE_TIMESTAMP_FORMATTER.format(new Date()) + ".jpg");
        return FileProvider.getUriForFile(
                context,
                context.getApplicationContext().getPackageName() + ".fileprovider",
                file
        );
    }

    @Nullable
    public static Uri textToHtml(@NonNull Context context, String text, String fileName) {
        Uri fileUri = null;
        try {
            File tempFile = getShareableFile(context, fileName);
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
            writer.write(text);
            writer.flush();
            writer.close();
            try {
                fileUri = FileProvider.getUriForFile(
                        context,
                        context.getPackageName() + ".fileprovider",
                        tempFile);
            } catch (IllegalArgumentException e) {
                Timber.e(e, "Error getting share URI for file: %s", tempFile.getPath());
            }
        } catch (IOException e) {
            Timber.e(e, "Error writing text to file");
        }
        return fileUri;
    }

    public static Function<ResponseBody, File> parseResponseToFile(@NonNull Context context, String tempFileName) {
        return responseBody -> {
            File tempFile = FileUtils.getShareableFile(context, tempFileName);
            try (OutputStream outputStream = new FileOutputStream(tempFile);
                 InputStream inputStream = responseBody.byteStream()) {
                FileUtils.writeInputToOutput(inputStream, outputStream);
            }
            return tempFile;
        };
    }

    public static File writeBitmapToFile(@NonNull Context context, Bitmap bitmap) throws IOException {
        File bitmapFile = getTempFile(context, ".jpg");
        try (FileOutputStream fos = new FileOutputStream(bitmapFile)) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, JPEG_QUALITY, fos);
        }
        return bitmapFile;
    }

    public static byte[] bytesFromFile(@NonNull File file) throws IOException {
        int size = (int) file.length();
        byte[] result = new byte[size];
        try (BufferedInputStream is = new BufferedInputStream(new FileInputStream(file))) {
            if (is.read(result, 0, result.length) != -1) {
                return result;
            }
        }
        throw new IOException("Error reading file into byte[]");
    }

    /**
     * Writes bytes from an input stream to an output stream using a buffer. This does NOT close/flush streams
     * afterwards; it is assumed that the stream instances are passed here from within a try-with-resources block.
     */
    private static void writeInputToOutput(
            @NonNull InputStream inputStream,
            @NonNull OutputStream outputStream
    ) throws IOException {
        byte[] buffer = new byte[BUFFER_SIZE];
        int read;
        while ((read = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, read);
        }
    }

    private static File getTempDirectory(@NonNull Context context) {
        return context.getCacheDir();
    }

    private static File getSharedDirectory(@NonNull Context context) {
        File shareDir = new File(context.getFilesDir(), SHARE_DIR);
        if (!shareDir.exists()) {
            //noinspection ResultOfMethodCallIgnored
            shareDir.mkdirs();
        }
        return shareDir;
    }

    private static File getTempFile(@NonNull Context context, String extension) {
        File tempDir = getTempDirectory(context);
        return new File(tempDir, new Date().toString().replace(" ", "") + extension);
    }

    private static File getShareableFile(@NonNull Context context, String fileName) {
        File shareDir = getSharedDirectory(context);
        return new File(shareDir, fileName);
    }
}
