package com.example.currencyconverter.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyconverter.databinding.RecyItemBinding
import com.example.currencyconverter.models.JsonModels

class MyRecyAdapter(val list:ArrayList<JsonModels>,val onClick: OnClick,val imageList:ArrayList<Int>,):RecyclerView.Adapter<MyRecyAdapter.MHVH>() {
    inner class MHVH(val recyItemBinding: RecyItemBinding):RecyclerView.ViewHolder(recyItemBinding.root){
        fun onBind(jsonModels: JsonModels,position: Int){
            recyItemBinding.imageRecy.setImageResource(imageList[position])
            recyItemBinding.senaName.text = jsonModels.title
            recyItemBinding.senaDollar.text = jsonModels.cb_price
            recyItemBinding.senaCode.text = jsonModels.code
            recyItemBinding.root.setOnClickListener {
                onClick.onClick(jsonModels,position)
            }

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MHVH {
        return MHVH(RecyItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: MHVH, position: Int) {
        holder.onBind(list[position],position)
    }
    interface OnClick{
        fun onClick(jsonModels: JsonModels,position: Int)
    }
}