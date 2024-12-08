package com.example.galaxymoment.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.galaxymoment.callback.IRepository
import com.example.galaxymoment.data.MediaItems
import com.example.galaxymoment.data.TimeLineType
import com.example.galaxymoment.repository.RepositoryImpl


class DetailViewModel : ViewModel() {

    private val _repo = MutableLiveData<IRepository>()

    private val _context = MutableLiveData<Context>()

    private val _listItemTimeLine = MutableLiveData<ArrayList<TimeLineType>>()
    val listItemTimeLine: LiveData<ArrayList<TimeLineType>> = _listItemTimeLine

    private val _listItemDetail = MutableLiveData<ArrayList<MediaItems>>()
    val listItemDetail: LiveData<ArrayList<MediaItems>> = _listItemDetail

    private val _currentPosPager = MutableLiveData<Int>()
    val currentPosPager: LiveData<Int> = _currentPosPager

    private val _isShowMoreInfo = MutableLiveData<Boolean>()
    val isShowMoreInfo: LiveData<Boolean> = _isShowMoreInfo

    fun setShowMoreInfo(isShow: Boolean) {
        _isShowMoreInfo.value = isShow
    }

    fun setCurrentPosPager(position: Int) {
        _currentPosPager.value = position
    }

    fun setContext(context: Context) {
        _context.value = context
    }
    fun getContext(): Context {
        return _context.value!!
    }

    fun getListItemDetail(): ArrayList<MediaItems> {
        if (_repo.value == null) {
            _repo.value = RepositoryImpl()
        }
        if (_listItemDetail.value == null) {
            _listItemDetail.value = _repo.value?.getListItemDetail(getContext()!!)
        }
        return _listItemDetail.value!!
    }
}