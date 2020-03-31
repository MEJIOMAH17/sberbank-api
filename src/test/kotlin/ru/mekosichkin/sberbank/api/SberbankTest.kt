package ru.mekosichkin.sberbank.api

import org.junit.Test


internal class SberbankTest {
    val sberbank = Sberbank()

    @Test
    fun getProducts() {
        println("login:")
        val mGuid = sberbank.register(readLine()!!)
        println("smsPassword:")
        val sms = readLine()!!
        sberbank.confirm(mGuid, sms)
        sberbank.createPin(mGuid)
        var getProducts = sberbank.productListRaw()
        sberbank.login(mGuid)
        getProducts = sberbank.productListRaw()

    }

}