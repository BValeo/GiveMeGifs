package me.bvaleo.givemegifs.ui.fragment

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.content.res.Configuration
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.support.DaggerFragment
import me.bvaleo.givemegifs.R
import me.bvaleo.givemegifs.databinding.FragmentTrendingBinding
import me.bvaleo.givemegifs.model.ResponseGif
import me.bvaleo.givemegifs.ui.adapter.GifAdapter
import me.bvaleo.givemegifs.ui.adapter.RecyclerTouchListener
import me.bvaleo.givemegifs.util.Constants
import me.bvaleo.givemegifs.util.getViewModel
import me.bvaleo.givemegifs.util.toast
import me.bvaleo.givemegifs.viewmodel.MainViewModel
import javax.inject.Inject


class TrendingFragment : DaggerFragment(), RecyclerTouchListener.ClickListener {

    @Inject lateinit var mFactory: ViewModelProvider.Factory

    private lateinit var mBind: FragmentTrendingBinding
    private lateinit var mVModel: MainViewModel
    private lateinit var mLData: MutableLiveData<MutableList<ResponseGif>>
    private lateinit var mLayoutManager: LinearLayoutManager
    private lateinit var mAdapter: GifAdapter



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBind = DataBindingUtil.inflate(inflater, R.layout.fragment_trending, container, false)
        mVModel = getViewModel(activity!!, mFactory)
        initRV()

        mLData = mVModel.getTrending()
        mLData.observe(this, Observer<MutableList<ResponseGif>> { it?.let { mAdapter.addData(it)} })

        mBind.vm = mVModel
        mBind.executePendingBindings()

        return mBind.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        mVModel.onSaveData(mAdapter.saveData())
        outState.putParcelable(Constants.LIST_STATE, mLayoutManager.onSaveInstanceState())
        super.onSaveInstanceState(outState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        savedInstanceState?.let {
            mLayoutManager.onRestoreInstanceState(it.getParcelable(Constants.LIST_STATE))
        }
    }

    private fun initRV() {

        mLayoutManager = if (activity?.resources?.configuration?.orientation == Configuration.ORIENTATION_PORTRAIT)
            LinearLayoutManager(context)
        else
            GridLayoutManager(context, 2)
        mAdapter = GifAdapter(mVModel.onRestoreData())

        with(mBind.rvGifs) {
            adapter = mAdapter
            layoutManager = mLayoutManager

            addOnItemTouchListener(RecyclerTouchListener(context, this, this@TrendingFragment))
            setOnScrollChangeListener{ view, _, _, _, _ ->
                val rv = view as RecyclerView
                val layoutManager = rv.layoutManager as LinearLayoutManager
                val lastVisibleElement = layoutManager.findLastVisibleItemPosition()

                if(lastVisibleElement >= rv.adapter.itemCount - 2){
                    mVModel.getGifs()
                }
            }
        }
    }

    override fun onClick(view: View, position: Int) {
        val gif = mAdapter.getItem(position)
        toast(gif.images.info.url)
    }

    override fun onLongClick(view: View?, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}