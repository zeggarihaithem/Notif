package com.example.tdm2_td02_exo2.broadcastReceiver

import android.app.Notification
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.SmsMessage
import android.widget.Toast
import com.example.tdm2_td02_exo2.data.entity.Contact
import com.example.tdm2_td02_exo2.mail.MailStructure
import com.example.tdm2_td02_exo2.mail.MailUtils
import com.example.tdm2_td02_exo2.notification.NotificationStructure
import com.example.tdm2_td02_exo2.notification.NotificationUtils


class SmsBroadcastReceiver : BroadcastReceiver() {
    companion object {
        //get list Contact registered
        var listContact = ArrayList<Contact>()

        fun isRegistered(number: String): Boolean {
            for (contact in listContact) {
                if (contact.number == number) {
                    return true
                }
            }
            return false
        }

        fun getEmail(number: String): String? {
            for (contact in listContact) {
                if (contact.number == number) {
                    return contact.mail
                }
            }
            return null
        }

    }


    private var msgBody: String? = null
    private var msgSource: String? = null
    private var emailAddress: String? = null
    override fun onReceive(
        context: Context,
        intent: Intent
    ) {
        if (intent.action == "android.provider.Telephony.SMS_RECEIVED") {
            val bundle = intent.extras
            try {
                if (bundle != null) {
                    val pdus =
                        bundle["pdus"] as Array<*>?
                    for (i in pdus!!.indices) {
                        val smsMessage: SmsMessage = SmsMessage.createFromPdu(
                            pdus[i] as ByteArray,
                            bundle.getString("format")
                        )
                        msgBody = smsMessage.messageBody
                        msgSource = smsMessage.displayOriginatingAddress
                    }
                    val number = "0" + msgSource!!.subSequence(4, msgSource!!.length)
                    emailAddress = getEmail(number)
                    if (isRegistered(number)) {
                        //send email
                        val sendMail = MailUtils(
                            emailAddress!!,
                            MailStructure.subject,
                            MailStructure.text
                        )
                        //send notification
                        val mNotificationUtils =
                            NotificationUtils(
                                context
                            )
                        val nb: Notification.Builder? =
                            mNotificationUtils.getAndroidChannelNotification(
                                NotificationStructure.title, NotificationStructure.body + emailAddress
                            )
                        mNotificationUtils.getManager().notify(101, nb!!.build())
                    }
                }
            } catch (e: Exception) {
                Toast.makeText(context, "erreur", Toast.LENGTH_LONG).show()
            }
        }
    }


}
