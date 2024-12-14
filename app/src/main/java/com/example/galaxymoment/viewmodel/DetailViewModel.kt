package com.example.galaxymoment.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.galaxymoment.callback.IRepository
import com.example.galaxymoment.data.MediaItems
import com.example.galaxymoment.data.TimeLineType
import com.example.galaxymoment.repository.RepositoryImpl
import java.util.TreeMap


class DetailViewModel : ViewModel() {

    private val _repo = MutableLiveData<IRepository>()

    private val _context = MutableLiveData<Context>()

    private val _listItemTimeLine = MutableLiveData<ArrayList<TimeLineType>>()
    val listItemTimeLine: LiveData<ArrayList<TimeLineType>> = _listItemTimeLine

    private val _listItemDetail = MutableLiveData<ArrayList<MediaItems>>()
    val listItemDetail: LiveData<ArrayList<MediaItems>> = _listItemDetail

    private val _currentPosFilmStrip = MutableLiveData<Int>()
    val currentPosFilmStrip: LiveData<Int> = _currentPosFilmStrip

    private val _currentPosPager = MutableLiveData<Int>()
    val currentPosPager: LiveData<Int> = _currentPosPager

    private val _isShowMoreInfo = MutableLiveData<Boolean>()
    val isShowMoreInfo: LiveData<Boolean> = _isShowMoreInfo

    private val _filmStripScrolling = MutableLiveData<Boolean>()
    val isFilmStripScrolling: LiveData<Boolean> = _filmStripScrolling

    private val _treeMapTag = MutableLiveData<TreeMap<String, ArrayList<MediaItems>>>()
    val treeMapTag: LiveData<TreeMap<String, ArrayList<MediaItems>>> = _treeMapTag

    private val _listItemByTags = MutableLiveData<ArrayList<TimeLineType>>()
    val listItemByTags: LiveData<ArrayList<TimeLineType>> = _listItemByTags

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

    fun getListItemTimeLine() : ArrayList<TimeLineType>{
        if (_repo.value == null) {
            _repo.value = RepositoryImpl()
        }
        if (_listItemTimeLine.value == null) {
            _listItemTimeLine.value = _repo.value?.getListItemTimeLine(getContext()!!)
        }
        return _listItemTimeLine.value!!
    }

    fun getListItemDetail(): ArrayList<MediaItems> {
        if (_repo.value == null) {
            _repo.value = RepositoryImpl()
        }
        if (_listItemDetail.value == null) {
            _listItemDetail.value = _repo.value?.getListItemDetail(getContext())
        }
        return _listItemDetail.value!!
    }

    fun getTreeMapTag() : TreeMap<String, ArrayList<MediaItems>> {
        if (_repo.value == null) {
            _repo.value = RepositoryImpl()
        }
        if(_treeMapTag.value == null) {
            _treeMapTag.value = _repo.value?.getTreeMapTag()
        }
        return _treeMapTag.value!!
    }

    fun getListItemByTags(tags: ArrayList<String>) : ArrayList<TimeLineType> {
        if (_repo.value == null) {
            _repo.value = RepositoryImpl()
        }
        if(tags.isEmpty()){
            return getListItemTimeLine()
        }
        _listItemByTags.value = _repo.value?.getListItemByTags(tags)
        return _listItemByTags.value!!
    }

    fun setFilmStripScrolling(isScrolling: Boolean) {
        _filmStripScrolling.value = isScrolling
    }

    fun setCurrentPosFilmStrip(pos: Int) {
        _currentPosFilmStrip.value = pos
    }
}