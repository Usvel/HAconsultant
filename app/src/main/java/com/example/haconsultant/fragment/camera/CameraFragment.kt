package com.example.haconsultant.fragment.camera

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.haconsultant.R
import com.example.haconsultant.fragment.BackStackLiveData
import com.example.haconsultant.fragment.StatusCamera
import com.google.mlkit.vision.barcode.Barcode
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import kotlinx.android.synthetic.main.fragment_camera.*
import java.nio.ByteBuffer
import java.util.HashMap
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class CameraFragment : Fragment() {

    val viewModel: BackStackLiveData by lazy {
        ViewModelProvider(requireActivity()).get(BackStackLiveData::class.java)
    }

    private var okCamera = 0

    private var fragmentInteractor: CameraFragmentInteractor? = null

    private lateinit var cameraExecutor: ExecutorService

    private lateinit var safeContext: Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        safeContext = context
        if (context is CameraFragmentInteractor) {
            fragmentInteractor = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_camera, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.setStatusCamera(StatusCamera.CameraOn)

        // Request camera permissions
        if (allPermissionsGranted()) {
            startCamera()
        } else {
            requestPermissions(REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS)
        }

        cameraExecutor = Executors.newSingleThreadExecutor()
//        cameraExecutor = Executors.newCachedThreadPool()

        cameraBtnBack.setOnClickListener {
            fragmentInteractor?.onCameraBack()
        }
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(safeContext)

        cameraProviderFuture.addListener(Runnable {
            // Used to bind the lifecycle of cameras to the lifecycle owner
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            // Preview
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(viewFinder.createSurfaceProvider())
                }


            val imageAnalyzer = ImageAnalysis.Builder()
                .build()
                .also {
                    it.setAnalyzer(
                        cameraExecutor,
                        LuminosityAnalyzer() { codeVendor, name ->
                            okCamera++
                            if (okCamera == 1) fragmentInteractor?.onCameraOk(
                                codeVendor,
                                name
                            )
                        })
                }

            // Select back camera as a default
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                // Unbind use cases before rebinding
                cameraProvider.unbindAll()

//                // Bind use cases to camera
//                cameraProvider.bindToLifecycle(
//                    this, cameraSelector, preview
//                )

                cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview, imageAnalyzer
                )
            } catch (exc: Exception) {
                Log.e(TAG, "Use case binding failed", exc)
            }

        }, ContextCompat.getMainExecutor(safeContext))
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            safeContext, it
        ) == PackageManager.PERMISSION_GRANTED
    }

    override fun onDestroy() {
        cameraExecutor.shutdown()
        viewModel.setStatusCamera(StatusCamera.CameraOff)
        super.onDestroy()
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                startCamera()
            } else {
                fragmentInteractor?.onCameraBack()
            }
        }
    }


    companion object {
        private const val TAG = "CameraXBasic"
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    }
}

//класс для анализа
//Удалить контекст Не забыть
private class LuminosityAnalyzer(private val onResult: (codeVendo: String?, name: String?) -> Unit) :
    ImageAnalysis.Analyzer {

    private fun ByteBuffer.toByteArray(): ByteArray {
        rewind()    // Rewind the buffer to zero
        val data = ByteArray(remaining())
        get(data)   // Copy the buffer into a byte array
        return data // Return the byte array
    }

    override fun analyze(image: ImageProxy) {

        val buffer = image.planes[0].buffer
        val data = buffer.toByteArray()
        val pixels = data.map { it.toInt() and 0xFF }
        val luma = pixels.average()

        //Log.d("IMAGE", "NEWIMAGE")

        //val options = BarcodeScannerOptions.Builder().setBarcodeFormats().build()
        //в getСlient(option)
        val barcodeScanner: BarcodeScanner = BarcodeScanning.getClient()
        processImageProxy(barcodeScanner, image)

        //image.close()
    }


    @SuppressLint("UnsafeExperimentalUsageError")
    private fun processImageProxy(
        barcodeScanner: BarcodeScanner,
        imageProxy: ImageProxy
    ) {
        val inputImage =
            InputImage.fromMediaImage(imageProxy.image!!, imageProxy.imageInfo.rotationDegrees)

        barcodeScanner.process(inputImage)
            .addOnSuccessListener { barcodes ->
                if (barcodes != null && barcodes.size > 0) {
                    barcodes.forEach { barcode ->
                        val scannedFormat = barcode.getFormat();
                        when (scannedFormat) {
                            Barcode.FORMAT_QR_CODE -> {
                                var name: String? = null
                                var codeVendor: String? = null
                                val pairs = barcode.rawValue.split("\\|".toRegex()).toTypedArray()
                                for (pair in pairs) {
                                    val keyValue = pair.split(":".toRegex()).toTypedArray()
                                    //Log.d("Tag", "${keyValue[0]} + ${keyValue[1]}")
                                    when (keyValue[0]) {
                                        "CodeVendor" -> {
                                            codeVendor = keyValue[1]
                                        }
                                        "Id" -> {
                                            name = keyValue[1]
                                        }
                                    }
                                }
                                if (codeVendor != null || (name != null)) {
                                    imageProxy.close()
                                    onResult(codeVendor, name)
                                }
                            }
                            else -> {

                            }
                        }
                    }
                }
            }
            .addOnFailureListener {
                Log.e("IMAGE", it.message)
            }.addOnCompleteListener {
                // When the image is from CameraX analysis use case, must call image.close() on received
                // images when finished using them. Otherwise, new images may not be received or the camera
                // may stall.
                imageProxy.close()
            }
    }
}