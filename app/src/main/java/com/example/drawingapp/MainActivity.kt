package com.example.drawingapp

import android.app.Dialog
import android.Manifest
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

class MainActivity : AppCompatActivity() {
    var drawingView: DrawingView? = null
    var iv_brushSize: ImageButton? = null
    var mchoosedcolor: ImageButton? = null
    var iv_gallery:ImageButton?=null
    var iv_imageset:ImageView?=null
    var iv_undo:ImageButton?=null
    var iv_redo:ImageButton?=null
    var iv_save:ImageButton?=null

    var customProgressDialog: Dialog? = null
    private val pickImage = 100
    private var imageUri: Uri? = null
    val requestPermission: ActivityResultLauncher<Array<String>> =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
                permissions ->
            permissions.entries.forEach {
                val perMissionName = it.key
                val isGranted = it.value
                if (isGranted && perMissionName==Manifest.permission.READ_EXTERNAL_STORAGE) {
                    val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
                    startActivityForResult(gallery, pickImage)
                }
                else if (isGranted && perMissionName==Manifest.permission.WRITE_EXTERNAL_STORAGE) {
                    // TODO
                }
                else {
                    if (perMissionName == Manifest.permission.READ_EXTERNAL_STORAGE)
                        Toast.makeText(this@MainActivity, "Oops you just denied the permission.", Toast.LENGTH_LONG
                        ).show()
                }
            }

        }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        drawingView = findViewById(R.id.drawingview)
        iv_brushSize = findViewById(R.id.brush_iv)
        iv_gallery=findViewById(R.id.gallery_iv)
        iv_imageset=findViewById(R.id.imageset_iv)
        iv_undo=findViewById(R.id.undo_iv)
        iv_redo=findViewById(R.id.redo_iv)
        iv_save=findViewById(R.id.save_iv)
        iv_brushSize?.setOnClickListener {
            setBrushSize()
        }
        iv_gallery?.setOnClickListener {
            requestStoragePermission()
        }
        iv_undo?.setOnClickListener {
            drawingView?.UndoFunction()
        }
        iv_redo?.setOnClickListener {
            drawingView?.RedoFunction()
        }
        iv_save?.setOnClickListener {
            iv_save?.setOnClickListener {
                if (isReadStorageAllowed()) {
                    showProgressDialog()
                    var fl: FrameLayout =findViewById(R.id.framelayout)
                    val bitmap = getScreenShotFromView(fl)
                    if (bitmap != null) {
                        saveMediaToStorage(bitmap)
                    }
                }
            }
        }
    }

    private fun setBrushSize() {
        var brushDialog = Dialog(this)
        brushDialog.setContentView(R.layout.brush_size_dialog_bg)

        var imagebtn1 = brushDialog.findViewById<ImageButton>(R.id.imageView)
        var imagebtn2 = brushDialog.findViewById<ImageButton>(R.id.imageView2)
        var imagebtn3 = brushDialog.findViewById<ImageButton>(R.id.imageView3)
        var imagebtn4 = brushDialog.findViewById<ImageButton>(R.id.imageView4)
        var imagebtn5 = brushDialog.findViewById<ImageButton>(R.id.imageView5)

        imagebtn1.setOnClickListener {
            drawingView?.setBrushSize(5.toFloat())
            brushDialog.dismiss()
        }
        imagebtn2.setOnClickListener {
            drawingView?.setBrushSize(10.toFloat())
            brushDialog.dismiss()
        }
        imagebtn3.setOnClickListener {
            drawingView?.setBrushSize(15.toFloat())
            brushDialog.dismiss()
        }
        imagebtn4.setOnClickListener {
            drawingView?.setBrushSize(20.toFloat())
            brushDialog.dismiss()
        }
        imagebtn5.setOnClickListener {
            drawingView?.setBrushSize(25.toFloat())
            brushDialog.dismiss()
        }
        brushDialog.show()
    }
    fun setColor(view: View) {
        when (view.id) {
            R.id.colorBlack_iv -> {
                var tagc: String = view.tag.toString()
                drawingView?.setColor(tagc)
                view.getLayoutParams().height = 100
                view.getLayoutParams().width = 100
                view.requestLayout()
                mchoosedcolor?.getLayoutParams()?.height = 80
                mchoosedcolor?.getLayoutParams()?.width = 80
                mchoosedcolor?.requestLayout()
                mchoosedcolor = findViewById(R.id.colorBlack_iv)
            }

            R.id.colorGrey_iv -> {
                var tagc: String = view.tag.toString()
                drawingView?.setColor(tagc)
                view.getLayoutParams().height = 100
                view.getLayoutParams().width = 100
                view.requestLayout()
                mchoosedcolor?.getLayoutParams()?.height = 80
                mchoosedcolor?.getLayoutParams()?.width = 80
                mchoosedcolor?.requestLayout()
                mchoosedcolor = findViewById(R.id.colorGrey_iv)
            }
            R.id.colorBlue_iv -> {
                var tagc: String = view.tag.toString()
                drawingView?.setColor(tagc)
                view.getLayoutParams().height = 100
                view.getLayoutParams().width = 100
                view.requestLayout()
                mchoosedcolor?.getLayoutParams()?.height = 80
                mchoosedcolor?.getLayoutParams()?.width = 80
                mchoosedcolor?.requestLayout()
                mchoosedcolor = findViewById(R.id.colorBlue_iv)
            }
            R.id.colorPink_iv -> {
                var tagc: String = view.tag.toString()
                drawingView?.setColor(tagc)
                view.getLayoutParams().height = 100
                view.getLayoutParams().width = 100
                view.requestLayout()
                mchoosedcolor?.getLayoutParams()?.height = 80
                mchoosedcolor?.getLayoutParams()?.width = 80
                mchoosedcolor?.requestLayout()
                mchoosedcolor = findViewById(R.id.colorPink_iv)
            }
            R.id.colorPurple_iv -> {
                var tagc: String = view.tag.toString()
                drawingView?.setColor(tagc)
                view.getLayoutParams().height = 100
                view.getLayoutParams().width = 100
                view.requestLayout()
                mchoosedcolor?.getLayoutParams()?.height = 80
                mchoosedcolor?.getLayoutParams()?.width = 80
                mchoosedcolor?.requestLayout()
                mchoosedcolor = findViewById(R.id.colorPurple_iv)
            }
            R.id.colorGreen_iv -> {
                var tagc: String = view.tag.toString()
                drawingView?.setColor(tagc)
                view.getLayoutParams().height = 100
                view.getLayoutParams().width = 100
                view.requestLayout()
                mchoosedcolor?.getLayoutParams()?.height = 80
                mchoosedcolor?.getLayoutParams()?.width = 80
                mchoosedcolor?.requestLayout()
                mchoosedcolor = findViewById(R.id.colorGreen_iv)
            }
            R.id.colorYellow_iv -> {
                var tagc: String = view.tag.toString()
                drawingView?.setColor(tagc)
                view.getLayoutParams().height = 100
                view.getLayoutParams().width = 100
                view.requestLayout()
                mchoosedcolor?.getLayoutParams()?.height = 80
                mchoosedcolor?.getLayoutParams()?.width = 80
                mchoosedcolor?.requestLayout()
                mchoosedcolor = findViewById(R.id.colorYellow_iv)
            }
            R.id.colorRed_iv -> {
                var tagc: String = view.tag.toString()
                drawingView?.setColor(tagc)
                view.getLayoutParams().height = 100
                view.getLayoutParams().width = 100
                view.requestLayout()
                mchoosedcolor?.getLayoutParams()?.height = 80
                mchoosedcolor?.getLayoutParams()?.width = 80
                mchoosedcolor?.requestLayout()
                mchoosedcolor = findViewById(R.id.colorRed_iv)
            }
            R.id.colorSkin_iv -> {
                var tagc: String = view.tag.toString()
                drawingView?.setColor(tagc)
                view.getLayoutParams().height = 100
                view.getLayoutParams().width = 100
                view.requestLayout()
                mchoosedcolor?.getLayoutParams()?.height = 80
                mchoosedcolor?.getLayoutParams()?.width = 80
                mchoosedcolor?.requestLayout()
                mchoosedcolor = findViewById(R.id.colorSkin_iv)
            }


        }
    }
    private fun showRationaleDialog(title:String,message:String) {
        val builder: AlertDialog.Builder=AlertDialog.Builder(this)
        builder.setTitle(title)
            .setMessage(message)
            .setPositiveButton("cancel"){
                    dialog, _ ->
                dialog.dismiss()
            }
        builder.create().show()
    }
    private fun requestStoragePermission(){
        if (
            ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
        ){
            showRationaleDialog("Kids Drawing App","Kids Drawing App " +
                    "needs to Access Your External Storage")
        }
        else {
            requestPermission.launch(
                arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            )
        }

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == pickImage) {
            imageUri = data?.data
            iv_imageset?.setImageURI(imageUri)
        }
    }
    private fun isReadStorageAllowed(): Boolean {
        val result = ContextCompat.checkSelfPermission(
            this, Manifest.permission.READ_EXTERNAL_STORAGE
        )
        return result == PackageManager.PERMISSION_GRANTED
    }
    private fun showProgressDialog() {
        customProgressDialog = Dialog(this@MainActivity)

        /*Set the screen content from a layout resource.
        The resource will be inflated, adding all top-level views to the screen.*/
        customProgressDialog?.setContentView(R.layout.dialog_custom_progress)

        //Start the dialog and display it on screen.
        customProgressDialog?.show()
    }
    private fun cancelProgressDialog() {
        if (customProgressDialog != null) {
            customProgressDialog?.dismiss()
            customProgressDialog = null
        }
    }
    private fun getScreenShotFromView(v: View): Bitmap? {
        var screenshot: Bitmap? = null
        try {
            screenshot = Bitmap.createBitmap(v.measuredWidth, v.measuredHeight, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(screenshot)
            v.draw(canvas)
        } catch (e: Exception) {
            Toast.makeText(this,"NOT POSSIBLE",Toast.LENGTH_LONG).show();
        }
        return screenshot
    }
    private  fun saveMediaToStorage(bitmap: Bitmap) {
        val filename = "${System.currentTimeMillis()}.jpg"
        var fos: OutputStream? = null
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            this.contentResolver?.also { resolver ->
                val contentValues = ContentValues().apply {
                    put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                    put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
                    put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
                }
                val imageUri: Uri? =
                    resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
                fos = imageUri?.let { resolver.openOutputStream(it) }
            }
        } else {
            val imagesDir =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            val image = File(imagesDir, filename)
            fos = FileOutputStream(image)
        }
        fos?.use {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
            Toast.makeText(this@MainActivity, "Captured View and saved to Gallery", Toast.LENGTH_SHORT)
                .show()

        }
        cancelProgressDialog()

    }
}