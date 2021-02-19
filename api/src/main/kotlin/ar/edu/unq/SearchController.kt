package ar.edu.unq

import io.javalin.http.Context
import org.ui.MediumSystem


class SearchController(val system: MediumSystem){
    fun  searchByTitle(ctx: Context){
        val searchText:String? = ctx.queryParam("q")?:""

        if  (searchText!!.isNotEmpty()) {
            ctx.status(200)
            ctx.json( system.searchNotesByTitle(searchText).map { note ->
                ApiNote(note.id, note.title, note.body, note.categories, ControlFunctions.getComentsAdapter(note))
            })
        } else {
            ctx.json(ErrorResponse("campos vacios", "no hay resultados"))
            }
        }
    fun searchByCategory(ctx: Context){
        val nameToSearch=ctx.pathParam("name")
        ctx.status(200)
        ctx.json( system.searchNotesByCategory(nameToSearch).map { note ->
            ApiNote(note.id, note.title, note.body, note.categories, ControlFunctions.getComentsAdapter(note))
            })
    }
}