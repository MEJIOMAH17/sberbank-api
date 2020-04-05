package ru.mekosichkin.sberbank.api.products.list

import ru.mekosichkin.sberbank.api.ProductFullId

class Card {

    var id: String? = null
    var smsName: String? = null
    var blockInfoReceived: String? = null
    var isMain: String? = null
        @JvmName("setIsMain") set
    var showarrestdetail: String? = null
    var description: String? = null
    var type: String? = null
    var number: String? = null
    var availableLimit: AvailableLimit? = null
    var tokenExists: String? = null
    var name: String? = null
    var options: Options? = null
    var expireDate: String? = null

    /**
     * Известные состояния blocked, active
     */
    var state: String? = null
    var cardAccount: String? = null
    var tokenList: List<Token>? = null
    var statusWay4: String? = null
    var isAllowedPriorityP2P: Boolean? = null
        @JvmName("setIsAllowedPriorityP2P") set

    val productFullId: ProductFullId
        get() = ProductFullId("card:${id}")
    val isBlocked: Boolean
        get() = state == "blocked"
}
