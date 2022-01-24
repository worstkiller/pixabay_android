package com.vikas.pixabayandroid.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.rxjava2.cachedIn
import com.vikas.pixabayandroid.persistence.PixabayModel
import com.vikas.pixabayandroid.repo.PixabayRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
@ExperimentalPagingApi
class PixabayImageViewModel @Inject constructor(val repository: PixabayRepository) : ViewModel() {

    private val disposable = CompositeDisposable()
    private var isError = MutableLiveData(false)

    fun getImages(data: (PagingData<PixabayModel>) -> Unit) {
        repository.getImagesObservable()
            .cachedIn(viewModelScope)
            .distinctUntilChanged()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io()).subscribe(
                { pagedData ->
                    data.invoke(pagedData)
                }, {
                    //handle error
                    isError.value = true
                })
            .also {
                disposable.addAll(it)
            }
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

}