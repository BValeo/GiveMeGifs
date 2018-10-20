package me.bvaleo.givemegifs.ui.fragment

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.content.res.Configuration
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_search.*
import me.bvaleo.givemegifs.R
import me.bvaleo.givemegifs.model.ResponseGif
import me.bvaleo.givemegifs.ui.adapter.GifAdapter
import me.bvaleo.givemegifs.util.getViewModel
import me.bvaleo.givemegifs.util.toast
import me.bvaleo.givemegifs.viewmodel.MainViewModel
import javax.inject.Inject

class SearchFragment : DaggerFragment() {

    @Inject
    lateinit var mFactory: ViewModelProvider.Factory

    private lateinit var mVModel: MainViewModel
    private lateinit var mLData: MutableLiveData<MutableList<ResponseGif>>
    private lateinit var mLayoutManager: LinearLayoutManager
    private lateinit var mAdapter: GifAdapter

    companion object Const {
        const val list_tag = "LIST_TAG"
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mVModel = getViewModel(activity!!, mFactory)
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        mLData = mVModel.getSearching()
        mLData.observe(this, Observer { it?.let { mAdapter.addData(it) } })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        mVModel.onSaveData(mAdapter.saveData())
        outState.putParcelable(list_tag, mLayoutManager.onSaveInstanceState())
        super.onSaveInstanceState(outState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        savedInstanceState?.let {
            mLayoutManager.onRestoreInstanceState(it.getParcelable(list_tag))
        }
    }

    private fun initView() {
        mLayoutManager = if (activity?.resources?.configuration?.orientation == Configuration.ORIENTATION_PORTRAIT)
            LinearLayoutManager(context)
        else
            GridLayoutManager(context, 2)
        mAdapter = GifAdapter(mutableListOf())

        rv_responseGif.layoutManager = mLayoutManager
        rv_responseGif.adapter = mAdapter
        rv_responseGif.setOnScrollChangeListener{ view, _, _, _, _ ->
            val rv = view as RecyclerView
            val layoutManager = rv.layoutManager as LinearLayoutManager
            val lastVisibleElement = layoutManager.findLastVisibleItemPosition()

            if(lastVisibleElement >= rv.adapter.itemCount - 2){
                val query = et_search.text.toString().trim()
                mVModel.getGifs(query)
            }
        }

        btn_search.setOnClickListener{
            val query = et_search.text.toString().trim()
            if(query.isNotEmpty()) {
                mAdapter.clearData()
                mVModel.getGifs(query)
            }
            else
                toast("Введите запрос")
        }
    }


}