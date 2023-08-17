package com.sis.base.utils

import android.content.res.AssetManager
import java.io.InputStream

class Utils {

    companion object{
        fun readFromAsset(assets: AssetManager,fileName:String): String? {
            var json: String? = null
            try {
                val inputStream: InputStream = assets.open(fileName)
                json = inputStream.bufferedReader().use{it.readText()}
            } catch (ex: Exception) {
                ex.printStackTrace()
                return null
            }
            return json
        }
    }

}