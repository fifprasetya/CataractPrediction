package com.example.cataractpredict.prediction

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.example.cataractpredict.R
import com.example.cataractpredict.databinding.ActivityPredictionBinding
import com.example.cataractpredict.ml.MobilenetV110224Quant
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer

class PredictionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPredictionBinding

    var bitmap: Bitmap? = null
    lateinit var imgView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPredictionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val assetFile ="label.txt"
        val inputString = application.assets.open(assetFile).bufferedReader().use { it.readText() }
        val townList =inputString.split("\n")

        imgView = findViewById(R.id.img_poster)

        binding.addBtn.setOnClickListener(View.OnClickListener {
            var intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"

            startActivityForResult(intent, 100)
        })

        binding.predictBtn.setOnClickListener(View.OnClickListener {
            if (bitmap != null){
            var resized: Bitmap = Bitmap.createScaledBitmap(bitmap!!, 224, 224, true)
            val model = MobilenetV110224Quant.newInstance(this)

// Creates inputs for reference.
            val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 224, 224, 3), DataType.UINT8)

            var tbuffer = TensorImage.fromBitmap(resized)
            var byteBuffer = tbuffer.buffer

            inputFeature0.loadBuffer(byteBuffer)



// Runs model inference and gets result.
            val outputs = model.process(inputFeature0)
            val outputFeature0 = outputs.outputFeature0AsTensorBuffer

            var getMax = getMax(outputFeature0.floatArray)

            binding.tvResult.setText(townList[getMax])

// Releases model resources if no longer used.
            model.close()
            }
            else{
                Toast.makeText(this, "Silahkan Import foto Anda terlebih dahulu", Toast.LENGTH_SHORT).show()
            }
        })

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        imgView.setImageURI(data?.data)

        var uri: Uri? = data?.data

        bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, uri)
    }

    fun getMax(arr: FloatArray):Int{

        var index = 0
        var min = 0.0f

        for (i in 0..1000 ){
            if(arr[i]>min){
                index = i
                min = arr[i]
            }
        }

        return index
    }
}