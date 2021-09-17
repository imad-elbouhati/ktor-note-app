package com.imaddev

import com.imaddev.data.checkPasswordForEmail
import com.imaddev.routes.loginRoute
import com.imaddev.routes.noteRoute
import com.imaddev.routes.registerRoute
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.routing.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    install(DefaultHeaders)
    install(CallLogging)

    install(ContentNegotiation) {
        gson {
            setPrettyPrinting()
        }
    }

    install(Authentication) {
        configureAuth()
    }

    install(Routing) {
        registerRoute()
        loginRoute()
        noteRoute()
    }
}

private fun Authentication.Configuration.configureAuth() {
    basic {
        realm = "Note Server"
        validate { credentials ->
            val email = credentials.name
            val password = credentials.password
            if (checkPasswordForEmail(email, password)) {
                UserIdPrincipal(email)
            } else null
        }
    }
}


