package me.okko.agatha.manager.service

import me.okko.agatha.manager.repository.HelloRepository

interface HelloService {
    fun sayHelloWorld(): String
}

class HelloServiceImpl(private val helloRepository: HelloRepository) : HelloService {
    override fun sayHelloWorld(): String {
        return helloRepository.getHello()
    }
}