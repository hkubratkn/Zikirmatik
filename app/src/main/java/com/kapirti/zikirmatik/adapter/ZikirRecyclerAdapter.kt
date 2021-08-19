package com.kapirti.zikirmatik.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.kapirti.zikirmatik.R
import com.kapirti.zikirmatik.databinding.RecyclerRowBinding
import com.kapirti.zikirmatik.model.ZikirModel

class ZikirRecyclerAdapter(val zikirList:ArrayList<ZikirModel>):RecyclerView.Adapter<ZikirRecyclerAdapter.ZikirViewHolder>() {
    class ZikirViewHolder(var view: RecyclerRowBinding):RecyclerView.ViewHolder(view.root){}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ZikirViewHolder {
        val inflater=LayoutInflater.from(parent.context)
        val view=DataBindingUtil.inflate<RecyclerRowBinding>(inflater,R.layout.recycler_row,parent,false)
        return ZikirViewHolder(view)
    }
    override fun getItemCount(): Int {
        return zikirList.size
    }
    override fun onBindViewHolder(holder: ZikirViewHolder, position: Int) {
        holder.view.zikir=zikirList[position]
    }

    fun loopZikir(newZikirList:List<ZikirModel>){
        zikirList.clear()
        zikirList.addAll(newZikirList)
        notifyDataSetChanged()
    }

}