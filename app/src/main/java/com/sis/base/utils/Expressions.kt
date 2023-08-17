package com.sis.base.utils

fun Int.oneStepChange(isAdd:Boolean) : Int{
    return if (isAdd){
        this+1
    }else{
        this-1
    }
}