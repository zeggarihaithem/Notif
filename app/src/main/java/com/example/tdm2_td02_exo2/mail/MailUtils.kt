package com.example.tdm2_td02_exo2.mail



import com.example.tdm2_td02_exo2.ui.main.MainActivity
import javax.mail.*
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

class MailUtils(emailReceiver: String, subject: String, text: String) {

    init {
        MainActivity.appExecutors.diskIO().execute {
            val props = System.getProperties()
            props["mail.smtp.host"] = "smtp.gmail.com"
            props["mail.smtp.socketFactory.port"] = "465"
            props["mail.smtp.socketFactory.class"] = "javax.net.ssl.SSLSocketFactory"
            props["mail.smtp.auth"] = "true"
            props["mail.smtp.port"] = "465"

            val session = Session.getInstance(props,
                object : javax.mail.Authenticator() {
                    //Authenticating the password
                    override fun getPasswordAuthentication(): PasswordAuthentication {
                        return PasswordAuthentication(Credentials.EMAIL, Credentials.PASSWORD)
                    }
                })

            try {
                //Creating MimeMessage object
                val mm = MimeMessage(session)
                //Setting sender address

                mm.setFrom(InternetAddress(Credentials.EMAIL))
                //Adding receiver
                mm.addRecipient(
                    Message.RecipientType.TO,
                    InternetAddress(emailReceiver)
                )
                //Adding subject
                mm.subject = subject
                //Adding message
                mm.setText(text)

                //Sending email
                Transport.send(mm)

                MainActivity.appExecutors.mainThread().execute {
                    //Something that should be executed on main thread.
                }

            } catch (e: MessagingException) {
                e.printStackTrace()
            }
        }
    }

}