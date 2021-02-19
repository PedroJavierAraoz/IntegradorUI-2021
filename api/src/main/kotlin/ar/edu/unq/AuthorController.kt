package ar.edu.unq

import ar.edu.unq.Exceptions.InvalidBodyException
import ar.edu.unq.Exceptions.InvalidEmailException
import io.javalin.http.Context
import io.javalin.http.NotFoundResponse
import org.ui.*


class AuthorController(private val system: MediumSystem) {
    val tokenController= TokenController()
    fun login(ctx : Context){
        var authorLogin= ctx.body<ApiLoginAuthor>()
        try {
           val autr =system.login(authorLogin.email,authorLogin.password)
            //falta  info del token
            ctx.header("Authorization", tokenController.generateToken(autr) )
            ctx.status(200)
            ctx.json(OkResponse("ok") )
        }catch (e: NotFound){
            ctx.status(404)
            ctx.json(ErrorResponse("error", "user not found"))

        }
    }
    fun register (ctx: Context){
        var authorBody= ctx.body<ApiRegisterAuthor>()
        try {

            ControlFunctions.validateDataRegister(authorBody)   //@Throw invvalidBodyExceptions
            ControlFunctions.validateEmail(authorBody.email)    // @Throw InvalidEmailException

            val newAutr= system.registerAuthor(authorBody.name, authorBody.email, authorBody.password, authorBody.photo)

            ctx.header("Authorization", tokenController.generateToken(newAutr) )
            ctx.status(201)
            ctx.json(OkResponse("ok"))
        } catch (e: UsedEmail) {
            ctx.status(400)
            ctx.json(ErrorResponse("error", e.message.toString()))
        } catch (e: InvalidEmailException) {
            ctx.status(400)
            ctx.json(ErrorResponse("error", e.message.toString()))
        } catch (e: InvalidBodyException){
            ctx.status(400)
            ctx.json(ErrorResponse("error", e.message.toString()))
        }
    }

    fun logedAuthor(ctx: Context){
        val autr: Author
        val autrId= ControlFunctions.getAutrIdContext(ctx)
        try {//getAuthor  podria arrojar  Notfoud,  pero aca estoy  con un  usuario loqueado
            autr = system.getAuthor(autrId)
            //Throw  notFound si userId no existe
            ctx.status(200)
            ctx.json(ApiAuthor(autr.id, autr.name, autr.email, autr.photo))
        } catch (e: NotFound) {
            ctx.status(404)
            ctx.json(NotFoundResponse( "no encontramos  el  usuario"))
        }
    }

    fun notesByAuthorId(ctx:  Context){
        val autrNotes: List<ApiNote>
        val autrId= ControlFunctions.getAutrIdContext(ctx)
        try {//getAuthor  podria arrojar  Notfoud,  pero aca estoy  con un  usuario loqueado
            autrNotes = system.searchNotesByAuthorId(autrId).map{ note->
                ApiNote( note.id, note.title, note.body, note.categories, ControlFunctions.getComentsAdapter(note))

            }
            //Throw  notFound si userId no existe
            ctx.status(200)
            ctx.json(autrNotes)
        } catch (e: NotFound) {
            ctx.status(404)
            ctx.json(NotFoundResponse( "no encontramos  el  usuario"))
        }

    }


}