package ar.edu.unq

import ar.edu.unq.Exceptions.InvalidBodyException
import ar.edu.unq.Exceptions.InvalidEmailException
import io.javalin.http.BadRequestResponse
import io.javalin.http.Context
import org.ui.DraftComment
import org.ui.Note

object ControlFunctions{

    fun getAutrIdContext(ctx: Context):String{
        return ctx.attribute<String>("autrId")?:throw BadRequestResponse("not  found  user")
    }


    fun getComentsAdapter(note: Note): List<ApiComment> {
        val cmnts = note.comments.map {cmntUsr-> ApiComment(cmntUsr.id,ApiSimpleAuthor(cmntUsr.author.name, cmntUsr.author.photo),cmntUsr.body) }
        return cmnts
    }

    fun validateEmail(email: String) {
        val subString= email.subSequence( email.indexOfFirst {c->c=='@'}+1 ,email.length)
        if( subString.length+1 ==email.length
            || subString.contains('@') || subString===email) throw InvalidEmailException()
    }

    fun validateDataRegister(dataRegister:ApiRegisterAuthor){
        if ( dataRegister.name=="" || dataRegister.email =="" || dataRegister.photo=="" || dataRegister.password=="")
            throw InvalidBodyException()
    }
    fun validateComment(comment: DraftComment){
        if (comment.body.length==0) throw InvalidBodyException()

    }

}