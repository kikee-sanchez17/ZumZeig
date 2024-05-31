package adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.zumzeig.R

// This class is responsible for adapting a list of image URLs into a RecyclerView
class ImageSliderAdapter(private val context: Context, private val imageUrls: List<String>) :
    RecyclerView.Adapter<ImageSliderAdapter.ImageViewHolder>() {

    // This function creates a new ViewHolder by inflating the layout file 'item_image_slider'
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_image_slider, parent, false)
        return ImageViewHolder(view)
    }

    // This function is called to bind data to the views inside the ViewHolder
    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val imageUrl = imageUrls[position]
        // Using Glide library to load the image from the URL into the ImageView
        Glide.with(context).load(imageUrl).into(holder.imageView)
    }

    // This function returns the size of the dataset, which is the number of image URLs
    override fun getItemCount(): Int {
        return imageUrls.size
    }

    // ViewHolder class to hold references to the views in the item layout
    class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
    }
}

