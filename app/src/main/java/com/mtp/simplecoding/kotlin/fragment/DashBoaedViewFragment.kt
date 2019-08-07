package com.mtp.simplecoding.kotlin.fragment

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.mtp.simplecoding.R

import com.mtp.simplecoding.kotlin.Adapter.dashBoardAdapter
import com.mtp.simplecoding.kotlin.pojo.DashBoardPojo
import com.mtp.simplecoding.kotlin.ui.dashboaedview.DashBoaedViewViewModel


class DashBoaedViewFragment : Fragment() {

    val dataSet: ArrayList<DashBoardPojo> = ArrayList()

    companion object {
        fun newInstance() = DashBoaedViewFragment()
    }

    private lateinit var viewModel: DashBoaedViewViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        val view = inflater?.inflate(R.layout.dash_boaed_view_fragment,
                container, false)

        /*val seekBar: SeekBar? = view?.findViewById(R.id.seekBar1)
        val button: Button? = view?.findViewById(R.id.button1)

        seekBar?.setOnSeekBarChangeListener(this)

        button?.setOnClickListener { v: View -> buttonClicked(v)}*/
        addAnimals()
        val rv_dashboard_list:RecyclerView?=view?.findViewById(R.id.rv_dashboard_list)
        // Creates a vertical Layout Manager
        rv_dashboard_list?.layoutManager = LinearLayoutManager(context)
        rv_dashboard_list?.adapter = dashBoardAdapter(dataSet,context)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(DashBoaedViewViewModel::class.java)
        // TODO: Use the ViewModel
    }

    fun addAnimals() {
        dataSet.add(DashBoardPojo("Expense","","2"))
        dataSet.add(DashBoardPojo("Stories","","3"))
        dataSet.add(DashBoardPojo("Contacts","","4"))
        dataSet.add(DashBoardPojo("Profile","","5"))

    }

    //check and set data of user
    fun getKeydataValue(refrance :String, mobileNumber : String){
      // Write a message to the database
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference(refrance)
        myRef.orderByChild("mobileNumber").equalTo(mobileNumber).addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        })

    }

}
