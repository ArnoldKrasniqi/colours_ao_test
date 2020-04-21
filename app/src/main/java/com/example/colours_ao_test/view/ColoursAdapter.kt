package com.example.colours_ao_test.view

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.colours_ao_test.R
import com.example.colours_ao_test.com.ColourEntity
import kotlinx.android.synthetic.main.recycler_view_card.view.*

class ColoursAdapter(
    private val  listOfColours : List<ColourEntity>
) : RecyclerView.Adapter<ColoursAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_card,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = listOfColours.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.tv_colourPatch.setBackgroundColor(Color.parseColor(listOfColours[position].hexa_name))
        holder.itemView.tv_hexaDecimal.text = listOfColours[position].hexa_name
        holder.itemView.tv_name.text = listOfColours[position].colour_name
    }
}