package com.mtp.simplecoding.kotlin.pojo


import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties


@IgnoreExtraProperties
data class WorkManegmentRequistPojo(
        var mobileNumber: String?="",
        var workManegmentPojo: String?=""
)
{

    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
                "mobileNumber" to mobileNumber,
                "dashBoardPjo" to workManegmentPojo

        )
    }
}

