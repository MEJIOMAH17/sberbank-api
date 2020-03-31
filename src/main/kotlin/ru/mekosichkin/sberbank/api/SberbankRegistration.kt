package ru.mekosichkin.sberbank.api

import io.github.rybalkinsd.kohttp.dsl.httpPost
import ru.mekosichkin.sberbank.api.Helper.defaultPin
import ru.mekosichkin.sberbank.api.Helper.findByXpath
import ru.mekosichkin.sberbank.api.Helper.jsessionid
import ru.mekosichkin.sberbank.api.Helper.swJsessionId

/**
 * Registration process in sberbank.
 */
class SberbankRegistration {

    /**
     * Get new [MGuid] to [SberbankLogining.login]
     * @param login login of user
     * @param pin new pin code of app
     * @param smsPasswordSupplier this function should return registration sms from sberbank
     */
    fun register(login: String, smsPasswordSupplier: () -> String): MGuid {
        val mGuid = register(login)
        confirm(mGuid, smsPasswordSupplier.invoke())
        createPin(mGuid, defaultPin)
        return mGuid
    }

    private fun register(login: String): MGuid {
        val httpPost = httpPost {
            scheme = "https"
            host = "online.sberbank.ru"
            port = 4477
            path = "/CSAMAPI/registerApp.do"
            header {
                cookie {
                    "JSESSIONID" to jsessionid
                    "SWJSESSIONID" to swJsessionId
                }
            }
            body {
                form {
                    "operation" to "register"
                    "login" to login
                    "version" to "9.20"
                    "appType" to "android"
                    "appVersion" to "10.2.0"
                    "deviceName" to "HUAWEI_ANE-LX1"
                    "devID" to "607d725604d1f032e50bb3c0622e791d3f400000"
                    "devIDOld" to "63c103d506178038cb0964403f372ae5af1e0000"

                    "mobileSdkData" to "{\"TIMESTAMP\":\"2019-09-13T07:23:14Z\",\"HardwareID\":\"-1\",\"SIM_ID\":\"-1\",\"PhoneNumber\":\"-1\",\"GeoLocationInfo\":[{\"Timestamp\":\"0\",\"Status\":\"1\"}],\"DeviceModel\":\"ANE-LX1\",\"MultitaskingSupported\":true,\"DeviceName\":\"marky\",\"DeviceSystemName\":\"Android\",\"DeviceSystemVersion\":\"28\",\"Languages\":\"ru\",\"WiFiMacAddress\":\"02:00:00:00:00:00\",\"WiFiNetworksData\":{\"BBSID\":\"02:00:00:00:00:00\",\"SignalStrength\":\"-47\",\"Channel\":\"null\"},\"CellTowerId\":\"-1\",\"LocationAreaCode\":\"-1\",\"ScreenSize\":\"1080x2060\",\"RSA_ApplicationKey\":\"2C501591EA5BF79F1C0ABA8B628C2571\",\"MCC\":\"286\",\"MNC\":\"02\",\"OS_ID\":\"1f32651b72df5515\",\"SDK_VERSION\":\"3.10.0\",\"Compromised\":0,\"Emulator\":0}"
                    "mobileSDKKAV" to "{\"osVersion\":0,\"KavSdkId\":\"\",\"KavSdkVersion\":\"\",\"KavSdkVirusDBVersion\":\"SdkVirusDbInfo(year=0, month=0, day=0, hour=0, minute=0, second=0, knownThreatsCount=0, records=0, size=0)\",\"KavSdkVirusDBStatus\":\"\",\"KavSdkVirusDBStatusDate\":\"\",\"KavSdkRoot\":false,\"LowPasswordQuality\":false,\"NonMarketAppsAllowed\":false,\"UsbDebugOn\":false,\"ScanStatus\":\"NONE\"}"
                }
            }
        }
        val rs = httpPost.body()!!.string()
        return parseRegisterResponse(rs)
    }

