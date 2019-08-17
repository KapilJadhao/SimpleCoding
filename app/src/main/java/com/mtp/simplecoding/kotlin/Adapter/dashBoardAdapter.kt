package com.mtp.simplecoding.kotlin.Adapter

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.mtp.simplecoding.FirebaseDataBase.paymentPojo

import com.mtp.simplecoding.R
import com.mtp.simplecoding.kotlin.pojo.DashBoardPojo
import kotlinx.android.synthetic.main.dashboard_adapter.view.*


class dashBoardAdapter(val items: ArrayList<DashBoardPojo>, val context: Context?,val mListener: ItemListener) : RecyclerView.Adapter<ViewHolderDashBoard>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolderDashBoard {
        return ViewHolderDashBoard(LayoutInflater.from(context).inflate(R.layout.dashboard_adapter, p0, false))
    }

    // Gets the number of animals in the list
    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(p0: ViewHolderDashBoard, p1: Int) {
        val dashpojo: DashBoardPojo
        dashpojo=items[p1]
        p0?.tvAnimalType?.text = items[p1].titleName
        p0?.tvCount?.text=items[p1].count

        if (context != null) {
            Glide.with(context)
                    .asBitmap()
                    .load(items[p1].imageName)
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

        p0?.ivBackground.setOnClickListener {
           /* val i = Intent(this@MainActivity, SecondActivity::class.java)
            startActivity(i)
            finish()*/
            if (mListener != null) {
                mListener.onItemClick(dashpojo)
            }
            //Toast.makeText(context, "You clicked me.", Toast.LENGTH_SHORT).show()
        }

    }


    public interface ItemListener {
        fun onItemClick(item: DashBoardPojo)
    }


}

class ViewHolderDashBoard (view: View) : RecyclerView.ViewHolder(view) {
    // Holds the TextView that will add each animal to
    val tvAnimalType = view.text_item
    val tvCount=view.tv_hmmer
    val ivBackground=view.imageView
}
