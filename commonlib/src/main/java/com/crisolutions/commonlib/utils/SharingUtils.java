package com.crisolutions.commonlib.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.content.FileProvider;
import android.util.Base64;
import android.widget.Toast;

import com.crisolutions.commonlib.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class SharingUtils {
    public static void sendEmail(Context context, String email) {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", email, null));
        context.startActivity(Intent.createChooser(emailIntent, null));
    }

    public static void openDialer(Context context, String phone) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
        context.startActivity(intent);
    }

    public static void openMaps(Context context, double lat, double lng) {
        String url = "http://maps.google.com/maps?daddr=" + lat + "," +
                lng + "&f=d&dirflg=d&nav=1";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));

        context.startActivity(intent);
    }

    public static void openMaps(Context context, String address) {
        String url = "http://maps.google.com/maps?q=" + address;
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));

        context.startActivity(intent);
    }

    /**
     * This method opens native sharing dialog for images coded in Base64. It creates a Bitmap from Base64 encoded
     *
     * @param imageContent Base64 string of the image
     */
    public static void shareImage(Context context, String imageContent) {
        byte[] checkFront = Base64.decode(imageContent, Base64.DEFAULT);

        Bitmap image = BitmapFactory.decodeByteArray(checkFront, 0, checkFront.length);

        try {

            File cachePath = new File(context.getCacheDir(), "images");
            cachePath.mkdirs(); // don't forget to make the directory
            FileOutputStream stream = new FileOutputStream(cachePath + "/image.png"); // overwrites this image every
            // time
            image.compress(Bitmap.CompressFormat.PNG, 100, stream);
            stream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        File imagePath = new File(context.getCacheDir(), "images");
        File newFile = new File(imagePath, "image.png");
        shareFile(context, newFile);
    }

    public static void shareFile(Context context, File file) {
        Uri contentUri = FileProvider.getUriForFile(context, context.getPackageName() + ".fileprovider", file);

        if (contentUri != null) {
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); // temp permission for receiving app to read
            // this file
            shareIntent.setDataAndType(contentUri, context.getContentResolver().getType(contentUri));
            shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri);
            context.startActivity(Intent.createChooser(shareIntent, "ShareFile"));
        }
    }

    public static void copyToClipboard(Context context, String label, String text) {
        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText(label, text);
        clipboardManager.setPrimaryClip(clip);

        Toast.makeText(context, context.getString(R.string.message_copied_to_clipboard), Toast.LENGTH_SHORT).show();
    }
}
