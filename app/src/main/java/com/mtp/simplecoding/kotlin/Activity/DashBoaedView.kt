package com.mtp.simplecoding.kotlin.Activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.mtp.simplecoding.R
import com.mtp.simplecoding.SharePrefUtil
import com.mtp.simplecoding.kotlin.fragment.DashBoaedViewFragment

class DashBoaedView : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dash_boaed_view_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, DashBoaedViewFragment.newInstance())
                    .commitNow()
        }

        println("mobile_number "+SharePrefUtil.getValue(this,"mobile_number"))
        println("firebase_key "+SharePrefUtil.getValue(this,"firebase_id"))
    }

}
