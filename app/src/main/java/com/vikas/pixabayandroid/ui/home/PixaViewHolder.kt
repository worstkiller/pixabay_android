package com.vikas.pixabayandroid.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.vikas.pixabayandroid.R
import com.vikas.pixabayandroid.databinding.ItemImageGridViewBinding
import com.vikas.pixabayandroid.persistence.PixabayModel

/**
 * viewholder class for setting up ui at atomic level using data binding
 */
class PixaViewHolder(private val itemViewBinding: ItemImageGridViewBinding) :
    RecyclerView.ViewHolder(itemViewBinding.root) {

    companion object {

        fun getInstance(viewGroup: ViewGroup): PixaViewHolder {
            val inflater = LayoutInflater.from(viewGroup.context)
            val binding = ItemImageGridViewBinding.inflate(inflater, viewGroup, false)
            return PixaViewHolder(binding)
        }

    }

    fun bind(pixabayModel: PixabayModel?) {
        pixabayModel?.run {
            Picasso.get()
                .load(previewURL)
                .placeholder(R.drawable.ic_image)
                .into(itemViewBinding.ivImage)
            itemViewBinding.tvViews.text = views.toString()
            itemViewBinding.tvUser.text = user
        }
    }

}