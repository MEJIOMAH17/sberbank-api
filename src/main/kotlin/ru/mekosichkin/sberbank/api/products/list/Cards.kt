package ru.mekosichkin.sberbank.api.products.list

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty

class Cards {
    @JacksonXmlProperty(localName = "card")
    @JacksonXmlElementWrapper(useWrapping = false)
    var list: List<Card>? = null
    var status: Status? = null

}