package ar.edu.unq.viewmodel

import org.ui.MediumSystem
import org.uqbar.commons.model.annotations.Observable

@Observable
class LoginViewModel(val system: MediumSystem) {
    var email: String=""
    var pass: String=""
    var enabledRegister :Boolean= false

    fun cleanFields(){
        email=""
        pass =""
    }

}
