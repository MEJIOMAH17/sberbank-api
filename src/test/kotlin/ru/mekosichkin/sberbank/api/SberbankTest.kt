package ru.mekosichkin.sberbank.api

import org.junit.Test
import ru.mekosichkin.sberbank.api.payments.list.Operation
import java.io.File
import java.time.LocalDate


internal class SberbankTest {

    @Test
    fun register() {
        println("login:")
        val mGuid = SberbankRegistration().register(readLine()!!) {
            println("smsPassword:")
            readLine()!!
        }
        print(mGuid)
    }

    @Test
    fun getProducts() {
        val sberbank = createSberbank()
        var getProducts = sberbank.productList()
        print(getProducts)
    }


    @Test
    fun getPayments() {
        val sberbank = createSberbank()
        val products = sberbank.productList()
        val result = ArrayList<Operation>()
        for (card in products.cards!!.list!!) {
            val elements = sberbank.paymentsList(
                    card,
                    LocalDate.now().minusYears(5),
                    LocalDate.now(),
                    Short.MAX_VALUE.toInt(),
                    0
            ).operations
            if (elements != null) {
                result.addAll(elements)
            }
        }
        print(result)
    }

    @Test
    fun payment() {
        val sberbank = createSberbank()
        val products = sberbank.productList()
        val xx = sberbank.internalPayment(
                from = products.accounts!!.list!!.first { it.balance!!.amount!! > 0 }.productFullId,
                to = products.cards!!.list!!.first { !it.isBlocked }.productFullId,
                buyAmount = 1)
        print(xx)
    }


    private fun createSberbank(): Sberbank {
        val mguid = MGuid(File("/home/mark/Projects/sberbank-api/mguid").readText())
        val sberbank = SberbankLogining().login(mguid)
        return sberbank
    }

}