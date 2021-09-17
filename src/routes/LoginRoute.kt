package com.imaddev.routes

import com.imaddev.data.checkPasswordForEmail
import com.imaddev.requests.AccountRequest
import com.imaddev.responses.SimpleResponse
import io.ktor.application.*
import io.ktor.features.ContentTransformationException
import io.ktor.http.*
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*


fun Route.loginRoute() {
    route("/login") {
        post {
            val request = try {
                call.receive<AccountRequest>()
            } catch (e: ContentTransformationException){
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }

            val isPasswordCorrect = checkPasswordForEmail(request.email,request.password)
            if(isPasswordCorrect) {
                call.respond(OK,SimpleResponse(true,"You are now connected"))
            }else {
                call.respond(OK,SimpleResponse(false,"The E-mail or password is incorrect"))
            }
        }
    }
}