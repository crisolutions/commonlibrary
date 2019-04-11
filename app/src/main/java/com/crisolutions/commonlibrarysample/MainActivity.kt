package com.crisolutions.commonlibrarysample

import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.PermissionChecker.PERMISSION_GRANTED
import com.crisolutions.commonlib.utils.permissions.PermissionCheckerUtil
import com.crisolutions.commonlib.utils.permissions.PermissionType
import com.crisolutions.commonlibrarysample.Samples.SampleJ
import com.crisolutions.commonlibrarysample.Samples.SampleKt
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sampleK = SampleKt()
        val sampleJ = SampleJ()

        //sample.stripNonDigitsSample()
        //samplej.stripNonDigitsSample()

        //sampleK.arrayToMultiLineStringSample()
        //sampleJ.arrayToMultiLineStringSample()

        sampleK.capitalizeWords()
        sampleJ.capitalizeWords()

        button_request_permission.setOnClickListener {
            if (PermissionCheckerUtil.hasPermission(PermissionType.CAMERA, this)) {
                Toast.makeText(this, "Camera Permission has been granted", Toast.LENGTH_SHORT).show()
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(arrayOf(PermissionType.CAMERA.permissionString), REQUEST_CODE_CAMERA_PERMISSION)
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_CAMERA_PERMISSION) {
            if (grantResults[0] == PERMISSION_GRANTED) {
                Toast.makeText(this, "Camera Permission granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Camera Permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        private const val REQUEST_CODE_CAMERA_PERMISSION: Int = 4
    }
}
