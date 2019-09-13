package ru.mekosichkin.sberbank.api

import org.junit.Test


internal class SberbankTest {
    val sberbank = Sberbank()
    val login="mejiomah17"
    @Test
    fun register() {
        val register = sberbank.register(login)
        assert( register.value.isNotEmpty())
    }
    @Test
    fun confirm(){
        val register = sberbank.register(login)
        println("smsPassword:")
        val sms = readLine()!!
        val confirm = sberbank.confirm(register, sms)
        assert(confirm)
    }

    @Test
    fun createPin(){
        val mGuid = sberbank.register(login)
        println("smsPassword:")
        val sms = readLine()!!
        val confirm = sberbank.confirm(mGuid, sms)
        val loginData = sberbank.createPin(mGuid)
        print(mGuid)
        print(loginData)
    }
    @Test
    fun init(){
        val mGuid = sberbank.register(login)
        println("smsPassword:")
        val sms = readLine()!!
        val confirm = sberbank.confirm(mGuid, sms)
        val loginData = sberbank.createPin(mGuid)
        val init = sberbank.init(mGuid, loginData)
        assert(init.value.isNotEmpty())
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
        val loginData=sberbank.parseCreatePinRs(createPinRs)
        loginData.run {
            assert(host=="node2.online.sberbank.ru")
            assert(token=="a791e6007a2a5506cd495e947829b7fa")
            assert(externalToken=="060d0500060406535752545a02015a5155030702050300005606520601030453")
        }
    }
    @Test
    fun parseInitRs(){
        val vuid=sberbank.parseInit(initRs)
        assert(vuid.value=="e01784b28c09f96c525d0df35c172e4c")
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
    val initRs="""<response> 
	<status> 
		<code>0</code> 
	</status> 
	<VUID>e01784b28c09f96c525d0df35c172e4c</VUID> 
</response>"""


}