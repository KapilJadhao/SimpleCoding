package com.mtp.simplecoding.kotlin.Adapter

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition

import com.mtp.simplecoding.R
import com.mtp.simplecoding.kotlin.pojo.DashBoardPojo
import kotlinx.android.synthetic.main.dashboard_adapter.view.*


class dashBoardAdapter(val items: ArrayList<DashBoardPojo>, val context: Context?) : RecyclerView.Adapter<ViewHolderDashBoard>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolderDashBoard {
        return ViewHolderDashBoard(LayoutInflater.from(context).inflate(R.layout.dashboard_adapter, p0, false))
    }

    // Gets the number of animals in the list
    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(p0: ViewHolderDashBoard, p1: Int) {
        p0?.tvAnimalType?.text = items[p1].titleName
        p0?.tvCount?.text=items[p1].count

        if (context != null) {
            Glide.with(context)
                    .asBitmap()
                    .load("https://firebasestorage.googleapis.com/v0/b/simplecoding-767e7.appspot.com/o/images%2Ftexture_image_2.jpg?alt=media&token=8b465907-7279-45dd-945e-8827d66ffcbc")
                    .into(object : CustomTarget<Bitmap>(){
                        override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                            p0?.ivBackground.setImageBitmap(resource)
                        }

                        override fun onLoadCleared(placeholder: Drawable?) {
                            // this is called when imageView is cleared on lifecycle call or for
                            // some other reason.
                            // if you are referencing the bitmap somewhere else too other than this imageView
                            // clear it here as you can no longer have the bitmap
                        }
                    })
        }

    }



}

class ViewHolderDashBoard (view: View) : RecyclerView.ViewHolder(view) {
    // Holds the TextView that will add each animal to
    val tvAnimalType = view.text_item
    val tvCount=view.tv_hmmer
    val ivBackground=view.imageView
}
