package ru.mekosichkin.sberbank.api.payments.list

import com.fasterxml.jackson.annotation.JsonFormat
import java.util.*

class Operation {
    @JsonFormat
    (shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy'T'HH:mm:ss")
    var date: Date? = null
    var autopayable: String? = null
    var description: String? = null
    var type: String? = null
    var copyable: String? = null
    var form: String? = null
    var creationChannel: String? = null
    var classificationCode: String? = null
    var invoiceReminderSupported: String? = null
    var operationAmount: OperationAmount? = null
    var from: String? = null
    var nationalAmount: NationalAmount? = null
    var commission: Commission? = null
    var id: String? = null
    var imageId: ImageId? = null
    var state: String? = null
    var to: String? = null
    var invoiceSubscriptionSupported: String? = null
    var ufsId: String? = null
    var templatable: String? = null
    var isMobilePayment: String? = null
        @JvmName("setIsMobilePayment") set
}