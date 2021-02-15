package ar.edu.unq.view


import AutorViewModel
import org.ui.NotFound
import ar.edu.unq.viewmodel.LoginViewModel
import org.uqbar.arena.kotlin.extensions.*
import org.uqbar.arena.widgets.*
import org.uqbar.arena.windows.SimpleWindow
import org.uqbar.arena.windows.WindowOwner
import org.uqbar.commons.model.exceptions.UserException
import java.awt.Color

class LoginWindow : SimpleWindow<LoginViewModel> {
    constructor(parent: WindowOwner, model: LoginViewModel) : super(parent, model)


    override fun createFormPanel(mainPanel: Panel) {
        mainPanel!!.setWidth(250)
        val userPanel= Panel(mainPanel)
        userPanel.asColumns(2)
        val passwordPanel= Panel(mainPanel)
        passwordPanel.asColumns(2)

        thisWindow.setMinHeight(200)
        thisWindow.setMinWidth(400)
        title = "MediumSystem"

        Label(userPanel) withText("Email")
        var usuario= TextBox(userPanel) with {
            height = 20
            width = 100
            bindTo("email")
        }

        Label(passwordPanel) with { text = "Password" }
        PasswordField(passwordPanel) with {
            height = 20
            width = 100
            bindTo("pass")
        }

        val buttonPanel= Panel(mainPanel)
        buttonPanel.asHorizontal()
        Button(buttonPanel) with {
            text = "Ingresar"
            color = Color.RED
            onClick{ try {
                val autor = modelObject.system.login(modelObject.email!!,modelObject.pass!! )
                print("abro la  ventgana  principal")

                AuthorWindow(this@LoginWindow, AutorViewModel(autor, modelObject.system)).open()
            }  catch (e: NotFound) {
                throw UserException("Ususario o contraseña incorrecto")
            }
            finally {
                modelObject.cleanFields()
            }
            }
        }
        Button(buttonPanel) with {
            text = "Salir"
            color = Color.RED
            onClick {
                this@LoginWindow.close()
            }
        }

        Button(buttonPanel) with {
            text = "Registrarse"
            color= Color.BLACK
            bindEnabledTo("enabledRegister")
        }



    }
    override fun addActions(mainPanel: Panel) {
    }


}