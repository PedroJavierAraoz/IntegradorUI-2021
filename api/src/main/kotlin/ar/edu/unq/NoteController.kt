package ar.edu.unq

import io.javalin.http.Context
import org.ui.DraftComment
import org.ui.MediumSystem
import org.ui.NotFound


class NoteController(val system: MediumSystem) {
    fun getLatestNote(ctx: Context){
        try {
            val latestNotes = system.latestAddedNotes().map { note ->
                ApiNote(note.id, note.title, note.body, note.categories, ControlFunctions.getComentsAdapter(note))
                }
            ctx.status(200)
            ctx.json(latestNotes)
            }
            catch (e: NotFound) {
            ctx.status(404)
            ctx.json(NotFoundResponse("Not found notes"))
        }

    }


    fun getNoteId(ctx: Context) {
        try {
            val note = system.getNote(ctx.pathParam("id"))
            ctx.status(200)
            ctx.json(
                ApiNote(
                    note.id, note.title, note.body, note.categories, ControlFunctions.getComentsAdapter(note)
                )
            )
        } catch (e: NotFound) {
            ctx.status(404)
            ctx.json(NotFoundResponse("Not found note with id " + ctx.pathParam("id")))
        }
    }
    fun addComment(ctx: Context){
        // @Throw NotFound si `noteId` o `authorId` no existen
        //fun addComment(noteId: String, authorId: String, comment: DraftComment): void
        val logedUsrId = ControlFunctions.getAutrIdContext(ctx)
        val dComment = ctx.body<DraftComment>()
        try {
            ControlFunctions.validateComment(dComment)
            system.addComment(ctx.pathParam("id"), logedUsrId, dComment)
            ctx.status(200)
            ctx.json(OkResponse("ok"))

        }catch (e: NotFound){

        }
    }
}