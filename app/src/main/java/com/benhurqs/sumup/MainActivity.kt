package com.benhurqs.sumup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.benhurqs.sumup.album.managers.AlbumRepository
import com.benhurqs.sumup.commons.data.APICallback
import com.benhurqs.sumup.photos.domains.entities.Album
import com.benhurqs.sumup.photos.domains.entities.Photo
import com.benhurqs.sumup.photos.managers.PhotoRepository
import com.benhurqs.sumup.splash.domains.entities.User
import com.benhurqs.sumup.user.clients.local.UserLocalDataSource
import com.benhurqs.sumup.user.clients.remote.UserRemoteDataSource
import com.benhurqs.sumup.user.managers.UserRepository

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        UserRepository.getInstance(UserRemoteDataSource.getInstance(), UserLocalDataSource.getInstance(this))
            .getUserList(object: APICallback<List<User>, String>{
            override fun onStart() {
                Log.e("start","comecou")
            }

            override fun onError(error: String) {
                Log.e("error","error -> " + error)
            }

            override fun onFinish() {
                Log.e("finish","finish")
            }

            override fun onSuccess(response: List<User>) {
                Log.e("success","chegou - " + response.get(0).name)
            }
        })

//        PhotoRepository.getInstance().getPhotoList(1, object: APICallback<List<Photo>, String>{
//            override fun onStart() {
//                Log.e("start","comecou")
//            }
//
//            override fun onError(error: String) {
//                Log.e("error","error -> " + error)
//            }
//
//            override fun onFinish() {
//                Log.e("finish","finish")
//            }
//
//            override fun onSuccess(response: List<Photo>) {
//                Log.e("success","photo - " + response.get(0).url)
//            }
//        })
//
//        AlbumRepository.getInstance().getAlbumList(1, object: APICallback<List<Album>, String>{
//            override fun onStart() {
//                Log.e("start","comecou")
//            }
//
//            override fun onError(error: String) {
//                Log.e("error","error -> " + error)
//            }
//
//            override fun onFinish() {
//                Log.e("finish","finish")
//            }
//
//            override fun onSuccess(response: List<Album>) {
//                Log.e("success","Album - " + response.get(0).title)
//            }
//        })


    }
}