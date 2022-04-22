package com.hasanbektas.carsbook

import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.hasanbektas.carsbook.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var carList : ArrayList<Car>
    private lateinit var carAdapter : CarAdapter

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        carList = ArrayList<Car>()

        carAdapter = CarAdapter(carList)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = carAdapter

        try {

            val database = this.openOrCreateDatabase("Cars", MODE_PRIVATE,null)
            val cursor = database.rawQuery("SELECT * FROM cars",null)
            val carNameIx = cursor.getColumnIndex("carname")
            val idIx = cursor.getColumnIndex("id")
            val imageIx = cursor.getColumnIndex("image")

            while (cursor.moveToNext()){

                val name = cursor.getString(carNameIx)
                val id = cursor.getInt(idIx)

                val byteArray = cursor.getBlob(imageIx)
                val image = BitmapFactory.decodeByteArray(byteArray,0,byteArray.size)
                val car = Car(name,id,image)
                carList.add(car)
            }
            carAdapter.notifyDataSetChanged()

            cursor.close()

        }catch (e: Exception){
            e.printStackTrace()
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.car_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.carAdd){
            val intent = Intent(this@MainActivity,CarActivity::class.java)
            intent.putExtra("info","carAdd")
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }
}