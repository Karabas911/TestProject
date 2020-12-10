package com.example.testproject

import android.content.Context

class ResourceComparer {

    fun compareResAndValue(c: Context, resId : Int, value : String): Boolean{
        return c.getString(resId)==value
    }

}