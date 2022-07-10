package com.ddd.ddoit.dto

class ErrorResponse<T> (val code: Int,
                        val name: String,
                        val msg: T
){
    val trace: String? = null

}