package com.imaddev.routes

import com.imaddev.data.checkIfUserExists
import com.imaddev.data.collections.User
import com.imaddev.data.registerUser
import com.imaddev.requests.AccountRequest
import com.imaddev.responses.SimpleResponse
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.features.ContentTransformationException
import io.ktor.http.*
import io.ktor.http.HttpStatusCode.Companion.BadRequest
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.registerRoute() {
    route("/register") {
        post {
            val request = try {
                call.receive<AccountRequest>()
            } catch (e: ContentTransformationException) {
                call.respond(BadRequest,SimpleResponse(false,"Bad Request"))
                return@post
            }
            val userExist = checkIfUserExists(request.email)
            if (!userExist) {
                if (registerUser(User(request.email, request.password))) {
                    call.respond(OK, SimpleResponse(true, "Successfully created user"))
                } else {
                    call.respond(OK, SimpleResponse(false, "Unknown error happened"))
                }
            } else {
                call.respond(OK, SimpleResponse(false, "A user with that E-mail already exists"))
            }
        }
    }
}