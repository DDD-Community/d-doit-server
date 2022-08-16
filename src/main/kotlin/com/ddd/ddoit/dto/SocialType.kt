package com.ddd.ddoit.dto

enum class SocialType(val code :String, val social :String){
    KAKAO("kakao", "카카오"), GOOGLE("google", "구글");

    companion object {
        fun byCode(code: String): SocialType{
            for (type in SocialType.values()){
                if (type.code == code)
                    return type
            }
            return KAKAO
        }
    }

}