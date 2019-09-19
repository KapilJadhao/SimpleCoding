package com.mtp.simplecoding.kotlin.fragment

import android.app.ProgressDialog
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.database.*
import com.google.gson.Gson
import com.mtp.simplecoding.FirebaseDataBase.PaymentListActivity
import com.mtp.simplecoding.R
import com.mtp.simplecoding.RefranceName.BacgroundImage
import com.mtp.simplecoding.RefranceName.dashBoardRef
import com.mtp.simplecoding.Utility
import com.mtp.simplecoding.Utility.TAG
import com.mtp.simplecoding.Utility.mobileNUmber
import com.mtp.simplecoding.kotlin.Activity.Profile
import com.mtp.simplecoding.kotlin.Activity.WorkManegment
import com.mtp.simplecoding.kotlin.Adapter.dashBoardAdapter
import com.mtp.simplecoding.kotlin.pojo.DashBoardPojo
import com.mtp.simplecoding.kotlin.pojo.DashBoardRequestPojo
import com.mtp.simplecoding.kotlin.ui.dashboaedview.DashBoaedViewViewModel
import kotlinx.android.synthetic.main.dash_boaed_view_fragment.*
import org.json.JSONArray
import org.json.JSONObject


class DashBoaedViewFragment : Fragment(), dashBoardAdapter.ItemListener {

    val dataSet: ArrayList<DashBoardPojo> = ArrayList()
    lateinit  var mFirebaseDatabase : DatabaseReference
    lateinit var mFirebaseInstance :FirebaseDatabase
    var userId:String="";


    companion object {
        fun newInstance() = DashBoaedViewFragment()
    }

    private lateinit var viewModel: DashBoaedViewViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        val view = inflater?.inflate(R.layout.dash_boaed_view_fragment,
                container, false)


        fireBaseData()
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(DashBoaedViewViewModel::class.java)
        // TODO: Use the ViewModel
    }

    fun setView() {

        rv_dashboard_list?.layoutManager = LinearLayoutManager(context)
        rv_dashboard_list?.adapter = dashBoardAdapter(dataSet, context, this)


    }

    //check and set data of user
    fun getKeydataValue(refrance: String, mobileNumber: String,progressDialog : ProgressDialog) {
        // Write a message to the database
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference(refrance)
        myRef.orderByChild("mobileNumber").equalTo(mobileNumber).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

                if (progressDialog != null) {
                    progressDialog.dismiss()
                }




            }

            override fun onDataChange(p0: DataSnapshot) {
                if (progressDialog != null) {
                    progressDialog.dismiss()
                }

                //Log.e(TAG, "User Registration key"+p0)
                for (datas:DataSnapshot in p0.getChildren()) {
                    dataSet.clear()
                    Log.e(TAG, "User Registration key"+datas.getValue())
                    userId=datas.getKey().toString()
                    Log.e(TAG, "User key"+userId)
                    val post = datas.getValue(DashBoardRequestPojo::class.java)
                    Log.e(TAG, "pojo.."+post?.dashBoardPjo)
                    val dashboardpojoArray: JSONArray=JSONArray(post?.dashBoardPjo)
                    var i:Int = 0
                    var size:Int = dashboardpojoArray.length()
                    for (i in 0.. size-1) {
                        var json_objectdetail:JSONObject=dashboardpojoArray.getJSONObject(i)
                        dataSet.add(Gson().fromJson(json_objectdetail.toString(), DashBoardPojo::class.java))
                    }

                    setView()

                }
            }

        })

    }

    fun setDashBoardDataToFB(ddashBoardPojo: String) {

        println("value to send: " + Gson().toJson(DashBoardRequestPojo(mobileNUmber, ddashBoardPojo)))

        if (TextUtils.isEmpty(userId)) {
            userId = mFirebaseDatabase.push().key.toString()
        }

        mFirebaseDatabase.child(userId).setValue(DashBoardRequestPojo(mobileNUmber, ddashBoardPojo))

    }

    override fun onItemClick(item: DashBoardPojo) {
        println("You select name: " + item.titleName)
        BacgroundImage=item.imageName;
        if(item.titleName.equals("Expense")){

            val intent = Intent(context, PaymentListActivity::class.java)
            context?.startActivity(intent)

        }else if(item.titleName.equals("Stories")){
            val intent = Intent(context, WorkManegment::class.java)
            context?.startActivity(intent)

        }else if(item.titleName.equals("Contacts")){

        }else if(item.titleName.equals("Profile")){
            val intent = Intent(context, Profile::class.java)
            context?.startActivity(intent)

        }else if(item.titleName.equals("Setting")){

        }
    }


    fun fireBaseData() {

        val progressDialog : ProgressDialog=Utility.getCustomProgressDialog1(activity)

         mFirebaseInstance = FirebaseDatabase.getInstance()
        // get reference to 'users' node
         mFirebaseDatabase = mFirebaseInstance.getReference(dashBoardRef)
        // store app title to 'app_title' node
        mFirebaseInstance.getReference("app_title").setValue("Realtime Database")
        // app_title change listener
        mFirebaseInstance.getReference("app_title").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Log.e(TAG, "ERROR")
            }
            override fun onDataChange(p0: DataSnapshot) {

                Log.e(TAG, "App title updated")
                //setDashBoardDataToFB(Gson().toJson(dataSet))
                getKeydataValue(dashBoardRef, "0000000001",progressDialog)

            }
        })
    }

}