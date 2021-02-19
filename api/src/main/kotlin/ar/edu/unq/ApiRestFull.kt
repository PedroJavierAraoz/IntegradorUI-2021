package ar.edu.unq

import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.*
import io.javalin.core.security.Role
import io.javalin.core.util.RouteOverviewPlugin
import org.ui.bootstrap.getMediumSystem


enum class MediumRoles : Role {
    ANYONE, USER
}


fun main(args: Array<String>) {
    val system = getMediumSystem()

    val authorController = AuthorController(system)
    val noteController = NoteController(system)
    val searchController = SearchController(system)

    val app = Javalin.create {
        it.defaultContentType = "application/json"
        it.registerPlugin(RouteOverviewPlugin("/routes"))
        it.accessManager(MediumSystemAccessManager(system))
        it.enableCorsForAllOrigins()
    }.start(7000)
    app.before {
        it.header("Access-Control-Expose-Headers", "*")
    }

    app.routes {

        path("login") {
            post(authorController::login, setOf(MediumRoles.ANYONE))
        }
        path("register") {
            post(authorController::register, setOf(MediumRoles.ANYONE))
        }
        path("user") {
            get(authorController::logedAuthor, setOf(MediumRoles.USER))
            path("notes") {
                get(authorController::notesByAuthorId, setOf(MediumRoles.USER))
            }
        }
        path("content"){
            get(noteController::getLatestNote, setOf(MediumRoles.USER))
            path(":id"){
                get(noteController::getNoteId, setOf(MediumRoles.USER))
                post(noteController::addComment, setOf(MediumRoles.USER))
            }
        }
        path("search") {
            get(searchController::searchByTitle, setOf(MediumRoles.USER))
        }
        path("category/:name"){
             get(searchController::searchByCategory, setOf(MediumRoles.USER))
        }

    }
}
    data class NotFoundResponse(val result: String?)
    data class OkResponse(val result: String)
    data class ErrorResponse(val result: String, val message: String)

