package com.crisolutions.commonlib;

import android.content.Intent;
import android.support.annotation.NonNull;

public interface ActivityResultTask {

    void onActivityResult(int requestCode, int resultCode, Intent data);

    void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                    @NonNull int[] grantResults);
}
