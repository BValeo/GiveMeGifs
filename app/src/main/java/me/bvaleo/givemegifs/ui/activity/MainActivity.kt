package me.bvaleo.givemegifs.ui.activity

import android.os.Bundle
import android.view.View
import dagger.android.support.DaggerAppCompatActivity
import me.bvaleo.givemegifs.R
import me.bvaleo.givemegifs.ui.fragment.TrendingFragment

class MainActivity : DaggerAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(savedInstanceState == null) {
            with(supportFragmentManager.beginTransaction()) {
                add(R.id.container, TrendingFragment(), "TrendingFragment")
                addToBackStack("TrendingFragment")
                commit()
            }
        }
    }

}
