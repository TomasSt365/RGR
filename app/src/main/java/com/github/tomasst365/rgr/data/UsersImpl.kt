package com.github.tomasst365.rgr.data

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query


class UsersImpl : Users {
    private var usersList: MutableList<User> = ArrayList()
    private var store = FirebaseFirestore.getInstance()
    private var collectionReference = store.collection(USERS_COLLECTION)

    override fun size(): Int {
        return usersList.size
    }

    override fun getUser(position: Int): User {
        return usersList[position]
    }

    override fun addUser(user: User) {
        collectionReference
            .add(UserDataConvertor.noteDataToDocument(user))
        usersList.add(user)
    }

    override fun editUser(position: Int, user: User) {
        collectionReference
            .document(usersList[position].id as String)
            .update(UserDataConvertor.noteDataToDocument(user))
        usersList[position] = user
    }

    override fun deleteUser(position: Int) {
        collectionReference
            .document(usersList[position].id as String)
            .delete()
        usersList.removeAt(position)
    }

    override fun init(usersResponse: UsersResponse?): Users {
        collectionReference
            .orderBy(UserDataConvertor.Fields.LOGIN, Query.Direction.ASCENDING)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    usersList = ArrayList()
                    for (docFields in task.result!!) {
                        val note =
                            UserDataConvertor.documentToNoteData(docFields.id, docFields.data)
                        usersList.add(note)
                    }
                    usersResponse!!.initialized(this@UsersImpl)
                }
            }
        return this
    }

    companion object {
        private const val USERS_COLLECTION = "USERS_COLLECTION"
    }


}