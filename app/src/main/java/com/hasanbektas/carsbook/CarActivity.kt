package com.hasanbektas.carsbook

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.database.sqlite.SQLiteDatabase
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.media.Image
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.hasanbektas.carsbook.databinding.ActivityCarBinding
import java.io.ByteArrayOutputStream
import java.io.OutputStream

class CarActivity : AppCompatActivity() {
    private lateinit var intentResultLauncher : ActivityResultLauncher<Intent>
    private lateinit var permissionLaunchergallery : ActivityResultLauncher<String>
    private lateinit var permissionLaunchercamera : ActivityResultLauncher<String>
    val IMAGE_CAPTURE_CODE: Int = 12346
    var imageUri: Uri? = null
    var selectedBitmap : Bitmap? = null

    lateinit var binding: ActivityCarBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imageView.visibility = View.VISIBLE
        binding.camera.visibility = View.INVISIBLE
        binding.gallery.visibility = View.INVISIBLE

        registerLauncher()

        val intent = intent
        val info = intent.getStringExtra("info")

        if (info.equals("carAdd")){
            binding.carNameText.setText("")
            binding.modelNameText.setText("")
            binding.yearText.setText("")
            binding.button.visibility = View.VISIBLE
            binding.imageView.setImageResource(R.drawable.selectimage)

        }else{
            binding.button.visibility = View.INVISIBLE

            val selectedId = intent.getIntExtra("id",1)
            val database = this.openOrCreateDatabase("Cars", MODE_PRIVATE,null)
            val cursor = database.rawQuery("SELECT * FROM cars WHERE id = ?", arrayOf(selectedId.toString()))

            val carNamex= cursor.getColumnIndex("carname")
            val modelNamex = cursor.getColumnIndex("modelname")
            val yearx = cursor.getColumnIndex("year")
            val imagex = cursor.getColumnIndex("image")

            while (cursor.moveToNext()){

                binding.carNameText.setText(cursor.getString(carNamex))
                binding.modelNameText.setText(cursor.getString(modelNamex))
                binding.yearText.setText(cursor.getString(yearx))

                val byteArray = cursor.getBlob(imagex)
                val bitmap = BitmapFactory.decodeByteArray(byteArray,0,byteArray.size)
                binding.imageView.setImageBitmap(bitmap)
            }
            cursor.close()
        }
    }

    fun saveButton(view : View){

        val carName = binding.carNameText.text.toString()
        val modelName = binding.modelNameText.text.toString()
        val year = binding.yearText.text.toString()

        if (selectedBitmap !=null){
            val smallBitmap = makeSmallBitmap(selectedBitmap!!,300)

            val outputStream = ByteArrayOutputStream()
            smallBitmap.compress(Bitmap.CompressFormat.PNG,50,outputStream)
            val byteArray = outputStream.toByteArray()

            try {
                val database = this.openOrCreateDatabase("Cars", MODE_PRIVATE,null)
                database.execSQL("CREATE TABLE IF NOT EXISTS cars(id INTEGER PRIMARY KEY, carname VARCHAR, modelname VARCHAR, year VARCHAR, image BLOB)")
                val sqlString = "INSERT INTO cars(carname, modelname, year, image) VALUES(?,?,?,?)"
                val statement = database.compileStatement(sqlString)
                statement.bindString(1,carName)
                statement.bindString(2,modelName)
                statement.bindString(3,year)
                statement.bindBlob(4,byteArray)
                statement.execute()

            }catch (e:Exception){
                e.printStackTrace()
            }
            val intent = Intent(this@CarActivity, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }
    }
    private fun makeSmallBitmap(image:Bitmap,maximumSize : Int): Bitmap {

        var width = image.width
        var height = image.height

        var bitmapRatio : Double = width.toDouble() / height.toDouble()

        if (bitmapRatio > 1){
            width = maximumSize
            val scaledHeight = width / bitmapRatio
            height = scaledHeight.toInt()
        }else{
            height = maximumSize
            val scaledWidth = height * bitmapRatio
            width = scaledWidth.toInt()
        }
        return Bitmap.createScaledBitmap(image,width,height,true)
    }
    fun selectImage(view : View){
        binding.imageView.visibility = View.INVISIBLE
        binding.gallery.visibility = View.VISIBLE
        binding.camera.visibility = View.VISIBLE
    }

    fun galleryButton(view : View){
        if (ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){

            permissionLaunchergallery.launch(Manifest.permission.READ_EXTERNAL_STORAGE)

        }else {
            binding.imageView.visibility = View.VISIBLE
            binding.gallery.visibility = View.INVISIBLE
            binding.camera.visibility = View.INVISIBLE

            val intentToGallery = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intentResultLauncher.launch(intentToGallery)
        }
    }

    fun cameraButton(view : View){
        if(ContextCompat.checkSelfPermission(this,android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            permissionLaunchercamera.launch(android.Manifest.permission.CAMERA)
        }else{
            openCamera()
            binding.imageView.visibility = View.VISIBLE
            binding.gallery.visibility = View.INVISIBLE
            binding.camera.visibility = View.INVISIBLE
        }
    }

    private fun registerLauncher(){
        intentResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult(),
            ActivityResultCallback {
                if (it.resultCode == RESULT_OK){
                    val intentFromResult = it.data
                    if (intentFromResult != null){
                         val imageData = intentFromResult.data
                        try {
                            if (Build.VERSION.SDK_INT >= 28){
                                val source = ImageDecoder.createSource(contentResolver,imageData!!)
                                selectedBitmap = ImageDecoder.decodeBitmap(source)
                                binding.imageView.setImageBitmap(selectedBitmap)
                            }else {
                                selectedBitmap = MediaStore.Images.Media.getBitmap(contentResolver,imageData)
                                binding.imageView.setImageBitmap(selectedBitmap)
                            }
                        }catch (e:Exception){
                            e.printStackTrace()
                        }
                    }
                }
            })

        permissionLaunchergallery = registerForActivityResult(ActivityResultContracts.RequestPermission(),
            ActivityResultCallback {
                if(it){
                    val intentToGallery = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    intentResultLauncher.launch(intentToGallery)
                }else{
                    Toast.makeText(this@CarActivity,"Permission Needed",Toast.LENGTH_LONG).show()
                }
            })
        permissionLaunchercamera = registerForActivityResult(ActivityResultContracts.RequestPermission()){ result ->

            if (result ){

                binding.imageView.visibility = View.VISIBLE
                binding.gallery.visibility = View.INVISIBLE
                binding.camera.visibility = View.INVISIBLE

                openCamera()

            }else{
                Toast.makeText(applicationContext,"Permission needed!", Toast.LENGTH_LONG).show()
            }
        }
    }
    private fun openCamera() {

        val contentValues = ContentValues()
        contentValues.put(MediaStore.Images.Media.TITLE,"imageTitle")
        imageUri=contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,contentValues)

        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri)
        startActivityForResult(intent, IMAGE_CAPTURE_CODE)

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode== Activity.RESULT_OK){
            if (imageUri != null){
                try {
                    if (Build.VERSION.SDK_INT >=28){

                        val source = ImageDecoder.createSource(this@CarActivity.contentResolver,imageUri!!)
                        selectedBitmap = ImageDecoder.decodeBitmap(source)
                        binding.imageView.setImageBitmap(selectedBitmap)
                        println(selectedBitmap)

                    }else{
                        selectedBitmap = MediaStore.Images.Media.getBitmap(contentResolver,imageUri)
                        binding.imageView.setImageBitmap(selectedBitmap)
                    }

                } catch (e: java.lang.Exception){
                    e.printStackTrace()
                }
            }
        }
    }
}