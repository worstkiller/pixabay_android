package com.vikas.pixabayandroid.ui.home

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.vikas.pixabayandroid.persistence.PixabayModel

/**
 * Adapter for managing the data and ui and showing it efficiently based on @see[DiffUtil]
 */
class PixaPagedAdapter : PagingDataAdapter<PixabayModel, PixaViewHolder>(REPO_COMPARATOR) {

    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<PixabayModel>() {
            override fun areItemsTheSame(oldItem: PixabayModel, newItem: PixabayModel): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: PixabayModel, newItem: PixabayModel): Boolean =
                oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: PixaViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PixaViewHolder {
        return PixaViewHolder.getInstance(parent)
    }

}