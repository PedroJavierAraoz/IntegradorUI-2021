package ar.edu.unq.view

import ar.edu.unq.viewmodel.LoginViewModel
import org.ui.DraftNote
import org.ui.bootstrap.getMediumSystem
import org.uqbar.arena.Application
import org.uqbar.arena.windows.Window

class MyApp : Application() {
    override fun createMainWindow(): Window<*> {
        val model =  getMediumSystem()

        //datos exgta
        val a1 = model.registerAuthor("Name 1","name1@gmail.com", "pass1","https://pix.example/1.png")
        val a2 = model.registerAuthor("Name 2","name2@gmail.com", "pass2","https://pix.example/2.png")
        val a3 = model.registerAuthor("Name 3","name3@gmail.com", "pass3","https://pix.example/3.png")

        model.addNote(a1.id, DraftNote("Mi nota 1", "My body 1", "tag1,tag2"))
        model.addNote(a2.id, DraftNote("Mi nota 2", "My body 2", "tag2,tag3"))
        model.addNote(a3.id, DraftNote("Mi nota 3", "My body 3", "tag1,tag3"))

        var logModel= LoginViewModel(model)
        return LoginWindow(this, logModel)
    }
}

fun main() = MyApp().start()