package ar.edu.unq.view

import DeleteNoteModel
import EditNoteModel
import org.uqbar.arena.kotlin.extensions.*
import org.uqbar.arena.widgets.Button
import org.uqbar.arena.widgets.Label
import org.uqbar.arena.widgets.Panel
import org.uqbar.arena.widgets.TextBox
import org.uqbar.arena.windows.Dialog
import org.uqbar.arena.windows.WindowOwner


class EditNoteDialog (parent: WindowOwner, model: EditNoteModel): Dialog<EditNoteModel>(parent, model) {
    override fun createFormPanel(mainPanel: Panel) {
        title= "Edicion de notas"
        var dataPanel = Panel(mainPanel).asColumns(2)
        Label(dataPanel) withText("Titulo")
        TextBox(dataPanel) with {
            height = 20
            width = 100
            bindTo("title")
        }
        Label(dataPanel) withText("Body")
        TextBox(dataPanel) with {
            height = 20
            width = 100
            bindTo("body")
        }
        Label(dataPanel) withText("Categorias")
        TextBox(dataPanel) with {
            height = 20
            width = 100
            bindTo("categories")
        }
    }

    override fun addActions(actionsPanel: Panel) {
        Button(actionsPanel) with {
            caption= "Aceptar"
            onClick{ accept()}
        }
        Button(actionsPanel) with {
            caption= "Cancelar"
            onClick{ cancel()}
        }
    }
}


class DeleteNoteDialog (parent: WindowOwner, model: DeleteNoteModel): Dialog<DeleteNoteModel>(parent, model) {
    override fun createFormPanel(mainPanel: Panel) {
        title = "Eliminar nota"
        Label(mainPanel) withText ("Eliminara la nota ${modelObject.id} ")

    }

    override fun addActions(actionsPanel: Panel) {
        Button(actionsPanel) with {
            caption = "Confirmar"
            onClick { accept() }
        }
        Button(actionsPanel) with {
            caption = "Cancelar"
            onClick { cancel() }
        }
    }
}