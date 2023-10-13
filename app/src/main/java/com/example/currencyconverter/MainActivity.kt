package com.example.currencyconverter

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.currencyconverter.adapter.MyAdapterView
import com.example.currencyconverter.adapter.MyRecyAdapter
import com.example.currencyconverter.databinding.ActivityMainBinding
import com.example.currencyconverter.databinding.ItemButtomSheetDialogBinding
import com.example.currencyconverter.models.JsonModels
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONArray


class MainActivity : AppCompatActivity(), MyAdapterView.onClick, MyRecyAdapter.OnClick {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    lateinit var myAdapterView: MyAdapterView
    lateinit var myAdapterView2: MyRecyAdapter
    lateinit var requestQueue: RequestQueue
    lateinit var imageList: ArrayList<Int>
    lateinit var jsonList: ArrayList<JsonModels>

    var d:Double = 1.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        requestQueue = Volley.newRequestQueue(this)
        loadList()
        jsonList = ArrayList()
        imageList = ArrayList()
        imageLoad()
        myAdapterView = MyAdapterView(jsonList, this, imageList)
        myAdapterView2 = MyRecyAdapter(jsonList, this, imageList)
        binding.recyView.adapter = myAdapterView2
        binding.viewPager2.adapter = myAdapterView

    }


    fun loadList() {
        val myRequest = JsonArrayRequest(com.android.volley.Request.Method.GET,
            "https://nbu.uz/uz/exchange-rates/json/",
            null,
            object : Response.Listener<JSONArray> {
                @SuppressLint("NotifyDataSetChanged")
                override fun onResponse(response: JSONArray?) {
                    for (data in 0 until response!!.length()) {
                        val typeToken = object : TypeToken<JsonModels>() {}.type
                        val jsonData =
                            Gson().fromJson<JsonModels>(response[data].toString(), typeToken)
                        if (!jsonData.nbu_cell_price.isNullOrEmpty()) {
                            jsonList.add(jsonData)
                        }
                        myAdapterView.notifyDataSetChanged()
                    }
                }
            },
            object : Response.ErrorListener {
                override fun onErrorResponse(error: VolleyError?) {
                    Toast.makeText(this@MainActivity, "Error", Toast.LENGTH_SHORT).show()
                }
            }
        )
        requestQueue.add(myRequest)
    }


    fun imageLoad() {
        imageList.add(R.drawable.shvetsariya)
        imageList.add(R.drawable.yevropa)
        imageList.add(R.drawable.angilya)
        imageList.add(R.drawable.yaponiya)
        imageList.add(R.drawable.qozoq)
        imageList.add(R.drawable.rossiya)
        imageList.add(R.drawable.usa)
    }

    fun bottomSheetDialog(jsonModels: JsonModels,position: Int) {
        val bottomSheetDialog = BottomSheetDialog(this@MainActivity)
        val dialogView = ItemButtomSheetDialogBinding.inflate(layoutInflater)
        bottomSheetDialog.setContentView(dialogView.root)
        dialogView.codeValyuta.text = jsonModels.code
        dialogView.valyutaCode.text = jsonModels.nbu_buy_price
        dialogView.imgValyuta.setImageResource(imageList[position])
        dialogView.inputNumber.addTextChangedListener {
            if (dialogView.inputNumber.text.isNullOrEmpty()){
                dialogView.valyutaCode.text = jsonModels.nbu_buy_price
            }else{
                dialogView.codeValyuta.text = jsonModels.code
                d =  dialogView.inputNumber.text.toString().toDouble() * jsonModels.nbu_buy_price.toString().toDouble()
                dialogView.valyutaCode.text = d.toString()
            }
        }

        dialogView.cancelButton.setOnClickListener {
            bottomSheetDialog.onBackPressed()
        }

        bottomSheetDialog.show()
    }

    override fun ViewClick(viewPagerModels: JsonModels, position: Int) {
        bottomSheetDialog(viewPagerModels,position)
    }

    override fun onClick(jsonModels: JsonModels, position: Int) {
        bottomSheetDialog(jsonModels,position)
    }
}