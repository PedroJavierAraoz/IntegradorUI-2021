import org.uqbar.commons.model.annotations.Observable

@Observable
class NoteViewModel (var id : String, var title : String ){}
//Uso  para  la  lista  de   posts en  la tabla

@Observable
class EditNoteModel(var title: String, var body: String,var categories: String){
    //uso  para  la  ventana dde  edicion de  nota
    fun whiteFields() = (title==""||body==""||categories=="")
}
@Observable
class DeleteNoteModel(val id: String)
