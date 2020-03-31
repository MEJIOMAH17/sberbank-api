package ru.mekosichkin.sberbank.api

import org.junit.Test


internal class SberbankTest {

    @Test
    fun getProducts() {
        println("login:")
        val mGuid = SberbankRegistration().register(readLine()!!) {
            println("smsPassword:")
            readLine()!!
        }
        val sberbank = SberbankLogining().login(mGuid)
        var getProducts = sberbank.productListRaw()
        print(getProducts)
    }

}