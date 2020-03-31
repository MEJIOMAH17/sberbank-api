package ru.mekosichkin.sberbank.api

import org.xml.sax.InputSource
import java.io.StringReader
import java.time.format.DateTimeFormatter
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.xpath.XPathFactory

internal object Helper {
    internal val defaultPin = "42424"
    internal val jsessionid = "0000uHrFvcD0Xv3qIYW5bXDS_Jy:1akk7tu3m|rsDPJSESSIONID=PBC5YS:-152294547"
    internal val swJsessionId = "8f0961c07d8ff7ca1a881002df39ec2f"
    internal val DDMMYYYY=DateTimeFormatter.ofPattern("dd.MM.YYYY")
    private val xpFactory = XPathFactory.newInstance()

    fun findByXpath(xpath: String, xml: String): String {
        val dbFactory = DocumentBuilderFactory.newInstance()
        val dBuilder = dbFactory.newDocumentBuilder()
        val doc = dBuilder.parse(InputSource(StringReader(xml)))
        val xPath = xpFactory.newXPath()
        return xPath.evaluate(xpath, doc)
                .replace("\n", "")
                .replace(" ", "")

    }

}
