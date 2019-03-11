package com.qihe.kotlinncoroutines

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.qihe.kotlinncoroutines.callback.OnRecyclerItemClickListener
import com.qihe.kotlinncoroutines.callback.RecyclerItemTouchHelper
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item.view.*
import kotlinx.coroutines.*
import java.lang.Exception


/***
 *
 *
val job = GlobalScope.launch(Dispatchers.Main) {
delay(2000) // launch协程挂起2000
Log.e("tag", "-------launch------${Thread.currentThread().name}")

//两个async 并发启动
async(Dispatchers.IO) {
delay(5000)//async挂起5000
Log.e("tag", "-------async------${Thread.currentThread().name}")
}

try {
val await = async(Dispatchers.IO) {
delay(2000)//async挂起2000
Log.e("tag", "-------async2------${Thread.currentThread().name}")
delay(5000)//async挂起2000
"Hahahah"  // 返回结果
}
val result = await.await()//等待结果返回，会阻塞当前协程
Log.e("tag", "${result}     ====  ${Thread.currentThread().name}")
} catch (ex: Exception) {

} finally {

}

runBlocking(Dispatchers.IO) {
Log.e("tag", "-------runBlocking------${Thread.currentThread().name}")
}
}
Log.e("tag", "-------------${Thread.currentThread().name}")
 *
 *
 */
class MainActivity : AppCompatActivity() {
    private val datas = mutableListOf<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = MyAdapter(datas)
        recyclerView.adapter = adapter
        val itemTouchHelper = ItemTouchHelper(RecyclerItemTouchHelper(adapter))
        itemTouchHelper.attachToRecyclerView(recyclerView)
        recyclerView.addOnItemTouchListener(object : OnRecyclerItemClickListener(recyclerView) {
            override fun onItemClick(view: View, position: Int) {
//                Toast.makeText(this@MainActivity, "click item ${position}", Toast.LENGTH_SHORT).show()
            }

            override fun onItemLongClick(view: View, position: Int) {
//                Toast.makeText(this@MainActivity, "long click item ${position}", Toast.LENGTH_SHORT).show()
            }
        })


        for (index in 0..100) {
            datas.add("Item $index")
        }
    }

    inner class MyAdapter(val datas: MutableList<String>) : RecyclerView.Adapter<MyViewHolder>() {
        override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MyViewHolder {
            return MyViewHolder(LayoutInflater.from(p0.context).inflate(R.layout.item, p0, false))
        }

        override fun getItemCount(): Int = datas.size
        override fun getItemId(position: Int): Long {
            return super.getItemId(position)
        }

        override fun getItemViewType(position: Int): Int {
            return super.getItemViewType(position)
        }

        @SuppressLint("SetTextI18n")
        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            holder.itemView.mText.text = datas[position]
        }
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
