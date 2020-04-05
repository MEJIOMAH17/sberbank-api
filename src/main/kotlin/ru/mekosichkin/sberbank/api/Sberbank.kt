package ru.mekosichkin.sberbank.api

import com.fasterxml.jackson.dataformat.xml.XmlMapper
import io.github.rybalkinsd.kohttp.dsl.httpGet
import io.github.rybalkinsd.kohttp.dsl.httpPost
import io.github.rybalkinsd.kohttp.ext.url
import ru.mekosichkin.sberbank.api.Helper.mobileSdk
import ru.mekosichkin.sberbank.api.products.list.Card
import java.time.LocalDate
import ru.mekosichkin.sberbank.api.payments.list.Response as PaymentListResponse
import ru.mekosichkin.sberbank.api.products.list.Response as ProductListResponse

/**
 * API to Sberbank
 * Use [SberbankLogining.login] to get instance of this class
 */
class Sberbank internal constructor(private val jsessionid: String) {
    var xmlMapper = XmlMapper()

    /**
     * Get product list as [Response]
     */
    fun productList(): ProductListResponse {
        return xmlMapper.readValue(productListRaw(), ProductListResponse::class.java)
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

    /**
     * @param cardId from [productList]
     * @param from findTransactions from date.
     * @param to findTransactions to date.
     * @param paginationSize how much payments do you need in response
     * @param paginationOffset  skip first [paginationOffset] payments
     */
    fun paymentsList(card: Card,
                     from: LocalDate,
                     to: LocalDate,
                     paginationSize: Int = 50,
                     paginationOffset: Int = 0): PaymentListResponse {
        val paymentsListRaw = this.paymentsListRaw(
                cardId = card.id!!,
                from = from.format(Helper.DDMMYYYY),
                to = to.format(Helper.DDMMYYYY),
                paginationSize = paginationSize,
                paginationOffset = paginationOffset)
        return xmlMapper.readValue(paymentsListRaw, PaymentListResponse::class.java)
    }

    /**
     * @param cardId from [productListRaw]
     * @param from findTransactions from date. format DD.MM.YYYY
     * @param to findTransactions to date. format DD.MM.YYYY
     * @param paginationSize how much payments do you need in response
     * @param paginationOffset  skip first [paginationOffset] payments
     */
    fun paymentsListRaw(cardId: String,
                        from: String,
                        to: String,
                        paginationSize: Int,
                        paginationOffset: Int): String {
        val httpPost = httpPost {
            scheme = "https"
            host = "node2.online.sberbank.ru"
            port = 4477
            path = "/mobile9/private/payments/list.do"
            header {
                cookie {
                    "JSESSIONID" to jsessionid
                }
            }
            body {
                form {
                    "from" to from
                    "to" to to
                    "usedResource" to "card:$cardId"
                    "showExternal" to "true"
                    "paginationSize" to paginationSize
                    "paginationOffset" to paginationOffset
                    "includeUfs" to "true"
                }
            }

        }
        return httpPost.body()!!.string()
    }

    /**
     * Перевод между своими счетами. (вклады/карты)
     * @param from откуда перевести деньги
     *@param to куда перевести деньги
     *@param buyAmount  сумма в рублях
     */
    fun internalPayment(from: ProductFullId,
                        to: ProductFullId,
                        buyAmount: Int): String {
        val first = httpPost {
            scheme = "https"
            host = "node2.online.sberbank.ru"
            port = 4477
            path = "/mobile9/private/payments/payment.do"
            header {
                cookie {
                    "JSESSIONID" to jsessionid
                }
            }
            body {
                form {
                    "form" to "InternalPayment"
                    "operation" to "init"
                }
            }

        }
        var transactionToken = Helper.findByXpath("response/transactionToken", first.body()!!.string())
        val second = httpPost {
            scheme = "https"
            host = "node2.online.sberbank.ru"
            port = 4477
            path = "/mobile9/private/payments/payment.do"
            header {
                cookie {
                    "JSESSIONID" to jsessionid
                }
            }
            body {
                form {
                    "fromResource" to from.value
                    "toResource" to to.value
                    "transactionToken" to transactionToken
                    "buyAmount" to buyAmount
                    "form" to "InternalPayment"
                    "exactAmount" to "destination-field-exact"
                    "operation" to "save"
                }
            }

        }
        val redirectUrl = second.networkResponse()!!.request().url()
        val preConfirm = httpGet {
            url(redirectUrl.url())
            header {
                cookie {
                    "JSESSIONID" to jsessionid
                }
            }
        }.body()!!.string()
        val id = Helper.findByXpath("response/document/id", preConfirm)
        transactionToken = Helper.findByXpath("response/transactionToken", preConfirm)
        val confirm = httpPost {
            scheme = "https"
            host = "node2.online.sberbank.ru"
            port = 4477
            path = "/mobile9/private/payments/confirm.do"
            header {
                cookie {
                    "JSESSIONID" to jsessionid
                }
            }
            body {
                form {
                    "transactionToken" to transactionToken
                    "form" to "InternalPayment"
                    "id" to id
                    "operation" to "confirm"
                    mobileSdk()
                }
            }

        }
        return confirm.body()!!.string()
    }

}
