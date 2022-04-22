package com.hasanbektas.carsbook

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hasanbektas.carsbook.databinding.RecyclerRowBinding

class CarAdapter(val carList: ArrayList<Car>) : RecyclerView.Adapter<CarAdapter.CarHolder>() {

    class CarHolder(val binding : RecyclerRowBinding) : RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarHolder {
        val binding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CarHolder(binding)
    }
    override fun onBindViewHolder(holder: CarHolder, position: Int) {

        holder.binding.recyclerTextView.text = carList.get(position).name
        holder.binding.recyclerImgView.setImageBitmap(carList.get(position).image)

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context,CarActivity::class.java)
            intent.putExtra("info","detail")
            intent.putExtra("id",carList.get(position).id)
            holder.itemView.context.startActivity(intent)
        }
    }
    override fun getItemCount(): Int {
        return carList.size
    }
}