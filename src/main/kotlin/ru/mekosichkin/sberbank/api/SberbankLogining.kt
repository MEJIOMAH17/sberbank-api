package ru.mekosichkin.sberbank.api

import io.github.rybalkinsd.kohttp.dsl.httpPost
import ru.mekosichkin.sberbank.api.Helper.defaultPin
import ru.mekosichkin.sberbank.api.Helper.jsessionid
import ru.mekosichkin.sberbank.api.Helper.mobileSdk
import ru.mekosichkin.sberbank.api.Helper.swJsessionId

/**
 * Login process to Sberbank
 */
class SberbankLogining {

    /**
     * @param mGuid - [MGuid] from [SberbankRegistration.register]
     * @return [Sberbank] instance
     */
    fun login(mGuid: MGuid): Sberbank {
        val loginRs = privateLogin(mGuid, defaultPin)
        val jsessionId = postCSALogin(Helper.findByXpath("response/loginData/token", loginRs))
        return Sberbank(jsessionId)
    }

    private fun privateLogin(mGuid: MGuid, pin: String): String {
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
                    "password" to pin
                    "version" to "9.20"
                    "appType" to "android"
                    "appVersion" to "10.2.0"
                    "osVersion" to "28.0"
                    "deviceName" to "HUAWEI_ANE-LX1"
                    "isLightScheme" to false
                    "isSafe" to "true"
                    "mGUID" to mGuid.value
                    "devID" to "607d725604d1f032e50bb3c0622e791d3f400000"
                    mobileSdk()
                }
            }
        }
        return httpPost.body()!!.string()
    }


    private fun postCSALogin(token: String): String {
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
                    "token" to token
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
        return headers["Set-Cookie"]!!.split(";")
                .single { it.startsWith("JSESSIONID") }
                .drop("JSESSIONID=".length)

    }
}