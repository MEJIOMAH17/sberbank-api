package ru.mekosichkin.sberbank.api

import io.github.rybalkinsd.kohttp.dsl.httpPost
import org.xml.sax.InputSource
import java.io.StringReader
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.xpath.XPathFactory

/**
 *
 */
class Sberbank {
    companion object {
        val defaultPin = Pin("42424")
    }

    //<editor-fold desc="Interior">
    private val xpFactory = XPathFactory.newInstance()
    private val jsessionid = "0000uHrFvcD0Xv3qIYW5bXDS_Jy:1akk7tu3m|rsDPJSESSIONID=PBC5YS:-152294547"
    private var secondJsessionid = ""
    private val swJsessionId = "8f0961c07d8ff7ca1a881002df39ec2f"
    //</editor-fold>

    fun register(login: String): MGuid {
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

    fun confirm(mGuid: MGuid, smsPassword: String): Boolean {
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
    fun createPin(mGuid: MGuid, pin: Pin = defaultPin) {
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
                    "password" to pin.value
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
        postCSALogin(parseLoginData(rs))
    }

    fun login(mGuid: MGuid, pin: Pin = defaultPin) {
        val httpPost = httpPost {
            scheme = "https"
            host = "online.sberbank.ru"
            port = 4477
            path = "/CSAMAPI/login.do"
            header {
                cookie {
                    "JSESSIONID" to jsessionid
                    "SWJSESSIONID" to swJsessionId
                }
            }
            body {
                form {
                    "operation" to "button.login"
                    "password" to pin.value
                    "version" to "9.20"
                    "appType" to "android"
                    "appVersion" to "10.2.0"
                    "osVersion" to "28.0"
                    "deviceName" to "HUAWEI_ANE-LX1"
                    "isLightScheme" to false
                    "isSafe" to "true"
                    "mGUID" to mGuid.value
                    "devID" to "607d725604d1f032e50bb3c0622e791d3f400000"
                    "mobileSdkData" to "{\"TIMESTAMP\":\"2019-09-13T07:23:14Z\",\"HardwareID\":\"-1\",\"SIM_ID\":\"-1\",\"PhoneNumber\":\"-1\",\"GeoLocationInfo\":[{\"Timestamp\":\"0\",\"Status\":\"1\"}],\"DeviceModel\":\"ANE-LX1\",\"MultitaskingSupported\":true,\"DeviceName\":\"marky\",\"DeviceSystemName\":\"Android\",\"DeviceSystemVersion\":\"28\",\"Languages\":\"ru\",\"WiFiMacAddress\":\"02:00:00:00:00:00\",\"WiFiNetworksData\":{\"BBSID\":\"02:00:00:00:00:00\",\"SignalStrength\":\"-47\",\"Channel\":\"null\"},\"CellTowerId\":\"-1\",\"LocationAreaCode\":\"-1\",\"ScreenSize\":\"1080x2060\",\"RSA_ApplicationKey\":\"2C501591EA5BF79F1C0ABA8B628C2571\",\"MCC\":\"286\",\"MNC\":\"02\",\"OS_ID\":\"1f32651b72df5515\",\"SDK_VERSION\":\"3.10.0\",\"Compromised\":0,\"Emulator\":0}"
                    "mobileSDKKAV" to "{\"osVersion\":0,\"KavSdkId\":\"\",\"KavSdkVersion\":\"\",\"KavSdkVirusDBVersion\":\"SdkVirusDbInfo(year=0, month=0, day=0, hour=0, minute=0, second=0, knownThreatsCount=0, records=0, size=0)\",\"KavSdkVirusDBStatus\":\"\",\"KavSdkVirusDBStatusDate\":\"\",\"KavSdkRoot\":false,\"LowPasswordQuality\":false,\"NonMarketAppsAllowed\":false,\"UsbDebugOn\":false,\"ScanStatus\":\"NONE\"}"
                }
            }
        }
        val rs = httpPost.body()!!.string()
        postCSALogin(parseLoginData(rs))
    }

    /**
     * @return raw xml with product list of current account
     */
    fun productListRaw(): String {

        val httpPost = httpPost {
            scheme = "https"
            host = "node2.online.sberbank.ru"
            port = 4477
            path = "/mobile9/private/products/list.do"
            header {
                cookie {
                    "JSESSIONID" to secondJsessionid
                }
                "Cookie" to "JSESSIONID=$jsessionid"
                "User-Agent" to "Mobile Device"
                "Accept-Encoding" to "gzip"
                "Content-Type" to "application/x-www-form-urlencoded"
                "Host" to "node2.online.sberbank.ru:4477"
                "Connection" to "Keep-Alive"
            }
            body {
                form {
                    "showProductType" to "cards,accounts,imaccounts,loans"
                }
            }

        }
        return httpPost.body()!!.string()
    }

    //<editor-fold desc="Private footer">
    private fun parseRegisterResponse(response: String): MGuid {
        return MGuid(findByXpath("response/confirmRegistrationStage", response))
    }

    private fun parseConfirmRs(rs: String): Boolean {
        return findByXpath("response/status/code", rs) == "0"
    }

    private fun findByXpath(xpath: String, xml: String): String {
        val dbFactory = DocumentBuilderFactory.newInstance()
        val dBuilder = dbFactory.newDocumentBuilder()
        val doc = dBuilder.parse(InputSource(StringReader(xml)))
        val xPath = xpFactory.newXPath()
        return xPath.evaluate(xpath, doc)
                .replace("\n", "")
                .replace(" ", "")

    }

    /**
     * This method must be invoked after login for updating jsessionId
     */
    private fun postCSALogin(loginData: LoginData) {
        val httpPost = httpPost {
            scheme = "https"
            host = "node2.online.sberbank.ru"
            port = 4477
            path = "/mobile9/postCSALogin.do"
            header {
                cookie {
                    "JSESSIONID" to jsessionid
                    "SWJSESSIONID" to swJsessionId
                }
            }
            body {
                form {
                    "token" to loginData.token
                    "appName" to "��������"
                    "appBuildOSType" to "android"
                    "appVersion" to "10.2.0"
                    "appBuildType" to "RELEASE"
                    "appFormat" to "STANDALONE"
                    "deviceName" to "HUAWEI_ANE-LX1"
                    "deviceType" to "ANE-LX1"
                    "deviceOSType" to "android"
                    "deviceOSVersion" to "9"
                }
            }
        }
        val headers = httpPost.headers()
        secondJsessionid = headers["Set-Cookie"]!!.split(";")
                .filter { it.startsWith("JSESSIONID") }
                .single()
                .drop("JSESSIONID=".length)

    }

    private fun parseLoginData(rs: String): LoginData {
        val host = findByXpath("response/loginData/host", rs)
        val token = findByXpath("response/loginData/token", rs)
        val externalToken = findByXpath("response/loginData/externalToken", rs)
        return LoginData(
                host = host,
                token = token,
                externalToken = externalToken
        )
    }

    private class LoginData(val host: String, val token: String, val externalToken: String)
    //</editor-fold>

}
