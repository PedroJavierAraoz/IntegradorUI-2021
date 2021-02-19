package ar.edu.unq.Exceptions

class UserNameExist(): Exception("Username is used") {}

class InvalidEmailException(): Exception("Formato de email incorrecto") {}
class InvalidBodyException(): Exception("No  se permiten campos  vacios")