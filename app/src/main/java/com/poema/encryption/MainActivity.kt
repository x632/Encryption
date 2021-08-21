package com.poema.encryption

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import androidx.lifecycle.Transformations.map
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        myEncrypt()
    }

    private fun myEncrypt(){
        val theString = "this is my secret string"
        val thePassword = "andyzzon"
        println("!!! the secret string before encryption = $theString")
        println("!!! the secret password = $thePassword")

        val passwordCharArr = thePassword.toCharArray()
        val map = Encryption().encrypt(theString.toByteArray(Charsets.UTF_8), passwordCharArr)


        val enc = Base64.encodeToString(map["encrypted"], Base64.NO_WRAP)
        val sal = Base64.encodeToString(map["salt"], Base64.NO_WRAP)
        val i = Base64.encodeToString(map["iv"], Base64.NO_WRAP)

        println("!!! EncodedString : ${enc}")
        println("!!!Salt :$sal")
        println("!!!iv :$i")
        myDecrypt(enc,sal,i, passwordCharArr)
    }

    private fun myDecrypt(enc:String, sal : String, i: String, password:CharArray) {
        val encrypted = Base64.decode(enc, Base64.NO_WRAP)
        val iv = Base64.decode(i, Base64.NO_WRAP)
        val salt = Base64.decode(sal, Base64.NO_WRAP)

        //Decrypt
        val decrypted = Encryption().decrypt(
            hashMapOf("iv" to iv, "salt" to salt, "encrypted" to encrypted), password)

        decrypted?.let {
            println("!!! Avkrypterat: ${String(it, Charsets.UTF_8)}")

        }
    }

}