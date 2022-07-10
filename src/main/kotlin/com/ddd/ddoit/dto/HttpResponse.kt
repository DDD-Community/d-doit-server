package com.ddd.ddoit.dto

class HttpResponse<T> (val code: Int,
                       val msg: String,
                       val body: T
) {

}