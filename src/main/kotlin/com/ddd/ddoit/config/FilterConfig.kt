package com.ddd.ddoit.config

import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter

@Configuration
class FilterConfig {

    /**
     * OSIV처리 안하면 오류 나는 케이스 존재
     */
    @Bean
    fun openEntityManagerInViewFilter(): FilterRegistrationBean<OpenEntityManagerInViewFilter>? {
        val filterFilterRegistrationBean = FilterRegistrationBean<OpenEntityManagerInViewFilter>()
        filterFilterRegistrationBean.filter = OpenEntityManagerInViewFilter()
        filterFilterRegistrationBean.order = -200
        return filterFilterRegistrationBean
    }
}