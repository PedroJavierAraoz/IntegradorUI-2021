package ar.edu.unq

data class ApiLoginAuthor (val email: String, val password :String) //para  parsear el login
data class ApiRegisterAuthor( val name: String,val email:String, val password: String,val photo:String) //parsear el register
data class ApiAuthor(var id:String, var name: String, var email: String,  var photo: String )
data class ApiNote(val id: String, var title: String, var body: String, var categories: List<String>, val comments: List<ApiComment>)
data class ApiComment(val id: String, val author: ApiSimpleAuthor, val body: String)
data class ApiSimpleAuthor(val name: String,val photo: String)