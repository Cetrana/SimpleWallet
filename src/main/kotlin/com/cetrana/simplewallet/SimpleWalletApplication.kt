package com.cetrana.simplewallet

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SimpleWalletApplication

fun main(args: Array<String>) {
    runApplication<SimpleWalletApplication>(*args)
}
