package ru.mekosichkin.sberbank.api

import org.junit.Test


internal class SberbankTest {
    val sberbank = Sberbank()

    @Test
    fun getProducts() {
        println("login:")
        val mGuid = sberbank.register(readLine()!!)
        println("smsPassword:")
        val sms = readLine()!!
        sberbank.confirm(mGuid, sms)
        sberbank.createPin(mGuid)
        var getProducts = sberbank.productListRaw()
        sberbank.login(mGuid)
        getProducts = sberbank.productListRaw()

    }



    @Test
    fun parseRegisterResponse() {
        val mGuid = sberbank.parseRegisterResponse(registerRs)
        assert(mGuid.value == "3e8c8a6f7f04e080553fe9d7a238e63f")
    }

    @Test
    fun parseConfirmRs() {
        assert(sberbank.parseConfirmRs(successConfirmRs))
    }



    val registerRs = """<?xml version="1.0" encoding="windows-1251" ?>
<response>
    <status>
        <code>0</code>
    </status>
    <loginCompleted>false</loginCompleted>
    <confirmRegistrationStage>
        <mGUID>3e8c8a6f7f04e080553fe9d7a238e63f</mGUID>
    </confirmRegistrationStage>
    <confirmInfo>
        <type>smsp</type>
        <smsp>
            <lifeTime>600</lifeTime>
            <attemptsRemain>3</attemptsRemain>
        </smsp>
    </confirmInfo>
    <registrationParameters>
        <minimumPINLength>5</minimumPINLength>
    </registrationParameters>
</response>"""

    val successConfirmRs = "<?xml version=\"1.0\" encoding=\"windows-1251\" ?> \n" +
            "<response> \n" +
            "    <status> \n" +
            "        <code>0</code> \n" +
            "    </status> \n" +
            "            <loginCompleted>false</loginCompleted> \n" +
            "</response>"

    val createPinRs = """<response> 
	<status> 
		<code>0</code> 
	</status> 
	<loginCompleted>false</loginCompleted> 
	<loginData> 
		<host>node2.online.sberbank.ru</host> 
		<token>a791e6007a2a5506cd495e947829b7fa</token> 
		<externalToken>060d0500060406535752545a02015a5155030702050300005606520601030453</externalToken> 
	</loginData> 
</response>"""


}