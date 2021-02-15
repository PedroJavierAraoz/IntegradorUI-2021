

import org.ui.Author
import org.ui.MediumSystem
import org.uqbar.commons.model.annotations.Observable

@Observable
class AutorViewModel (val author: Author, val system: MediumSystem){
    var name =author.name
    var email = author.email
    var id = author.id
    var dataNotes =system.searchNotesByAuthorId(id ).map { NoteViewModel(it.id,it.title) }
    var checkSelected =true
    var selected = (if (!dataNotes.isEmpty()) {dataNotes[0] } else {null})
        set(value){
            checkSelected=(value!=null)
            field=value
        }


    fun updateNotesAuthor(){
        dataNotes =system.searchNotesByAuthorId(id ).map { NoteViewModel(it.id,it.title) }

    }


}