package com.example.galaxymoment.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.galaxymoment.callback.IRepository
import com.example.galaxymoment.data.MediaItems


class TimelineViewModel : ViewModel() {

    private val _repo = MutableLiveData<IRepository>()

    private val _context = MutableLiveData<Context>()

    private val _listItemDetail = MutableLiveData<MutableList<MediaItems>>()
    val listItemDetail: LiveData<MutableList<MediaItems>> = _listItemDetail

    private val _currentPosPager = MutableLiveData<Int>()
    val currentPosPager: LiveData<Int> = _currentPosPager

    fun setRepo(repo: IRepository) {
        _repo.value = repo
    }

    fun setContext(context: Context) {
        _context.value = context
    }

    fun setCurrentPosPager(position: Int) {
        _currentPosPager.value = position
    }

    fun getListItemDetail(context: Context) {
        _listItemDetail.value = _repo.value?.getListItemDetail(context)
    }

    fun getListItemTimeLine(context: Context) {
        _repo.value?.getListItemTimeLine(context)
    }

}