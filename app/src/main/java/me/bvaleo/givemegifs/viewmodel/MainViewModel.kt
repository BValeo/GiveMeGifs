package me.bvaleo.givemegifs.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableBoolean
import android.util.Log
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import me.bvaleo.givemegifs.model.*
import me.bvaleo.givemegifs.net.IGifService
import javax.inject.Inject

class MainViewModel @Inject constructor() : ViewModel() {

    init {
        Log.d("MainViewModel", "Constructor")
    }

    @Inject lateinit var mService: IGifService

    private var pagination: Int = 0
    private var paginationSearch: Int = 0

    private val disposable: CompositeDisposable = CompositeDisposable()

    private val trend: MutableLiveData<MutableList<ResponseGif>> = MutableLiveData()
    private val search: MutableLiveData<MutableList<ResponseGif>> = MutableLiveData()

    private val isLoading = ObservableBoolean(false)

    private val data: MutableList<ResponseGif> = mutableListOf()

    fun getTrending() : MutableLiveData<MutableList<ResponseGif>> = trend
    fun getSearching() : MutableLiveData<MutableList<ResponseGif>> = search

    private var curruntQuery: String = ""


    fun getGifs(query: String? = null) {
        query?.let {
            if (curruntQuery != query) {paginationSearch = 0; curruntQuery = query}
            getGifsByQuery(it)
            return
        }
        getTrendingGifs()
    }

    private fun getTrendingGifs() {
        if(isLoading.get())
            return

        isLoading.set(true)
        disposable.add(
                mService.getTrendingGif(paginationSearch)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            trend.value = it.data
                            paginationSearch += it.pagination.count
                            isLoading.set(false)
                        },{
                            Log.e("Error", "Loading gifs failed (trend)", it)
                        }))
    }

    private fun getGifsByQuery(query: String) {
        if(isLoading.get())
            return

        isLoading.set(true)
        disposable.add(
                mService.searchByQuery(query, pagination)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            search.value = it.data
                            isLoading.set(false)
                        },{
                            Log.e("Error", "Loading gifs failed (search)", it)
                        }))
    }

    fun onSaveData(_data: MutableList<ResponseGif>) = data.addAll(_data)

    fun onRestoreData() : MutableList<ResponseGif> {
        if(data.isEmpty())
            getTrendingGifs()
        return data
    }

    override fun onCleared() {
        super.onCleared()
        if(!disposable.isDisposed)
            disposable.dispose()
    }
}