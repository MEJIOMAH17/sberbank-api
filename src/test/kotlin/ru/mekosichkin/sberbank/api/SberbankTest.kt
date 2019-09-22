package ru.mekosichkin.sberbank.api

import org.junit.Test


internal class SberbankTest {
    val sberbank = Sberbank()
    @Test
    fun register() {
        val register = sberbank.register()
        assert( register.value.isNotEmpty())
    }
    @Test
    fun confirm(){
        val register = sberbank.register()
        println("smsPassword:")
        val sms = readLine()!!
        val confirm = sberbank.confirm(register, sms)
        assert(confirm)
    }

    @Test
    fun createPin(){
        val register = sberbank.register()
        println("smsPassword:")
        val sms = readLine()!!
        sberbank.confirm(register, sms)
        val loginData = sberbank.createPin(register)
        print(loginData)
    }
    @Test
    fun login(){
        val register = sberbank.register()
        println("smsPassword:")
        val sms = readLine()!!
        sberbank.confirm(register, sms)
        val loginData = sberbank.createPin(register)
        val postCSALogin = sberbank.postCSALogin(loginData)
        print(postCSALogin)
    }



    @Test
    fun parseRegisterResponse(){
        val mGuid = sberbank.parseRegisterResponse(registerRs)
        assert(mGuid.value=="3e8c8a6f7f04e080553fe9d7a238e63f")
    }
    @Test
    fun  parseConfirmRs(){
        assert(sberbank.parseConfirmRs(successConfirmRs))
    }

    @Test
    fun parseCreatePinRs(){
        val loginData=sberbank.parseLoginData(createPinRs)
        loginData.run {
            assert(host=="node2.online.sberbank.ru")
            assert(token=="a791e6007a2a5506cd495e947829b7fa")
            assert(externalToken=="060d0500060406535752545a02015a5155030702050300005606520601030453")
        }
    }



    val registerRs="""<?xml version="1.0" encoding="windows-1251" ?>
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

    val successConfirmRs="<?xml version=\"1.0\" encoding=\"windows-1251\" ?> \n" +
            "<response> \n" +
            "    <status> \n" +
            "        <code>0</code> \n" +
            "    </status> \n" +
            "            <loginCompleted>false</loginCompleted> \n" +
            "</response>"

    val createPinRs="""<response> 
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