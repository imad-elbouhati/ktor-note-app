package com.imaddev.requests

data class AddOwnerRequest(
    val noteId: String,
    val owner: String
)