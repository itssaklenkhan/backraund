package com.example.backraund

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.os.Environment
import android.view.View
import android.widget.Toast
import androidx.core.graphics.drawable.toBitmap
import com.slowmac.autobackgroundremover.BackgroundRemover
import com.slowmac.autobackgroundremover.BackgroundRemover.removeBackgroundFromImage
import com.slowmac.autobackgroundremover.OnBackgroundChangeListener

import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date

/** BackraundPlugin */
class BackraundPlugin : FlutterPlugin, MethodCallHandler {
    /// The MethodChannel that will the communication between Flutter and native Android
    /// This local reference serves to register the plugin with the Flutter Engine and unregister it
    /// when the Flutter Engine is detached from the Activity
    private lateinit var channel: MethodChannel

    override fun onAttachedToEngine(flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
        channel = MethodChannel(flutterPluginBinding.binaryMessenger, "backraund")
        channel.setMethodCallHandler(this)
    }

    override fun onMethodCall(call: MethodCall, result: Result) {
        if (call.method == "removeBackraund") {
//            result.success("Android ${android.os.Build.VERSION.RELEASE}")
            val image = call.argument<String>("imagePath")!!
//            String parp = call.argument<String>("imagePath")
            removeBg(result, image)

        } else {
            result.notImplemented()
        }
    }


    override fun onDetachedFromEngine(binding: FlutterPlugin.FlutterPluginBinding) {
        channel.setMethodCallHandler(null)
    }


    private fun removeBg(
        result: MethodChannel.Result,
        imagePath: String
    ) {
//        binding.img.invalidate()
        val options = BitmapFactory.Options()
        options.inPreferredConfig = Bitmap.Config.ARGB_8888
        val bitmap = BitmapFactory.decodeFile(imagePath, options)

        BackgroundRemover.bitmapForProcessing(
            bitmap,
            true,
            object : OnBackgroundChangeListener {
                override fun onSuccess(bitmap: Bitmap) {

//                    // Convert the processed bitmap to a byte array
//                    val byteArrayOutputStream = ByteArrayOutputStream()
//                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
//                    val byteArray = byteArrayOutputStream.toByteArray()
//
//
//                    // val processedImagePath = saveProcessedBitmapToFile(byteArray)
//                    // Send the result back to Flutter
//                    result.success(byteArray)
                    /////


                    // If the alpha channel is not present, create a new bitmap with an alpha channel
                    val transparentBitmap = Bitmap.createBitmap(
                        bitmap.width,
                        bitmap.height,
                        Bitmap.Config.ARGB_8888
                    )
                    val canvas = Canvas(transparentBitmap)
                    canvas.drawBitmap(bitmap, 0f, 0f, null)
                    bitmap.recycle() // Recycle the original bitmap

                    // Convert the transparentBitmap to a byte array
                    val byteArrayOutputStream = ByteArrayOutputStream()
                    transparentBitmap.compress(
                        Bitmap.CompressFormat.PNG,
                        100,
                        byteArrayOutputStream
                    )
                    val byteArray = byteArrayOutputStream.toByteArray()

                    result.success(byteArray)


                }

                override fun onFailed(exception: Exception) {
//                    Toast.makeText(this@MainActivity, "Error Occurred", Toast.LENGTH_SHORT).show()
//                    result.error(
//                        "BACKGROUND_REMOVAL_ERROR",
//                        "Error occurred while removing background",
//                        null
//                    )
                }
            })
    }

    private fun saveProcessedBitmapToFile(byteArray: ByteArray): String {
        // You can choose a directory to save the processed image, for example:
        val directory = File(Environment.getExternalStorageDirectory(), "YourDirectory")
        if (!directory.exists()) {
            directory.mkdirs()
        }

        // Generate a unique file name or use a timestamp
        val timestamp = SimpleDateFormat("yyyyMMddHHmmss").format(Date())
        val fileName = "processed_image_$timestamp.png"

        val file = File(directory, fileName)
        val fileOutputStream = FileOutputStream(file)
        fileOutputStream.write(byteArray)
        fileOutputStream.close()

        return file.absolutePath
    }
}
