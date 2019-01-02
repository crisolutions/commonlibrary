package com.crisolutions.commonlib;

import android.content.Intent;

import androidx.annotation.NonNull;

public interface ActivityResultTask {

    void onActivityResult(int requestCode, int resultCode, Intent data);

    void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                    @NonNull int[] grantResults);
}
