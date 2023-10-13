package com.example.currencyconverter.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyconverter.databinding.ItemViewpagerBinding
import com.example.currencyconverter.models.JsonModels
import com.example.currencyconverter.models.ViewPagerModels

class MyAdapterView(val list:ArrayList<JsonModels> = ArrayList(), val rvClick:onClick,val imageList:ArrayList<Int>):RecyclerView.Adapter<MyAdapterView.MyVH>() {
    inner class MyVH(val itemViewpagerBinding: ItemViewpagerBinding):RecyclerView.ViewHolder(itemViewpagerBinding.root){
        fun onBind(viewPagerModels: JsonModels,position: Int){
            itemViewpagerBinding.imagee.setImageResource(imageList[position])
            itemViewpagerBinding.currencyDate.text = viewPagerModels.date
            itemViewpagerBinding.converterText.text = viewPagerModels.title
            itemViewpagerBinding.currencyRate.text = viewPagerModels.nbu_buy_price

            itemViewpagerBinding.root.setOnClickListener {
                rvClick.ViewClick(viewPagerModels, position = position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyVH {
        return MyVH(ItemViewpagerBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int=list.size

    override fun onBindViewHolder(holder: MyVH, position: Int) {
        holder.onBind(list[position],position)
    }
    interface onClick{
        fun ViewClick(viewPagerModels: JsonModels,position: Int)
    }
}