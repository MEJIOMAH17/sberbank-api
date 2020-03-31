package ru.mekosichkin.sberbank.api

/**
 * 5 digit pin code of application
 */
data class Pin(val value: String) {
    init {
        require(value.length == 5)
        require(value.all { it.isDigit() })
    }
}