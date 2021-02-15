package ar.edu.unq.view

import AutorViewModel
import DeleteNoteModel
import EditNoteModel
import NoteViewModel
import ar.edu.unq.exceptions.WhiteFieldExceptions
import org.ui.DraftNote
import org.ui.NotFound
import org.uqbar.arena.kotlin.extensions.*
import org.uqbar.arena.widgets.*
import org.uqbar.arena.windows.Window
import org.uqbar.arena.windows.WindowOwner
import org.uqbar.commons.model.exceptions.UserException

class AuthorWindow(owner: WindowOwner, model: AutorViewModel): Window<AutorViewModel>(owner, model) {

    override fun createContents(mainPanel: Panel) {
        title = "Ventana  de autor"

        //panel con  la informacion del usuario
        var usrPanel= GroupPanel(mainPanel) with {
            asColumns(2)
            setWidth(300)
            setTitle("Usuario actual")
        }
        Label(usrPanel) with{
            text="Usuario"
            setFontSize(12)
        }
        Label(usrPanel) with {
            bindTo("name")
            setFontSize(14)

        }
        Label(usrPanel) with {text="Id de usuario"
            setFontSize(12)
        }
        Label(usrPanel) with {
            bindTo("id")
            setFontSize(12)
        }

        Label(usrPanel) with {text="Email"
            setFontSize(12)
        }
        Label(usrPanel) with {
            bindTo("email")
            setFontSize(12)
        }

        table<NoteViewModel>(mainPanel) {
            bindItemsTo("dataNotes")
            bindSelectionTo("selected")
            visibleRows = 10
            column {
                title = "Id note"
                fixedSize = 80
                bindContentsTo("id")
            }
            column {
                title = "Title"
                fixedSize = 200
                bindContentsTo("title")
            }
        }

        //botones de  accion
        var actButPane = Panel(mainPanel) with {
            asHorizontal()
        }
        Button(actButPane) with {
            caption = "Salir"
            onClick {
                this@AuthorWindow.close()
            }
        }




        Button(actButPane) with {
            caption = "Aregar nueva nota"

            onClick {
                val model=this@AuthorWindow.modelObject
                val modelEditNoteDialog = EditNoteModel("","","")
                EditNoteDialog(thisWindow, modelEditNoteDialog) with {
                    onAccept {
                        try {
                            if (modelEditNoteDialog.whiteFields())throw WhiteFieldExceptions("")
                            val note = model.system.addNote(
                                model.author.id,
                                DraftNote(
                                    modelEditNoteDialog.title,
                                    modelEditNoteDialog.body,
                                    modelEditNoteDialog.categories
                                )
                            )
                            model.updateNotesAuthor()
                        }   catch (e: WhiteFieldExceptions){
                            throw UserException("No puede haber campos vacios")
                        }

                        catch (e: NotFound) {// esto no  deberia  serccheado nunca,  pues estoy  pasando iel id  del  usuario   en curso
                            throw UserException("Ususario inexistenete")
                        }
                    }
                    onCancel {}
                    open()
                }
            }
        }
        Button(actButPane) with {
            caption = "Editar post"
            bindEnabledTo("checkSelected")
            onClick {
                val model = this@AuthorWindow.modelObject
                //voy  a  garantizar  que  los  campos  son  not null desabilitando  el  boton  en  caso correspondiente

                var toEditNote=model.system.getNote(model.selected!!.id)
                val modelEditNoteDialog =
                    EditNoteModel(toEditNote.title, toEditNote.body, toEditNote.categories.toString().substring(1,toEditNote.categories.toString().length-1))
                EditNoteDialog(thisWindow, modelEditNoteDialog) with {
                    onAccept {
                        try {
                            if (modelEditNoteDialog.whiteFields()) throw WhiteFieldExceptions("")
                            var post = model.system.editNote(
                                model.selected!!.id,
                                DraftNote(
                                    modelEditNoteDialog.title,
                                    modelEditNoteDialog.body,
                                    modelEditNoteDialog.categories
                                )
                            )
                            model.updateNotesAuthor()
                        } catch (e: WhiteFieldExceptions) {
                            throw UserException("No puede haber campos vacios")
                        }
                        catch (e: NotFound) {// esto no  deberia  serccheado nunca,  pues estoy  pasando iel id  del  usuario   en curso
                            throw UserException("Ususario inexistenete")
                        }
                    }
                    onCancel(){}
                    open()
                }
            }
        }

        Button(actButPane) with {
            caption = "Borrar nota"
            bindEnabledTo("checkSelected")
            onClick {
                val model = this@AuthorWindow.modelObject
                //garantizo que los campos son not null desabilitando el boton en caso correspondiente
                val modelDeleteNoteDialog = DeleteNoteModel(model.selected!!.id)
                DeleteNoteDialog(thisWindow, modelDeleteNoteDialog) with {
                    onAccept {
                        model.system.removeNote(model.selected!!.id)
                        model.updateNotesAuthor()
                    }
                    onCancel(){}
                    open()
                }
            }
        }
    }
}


