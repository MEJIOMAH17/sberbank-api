package ru.mekosichkin.sberbank.api

import com.fasterxml.jackson.dataformat.xml.XmlMapper
import org.junit.Test
import ru.mekosichkin.sberbank.api.products.list.Response
import java.io.File


internal class SberbankTest {

    @Test
    fun getProducts() {
        println("login:")
        val mGuid = SberbankRegistration().register(readLine()!!) {
            println("smsPassword:")
            readLine()!!
        }
        val sberbank = SberbankLogining().login(mGuid)
        var getProducts = sberbank.productList()
        print(getProducts)
    }
    @Test
    fun test(){
        val xml=File("/home/mark/.IntelliJIdea2019.3/config/scratches/scratch_5.xml").readText()
        XmlMapper().readValue(xml, Response::class.java)
    }

}