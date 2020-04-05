package ru.mekosichkin.sberbank.api

/**
 * Id продукта который используется для операций с продуктом.
 * Формируется как название продукта:id продукта
 * Например для карты card:32423; Для счета account:32423
 * Получить можно из [ru.mekosichkin.sberbank.api.products.list.Card] и [ru.mekosichkin.sberbank.api.products.list.Account]
 */
class ProductFullId(val type: String, val id: String) {
    val value = "$type:$id"
}