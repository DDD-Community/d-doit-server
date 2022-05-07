package com.ddd.ddoit

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class DDoitApplication

fun main(args: Array<String>) {
	runApplication<DDoitApplication>(*args)
}
