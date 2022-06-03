package com.ddd.ddoit.config

import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter

@Configuration
class FilterConfig {

    @Bean
    fun openEntityManagerInViewFilter(): FilterRegistrationBean<OpenEntityManagerInViewFilter>? {
        val filterFilterRegistrationBean = FilterRegistrationBean<OpenEntityManagerInViewFilter>()
        filterFilterRegistrationBean.filter = OpenEntityManagerInViewFilter()
        filterFilterRegistrationBean.order = -200
        return filterFilterRegistrationBean
    }
}