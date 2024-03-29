package com.benhurqs.sumup.photos.managers

import com.benhurqs.sumup.main.domains.entities.Photo
import io.reactivex.Observable

interface PhotoDataSource{
    fun getPhotoList(albumID: Int) : Observable<List<Photo>?>
}