package com.mtp.simplecoding.kotlin.Activity

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.*
import com.google.firebase.database.*
import com.google.gson.Gson
import com.mtp.simplecoding.R
import com.mtp.simplecoding.RefranceName
import com.mtp.simplecoding.Utility
import com.mtp.simplecoding.Utility.mobileNUmber
import com.mtp.simplecoding.Utility.spinnerStatus
import com.mtp.simplecoding.kotlin.pojo.DashBoardPojo
import com.mtp.simplecoding.kotlin.pojo.DashBoardRequestPojo
import kotlinx.android.synthetic.main.activity_verify_otp.*
import kotlinx.android.synthetic.main.activity_work_manegment.*
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class WorkManegment : AppCompatActivity() {
    val dataSet: ArrayList<DashBoardPojo> = ArrayList()
    lateinit  var mFirebaseDatabase : DatabaseReference
    lateinit var mFirebaseInstance :FirebaseDatabase
    var userId:String="";

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_work_manegment)

        // get the references from layout file




       // textview_date!!.text = "--/--/----"

        btn_add.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                println("click btn add ")
                showDialog()
            }

        })
    }

    fun fireBaseData() {

        val progressDialog : ProgressDialog = Utility.getCustomProgressDialog1(this)

        mFirebaseInstance = FirebaseDatabase.getInstance()
        // get reference to 'users' node
        mFirebaseDatabase = mFirebaseInstance.getReference(RefranceName.workManegment)
        // store app title to 'app_title' node
        mFirebaseInstance.getReference("app_title").setValue("Realtime Database")
        // app_title change listener
        mFirebaseInstance.getReference("app_title").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Log.e(Utility.TAG, "ERROR")
            }
            override fun onDataChange(p0: DataSnapshot) {

                Log.e(Utility.TAG, "App title updated")
                //setDashBoardDataToFB(Gson().toJson(dataSet))
                getKeydataValue(RefranceName.workManegment, mobileNUmber,progressDialog)

            }
        })
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
                    Log.e(Utility.TAG, "User Registration key"+datas.getValue())
                    userId=datas.getKey().toString()
                    Log.e(Utility.TAG, "User key"+userId)
                    val post = datas.getValue(DashBoardRequestPojo::class.java)
                    Log.e(Utility.TAG, "pojo.."+post?.dashBoardPjo)
                    val dashboardpojoArray: JSONArray = JSONArray(post?.dashBoardPjo)
                    var i:Int = 0
                    var size:Int = dashboardpojoArray.length()
                    for (i in 0.. size-1) {
                        var json_objectdetail: JSONObject =dashboardpojoArray.getJSONObject(i)
                        dataSet.add(Gson().fromJson(json_objectdetail.toString(), DashBoardPojo::class.java))
                    }

                    setView()

                }
            }

        })

    }

    fun setView() {

       /* rv_dashboard_list?.layoutManager = LinearLayoutManager(context)
        rv_dashboard_list?.adapter = dashBoardAdapter(dataSet, context, this)*/


    }

    private fun showDialog() {
        val dialog = Dialog(this)
        dialog .requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog .setCancelable(false)
        dialog .setContentView(R.layout.layout_work_out_diolog)

       val Workout= arrayOf("is Workout","Yes","No")
        val diet= arrayOf("is Follow Diet","Yes","No")


        val tv_date = dialog .findViewById(R.id.tv_date) as TextView
        val ev_reason = dialog .findViewById(R.id.ev_reason) as EditText
        val sp_text_is_d = dialog .findViewById(R.id.sp_text_is_d) as TextView
        val sp_text_is_w = dialog .findViewById(R.id.sp_text_is_w) as TextView

        val sp_is_d = dialog .findViewById(R.id.sp_is_d) as Spinner
        val sp_is_w = dialog .findViewById(R.id.sp_is_w) as Spinner

        spinnerStatus(Workout,sp_is_w,sp_text_is_w,this)
        spinnerStatus(diet,sp_is_d,sp_text_is_d,this)
        val btn_submit = dialog .findViewById(R.id.btn_submit) as Button

        var cal = Calendar.getInstance()
        val dateSetListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(view: DatePicker, year: Int, monthOfYear: Int,
                                   dayOfMonth: Int) {
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                val myFormat = "MM/dd/yyyy" // mention the format you need
                val sdf = SimpleDateFormat(myFormat, Locale.US)
                tv_date!!.text = sdf.format(cal.getTime())
            }
        }
        tv_date!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                DatePickerDialog(this@WorkManegment,
                        dateSetListener,
                        // set DatePickerDialog to point to today's date when it loads up
                        cal.get(Calendar.YEAR),
                        cal.get(Calendar.MONTH),
                        cal.get(Calendar.DAY_OF_MONTH)).show()
            }

        })

        btn_submit.setOnClickListener {
            Log.e(Utility.TAG, "W date"+tv_date.text.toString()
            +"ev_reason"+ev_reason.text.toString()
            +"sp_text_is_d"+sp_text_is_d.text.toString()
            +"sp_text_is_w"+sp_text_is_w.text.toString())
            dialog .dismiss()
        }

        dialog.show()
    }
}
