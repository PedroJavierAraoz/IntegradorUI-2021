package ar.edu.unq


import com.auth0.jwt.JWT
import com.auth0.jwt.JWTCreator
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import javalinjwt.JWTGenerator
import javalinjwt.JWTProvider
import org.ui.Author


class NotValidToken : Exception("Not valid token")
class TokenController {
    val algorithm: Algorithm = Algorithm.HMAC256("very_secret");

    val generator = JWTGenerator<Author> { author: Author, alg: Algorithm ->
        val token: JWTCreator.Builder = JWT.create().withClaim("id", author.id).withClaim("name",author.name)
        token.sign(alg)
    }

    val verifier : JWTVerifier = JWT.require(algorithm).build()

    val provider : JWTProvider = JWTProvider (algorithm, generator, verifier)

    fun generateToken(author: Author):String{
        return provider.generateToken(author)
    }

    fun verifyToken(token:String): String{
        val decodedJWT= provider.validateToken(token)
        if (decodedJWT.isPresent() && decodedJWT.get().claims.contains("id")) {
            return decodedJWT.get().getClaim("id").asString()
        }
        throw NotValidToken()
    }
}
