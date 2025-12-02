package com.example.birdtrail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class BirdAdapter(private val birdDataList: ArrayList<BirdData>) : RecyclerView.Adapter<BirdAdapter.MyViewHolder>() {

    private var listener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recycler_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = birdDataList[position]

        // Load image using Picasso
        Picasso.get()
            .load(currentItem.birdImage)
            .placeholder(R.drawable.new_camera_icon) // Placeholder image while loading
            .error(R.drawable.new_camera_icon) // Error image if loading fails
            .into(holder.birdImageView)

        holder.birdName.text = currentItem.birdName ?: "No Name"
        holder.birdLocation.text = currentItem.birdLocation ?: "No Location"
        holder.birdDescription.text = currentItem.birdDescription ?: "No Description"

        holder.itemView.setOnClickListener {
            listener?.onItemClick(position)
        }
    }

    override fun getItemCount(): Int {
        return birdDataList.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val birdImageView: ImageView = itemView.findViewById(R.id.birdImage)
        val birdName: TextView = itemView.findViewById(R.id.tvBirdName)
        val birdLocation: TextView = itemView.findViewById(R.id.tvBirdLocation)
        val birdDescription: TextView = itemView.findViewById(R.id.tvBirdDescription)
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }
}
//Bibliography
// Ali, M. (2018, jul 16). Google Maps api key Android Studio. Retrieved from Youtube:
// https://www.youtube.com/watch?v=6fVhmtzwvfk&amp;list=PLxefhmF0pcPlGUW8tyyOJ8-
// uF7Nk2VpSj&amp;index=2
// Ali, M. (2018, sep 18). youtube. Retrieved from Android Nearby Places Tutorial 06 - Making 3 classes
// - Google Maps Nearby Places Tutorial:
// https://www.youtube.com/watch?v=0QzKquJ4j8Y&amp;list=PLxefhmF0pcPlGUW8tyyOJ8-
// uF7Nk2VpSj&amp;index=6
// freecodecamp.org. (2021, jan 26). Android Studio Tutorial - Build a GPS App. Retrieved from
// Youtube: https://www.youtube.com/watch?v=_xUcYfbtfsI
// Jansen Van Rensburg, A. (n.d.). app.box.com. Retrieved september 9, 2024, from open source
// Example:
// https://app.box.com/s/yhxrl0a10z9guas3g6em4akt3t0lfud9