    private fun confirm(mGuid: MGuid, smsPassword: String): Boolean {
        val httpPost = httpPost {
            scheme = "https"
            host = "online.sberbank.ru"
            port = 4477
            path = "/CSAMAPI/registerApp.do"
            header {
                cookie {
                    "JSESSIONID" to jsessionid
                    "SWJSESSIONID" to swJsessionId
                }
            }
            body {
                form {
                    "operation" to "confirm"
                    "mGUID" to mGuid.value
                    "smsPassword" to smsPassword
                    "version" to "9.20"
                    "appType" to "android"
                    "mobileSdkData" to "{\"TIMESTAMP\":\"2019-09-13T07:23:14Z\",\"HardwareID\":\"-1\",\"SIM_ID\":\"-1\",\"PhoneNumber\":\"-1\",\"GeoLocationInfo\":[{\"Timestamp\":\"0\",\"Status\":\"1\"}],\"DeviceModel\":\"ANE-LX1\",\"MultitaskingSupported\":true,\"DeviceName\":\"marky\",\"DeviceSystemName\":\"Android\",\"DeviceSystemVersion\":\"28\",\"Languages\":\"ru\",\"WiFiMacAddress\":\"02:00:00:00:00:00\",\"WiFiNetworksData\":{\"BBSID\":\"02:00:00:00:00:00\",\"SignalStrength\":\"-47\",\"Channel\":\"null\"},\"CellTowerId\":\"-1\",\"LocationAreaCode\":\"-1\",\"ScreenSize\":\"1080x2060\",\"RSA_ApplicationKey\":\"2C501591EA5BF79F1C0ABA8B628C2571\",\"MCC\":\"286\",\"MNC\":\"02\",\"OS_ID\":\"1f32651b72df5515\",\"SDK_VERSION\":\"3.10.0\",\"Compromised\":0,\"Emulator\":0}"
                    "mobileSDKKAV" to "{\"osVersion\":0,\"KavSdkId\":\"\",\"KavSdkVersion\":\"\",\"KavSdkVirusDBVersion\":\"SdkVirusDbInfo(year=0, month=0, day=0, hour=0, minute=0, second=0, knownThreatsCount=0, records=0, size=0)\",\"KavSdkVirusDBStatus\":\"\",\"KavSdkVirusDBStatusDate\":\"\",\"KavSdkRoot\":false,\"LowPasswordQuality\":false,\"NonMarketAppsAllowed\":false,\"UsbDebugOn\":false,\"ScanStatus\":\"NONE\"}"
                    "confirmData" to smsPassword
                    "confirmOperation" to "confirmSMS"
                }
            }
        }
        val rs = httpPost.body()!!.string()
        return parseConfirmRs(rs)
    }

    /**
     * password - 5 numbers
     */
    private fun createPin(mGuid: MGuid, pin: String) {
        val httpPost = httpPost {
            scheme = "https"
            host = "online.sberbank.ru"
            port = 4477
            path = "/CSAMAPI/registerApp.do"
            header {
                cookie {
                    "JSESSIONID" to jsessionid
                    "SWJSESSIONID" to swJsessionId
                }
            }
            body {
                form {
                    "operation" to "createPIN"
                    "mGUID" to mGuid.value
                    "password" to pin
                    "version" to "9.20"
                    "appType" to "android"
                    "appVersion" to "10.2.0"
                    "deviceName" to "HUAWEI_ANE-LX1"
                    "devID" to "607d725604d1f032e50bb3c0622e791d3f400000"
                    "devIDOld" to "63c103d506178038cb0964403f372ae5af1e0000"
                    "mobileSdkData" to "{\"TIMESTAMP\":\"2019-09-13T07:23:14Z\",\"HardwareID\":\"-1\",\"SIM_ID\":\"-1\",\"PhoneNumber\":\"-1\",\"GeoLocationInfo\":[{\"Timestamp\":\"0\",\"Status\":\"1\"}],\"DeviceModel\":\"ANE-LX1\",\"MultitaskingSupported\":true,\"DeviceName\":\"marky\",\"DeviceSystemName\":\"Android\",\"DeviceSystemVersion\":\"28\",\"Languages\":\"ru\",\"WiFiMacAddress\":\"02:00:00:00:00:00\",\"WiFiNetworksData\":{\"BBSID\":\"02:00:00:00:00:00\",\"SignalStrength\":\"-47\",\"Channel\":\"null\"},\"CellTowerId\":\"-1\",\"LocationAreaCode\":\"-1\",\"ScreenSize\":\"1080x2060\",\"RSA_ApplicationKey\":\"2C501591EA5BF79F1C0ABA8B628C2571\",\"MCC\":\"286\",\"MNC\":\"02\",\"OS_ID\":\"1f32651b72df5515\",\"SDK_VERSION\":\"3.10.0\",\"Compromised\":0,\"Emulator\":0}"
                    "mobileSDKKAV" to "{\"osVersion\":0,\"KavSdkId\":\"\",\"KavSdkVersion\":\"\",\"KavSdkVirusDBVersion\":\"SdkVirusDbInfo(year=0, month=0, day=0, hour=0, minute=0, second=0, knownThreatsCount=0, records=0, size=0)\",\"KavSdkVirusDBStatus\":\"\",\"KavSdkVirusDBStatusDate\":\"\",\"KavSdkRoot\":false,\"LowPasswordQuality\":false,\"NonMarketAppsAllowed\":false,\"UsbDebugOn\":false,\"ScanStatus\":\"NONE\"}"
                }
            }
        }
        httpPost.body()!!.string()
    }

    private fun parseRegisterResponse(response: String): MGuid {
        return MGuid(findByXpath("response/confirmRegistrationStage", response))
    }

    private fun parseConfirmRs(rs: String): Boolean {
        return findByXpath("response/status/code", rs) == "0"
    }
}