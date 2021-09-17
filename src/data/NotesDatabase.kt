package com.imaddev.data

import com.imaddev.data.collections.User
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.eq
import org.litote.kmongo.reactivestreams.KMongo

private val client = KMongo.createClient().coroutine
private val database = client.getDatabase("NotesDatabase")
private val users = database.getCollection<User>()

suspend fun registerUser(user: User): Boolean {
    return users.insertOne(user).wasAcknowledged()
}

suspend fun checkIfUserExists(email: String): Boolean{
    return users.findOne(User::email eq email) != null
}