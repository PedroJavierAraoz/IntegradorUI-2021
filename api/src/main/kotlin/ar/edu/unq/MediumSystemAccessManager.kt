package ar.edu.unq


import io.javalin.core.security.AccessManager
import io.javalin.core.security.Role
import io.javalin.http.Context
import io.javalin.http.Handler
import io.javalin.http.UnauthorizedResponse
import org.ui.MediumSystem
import org.ui.NotFound

class MediumSystemAccessManager( val  mediumSystem: MediumSystem): AccessManager{

    val tokenController = TokenController()
    override fun manage(handler: Handler, ctx: Context, roles: MutableSet<Role>) {
        val token = ctx.header("Authorization")
        when{
            roles.contains(MediumRoles.ANYONE) -> handler.handle(ctx)
            token===null -> throw UnauthorizedResponse()
            roles.contains(MediumRoles.USER) ->{
                try {
                    val autrId= tokenController.verifyToken(token)
                    mediumSystem.getAuthor(autrId)//para  que  lance  la  excepcion  si el   usr no exiset
                    ctx.attribute("autrId", autrId)
                    handler.handle(ctx)
                }catch (e: NotValidToken){
                    throw UnauthorizedResponse("Not valid token")
                }catch (e: NotFound){
                    throw UnauthorizedResponse("Not found User")
                }
            }
        }
    }
}