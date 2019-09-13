package ru.mekosichkin.sberbank.api

import org.junit.Assert
import org.junit.Test


internal class SberbankTest {
    val sberbank = Sberbank()
    @Test
    fun register() {
        val register = sberbank.register()
        assert( register.value.isNotEmpty())
    }
    @Test
    fun parseRegisterResponse(){
        val mGuid = sberbank.parseRegisterResponse(registerRs)
        assert(mGuid.value=="3e8c8a6f7f04e080553fe9d7a238e63f")
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
}