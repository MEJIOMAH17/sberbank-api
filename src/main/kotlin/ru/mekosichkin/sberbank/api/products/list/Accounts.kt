package ru.mekosichkin.sberbank.api.products.list

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty

class Accounts {
    @JacksonXmlProperty(localName = "account")
    @JacksonXmlElementWrapper(useWrapping = false)
    var list: List<Account>? = null
    var status: Status? = null

}