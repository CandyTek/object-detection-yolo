@file:Suppress("DEPRECATION")

package dev.smoketrees.object_detection_yolo.activities

import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.otaliastudios.cameraview.CameraListener
import com.otaliastudios.cameraview.PictureResult
import com.otaliastudios.cameraview.gesture.Gesture
import com.otaliastudios.cameraview.gesture.GestureAction
import dev.smoketrees.object_detection_yolo.databinding.ActivityCameraBinding

class CameraActivity : AppCompatActivity() {
    private val binding by lazy { ActivityCameraBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(binding.root)
        setupCamera()

        binding.cameraFab.setOnClickListener {

            binding.camera.takePicture()
        }
    }

    private fun setupCamera() {
        binding.camera.setLifecycleOwner(this)
        binding.camera.mapGesture(Gesture.PINCH, GestureAction.ZOOM)
       binding. camera.mapGesture(Gesture.TAP, GestureAction.AUTO_FOCUS)
       binding. camera.addCameraListener(object : CameraListener() {
            override fun onPictureTaken(result: PictureResult) {
                super.onPictureTaken(result)
                result.toBitmap {bitmap ->
                    if (bitmap != null) {
                        MainActivity.clickedImage = bitmap
                        finish()
                    } else {
                        Toast.makeText(this@CameraActivity, "Something went wrong. Try again.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }
}
