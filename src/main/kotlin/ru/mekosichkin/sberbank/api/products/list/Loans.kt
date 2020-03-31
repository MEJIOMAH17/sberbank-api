package ru.mekosichkin.sberbank.api.products.list

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty

class Loans {
    @JacksonXmlProperty(localName = "loan")
    @JacksonXmlElementWrapper(useWrapping = false)
    var list: List<Loan>? = null
    var status: Status? = null
}
