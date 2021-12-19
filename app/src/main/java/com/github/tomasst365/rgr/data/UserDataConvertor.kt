package com.github.tomasst365.rgr.data

import java.util.*

object UserDataConvertor {

    fun documentToNoteData(id: String?, doc: Map<String?, Any?>): User {
        val noteBuilder = User.Builder()
            .setId(id)
            .setLogin(doc[Fields.LOGIN] as String?)
            .setPassword(doc[Fields.PASSWORD] as String?)
            .setIsAdmin(doc[Fields.IS_ADMIN] as Boolean)

        return noteBuilder.build()
    }

    fun noteDataToDocument(user: User?): Map<String, Any?> {
        val doc: MutableMap<String, Any?> = HashMap()
        if (user != null) {
            doc[Fields.LOGIN] = user.login
            doc[Fields.PASSWORD] = user.password
            doc[Fields.IS_ADMIN] = user.isAdmin
        }
        return doc
    }

    object Fields {
        const val LOGIN = "login"
        const val PASSWORD = "password"
        const val IS_ADMIN = "is admin"
    }

}