package ru.mekosichkin.sberbank.api.products.list

import ru.mekosichkin.sberbank.api.ProductFullId

class Account {
    var closeDate: String? = null
    var availcash: Availcash? = null
    var number: String? = null
    var moneyBoxAvailable: String? = null
    var smsName: String? = null
    var arrested: String? = null
    var balance: Balance? = null
    var rate: String? = null
    var name: String? = null
    var showarrestdetail: String? = null
    var id: String? = null
    var state: String? = null
    val productFullId: ProductFullId
        get() = ProductFullId(type = "account", id = id!!)
}
