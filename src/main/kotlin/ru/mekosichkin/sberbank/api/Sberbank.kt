package ru.mekosichkin.sberbank.api

import com.fasterxml.jackson.dataformat.xml.XmlMapper
import io.github.rybalkinsd.kohttp.dsl.httpPost
import ru.mekosichkin.sberbank.api.products.list.Response

/**
 * API to Sberbank
 * Use [SberbankLogining.login] to get instance of this class
 */
class Sberbank internal constructor(private val jsessionid: String) {
    var xmlMapper = XmlMapper()

    /**
     * Get product list as [Response]
     */
    fun productList(): Response {
        return xmlMapper.readValue(productListRaw(), Response::class.java)
    }

    /**
     * @return raw xml with product list of current account
     */
    fun productListRaw(): String {

        val httpPost = httpPost {
            scheme = "https"
            host = "node2.online.sberbank.ru"
            port = 4477
            path = "/mobile9/private/products/list.do"
            header {
                cookie {
                    "JSESSIONID" to jsessionid
                }
            }
            body {
                form {
                    "showProductType" to "cards,accounts,imaccounts,loans"
                }
            }

        }
        return httpPost.body()!!.string()
    }


}
