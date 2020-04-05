package ru.mekosichkin.sberbank.api.products.list

import ru.mekosichkin.sberbank.api.ProductFullId


class Loan {
    var smsName: String? = null
    var amount: Amount? = null
    var name: String? = null
    var nextPayAmount: NextPayAmount? = null
    var id: String? = null
    var state: String? = null
    var nextPayDate: String? = null
    val productFullId: ProductFullId
        get() = ProductFullId(type = "loan", id = id!!)

}