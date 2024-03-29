package com.benhurqs.sumup.main.presentation.presenter

import com.benhurqs.sumup.album.managers.AlbumRepository
import com.benhurqs.sumup.commons.data.APICallback
import com.benhurqs.sumup.main.domains.entities.Album
import com.benhurqs.sumup.main.presentation.contracts.AlbumContract
import com.benhurqs.sumup.main.presentation.contracts.MainView

class AlbumPresenter(private val albumView: AlbumContract.View, private val mainView: MainView, private val albumRepository: AlbumRepository): AlbumContract.Presenter,
    APICallback<List<Album>?> {

    private var lastUserId: Int? = null

    override fun callAlbumAPI(userID: Int?) {
        if(userID == null){
            onError()
            return
        }

        lastUserId = userID
        albumRepository.getAlbumList(userID, this)
    }

    override fun retryCallAlbumAPI() {
        callAlbumAPI(lastUserId)
    }

    override fun onStart() {
        if(mainView.isAdded()){
            mainView.showLoading()
            albumView.hideContent()
            albumView.hideEmptyView()
            mainView.hideError()
        }
    }

    override fun onError() {
        if(mainView.isAdded()){
            mainView.showError()
        }
    }

    override fun onFinish() {
        if(mainView.isAdded()){
            mainView.hideLoading()
        }
    }

    override fun onSuccess(albumList: List<Album>?) {
        if(mainView.isAdded()){
            if(albumList.isNullOrEmpty()){
                albumView.showEmptyView()
            }else{
                albumView.showContent()
                albumView.loadingAlbums(albumList)
            }


        }
    }
}