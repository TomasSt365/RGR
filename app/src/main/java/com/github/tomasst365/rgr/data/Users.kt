package com.github.tomasst365.rgr.data

interface Users {
    fun size(): Int
    fun getUser(position: Int): User
    fun addUser(user: User)
    fun editUser(position: Int, user: User)
    fun deleteUser(position: Int)

    fun init(usersResponse: UsersResponse?): Users
}