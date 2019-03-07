package br.com.hivecode.supergames.data.entity

class UserB(
    val name: String?,
    val age : Int?,
    val address : String?) {

    data class Builder(
        var name: String? = null,
        var age : Int? = null,
        var address : String? = null){

            fun name(name : String) =  apply { this.name = name }
            fun age(age : Int)      =  apply { this.age = age }
            fun address(address : String) =  apply { this.address = address }
            fun build() = UserB(name, age, address)
    }

}