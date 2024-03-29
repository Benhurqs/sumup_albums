package com.benhurqs.sumup.main.presentation.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.benhurqs.sumup.R
import com.benhurqs.sumup.commons.presentation.adapter.DefaultViewHolder
import com.benhurqs.sumup.injection.Injection
import com.benhurqs.sumup.main.domains.entities.Album
import com.benhurqs.sumup.main.domains.entities.Photo
import com.benhurqs.sumup.main.presentation.contracts.PhotoContract
import com.benhurqs.sumup.main.presentation.presenter.PhotoPresenter
import kotlinx.android.synthetic.main.album_content.view.*
import kotlinx.android.synthetic.main.album_error_content.view.*

class AlbumsAdapter (private val albumList: List<Album>): RecyclerView.Adapter<DefaultViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DefaultViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.album_content, parent, false))

    override fun getItemCount() = albumList.size

    override fun onBindViewHolder(holder: DefaultViewHolder, position: Int) {
        val view: View = holder.mView
        val album = albumList[position]

        val presenter = PhotoPresenter(object :
            PhotoContract.View {
            override fun showLoading() {
                view.album_progress.visibility = View.VISIBLE
            }

            override fun hideLoading() {
                view.album_progress.visibility = View.GONE
            }

            override fun showError() {
                view.album_error_content.visibility = View.VISIBLE
            }

            override fun hideError() {
                view.album_error_content.visibility = View.GONE
            }

            override fun loadPhotos(photoList: List<Photo>) {
                view.photo_list.layoutManager =
                    LinearLayoutManager(view.context, LinearLayoutManager.HORIZONTAL, false)
                view.photo_list.adapter =
                    PhotosAdapter(photoList)
            }

            override fun hideEmptyView() {
                view.album_empty_content.visibility = View.GONE
            }

            override fun showEmptyView() {
                view.album_empty_content.visibility = View.VISIBLE
            }

            override fun isAdded() = !(view.context as Activity).isFinishing
        }, Injection.providePhotoRepository(view.context))

        view.album_title.text = album.title
        presenter.callPhotoAPI(album.id)

        view.album_error_try_again.setOnClickListener {
            presenter.callPhotoAPI(album.id)
        }

    }
}