package info.nightscout.androidaps.plugins.general.externalAppCommunicator

class Sms {

    var phoneNumber: String
    var text: String
    var date: Long
    var received = false
    var sent = false
    var processed = false
    var ignored = false
    var broadcast = false

    internal constructor(phoneNumber: String, text: String, dummy: Boolean) {
        this.phoneNumber = phoneNumber
        this.text = text
        date = System.currentTimeMillis()
        received = true
    }

    internal constructor(phoneNumber: String, text: String) {
        this.phoneNumber = phoneNumber
        this.text = text
        date = System.currentTimeMillis()
        sent = true
    }

    override fun toString(): String {
        return "SMS from $phoneNumber: $text"
    }

    companion object {
        fun broadcast(sms: Sms): Sms {
            val copy = Sms(sms.phoneNumber, sms.text)
            copy.broadcast = true
            return copy
        }
    }
}