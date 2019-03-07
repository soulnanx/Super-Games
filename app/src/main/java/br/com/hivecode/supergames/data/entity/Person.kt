package br.com.hivecode.supergames.data.entity

import java.math.BigDecimal

class Person(
    val name: String?,
    val job : String?,
    val salary : BigDecimal?) {


    data class Builder(
        var name: String? = null,
        var job : String? = null,
        var salary : BigDecimal? = null
    ) {
        fun name(name : String?) = apply { this.name = name }
        fun job(job : String?) = apply { this.job = job }
        fun salary(salary: BigDecimal?) = apply { this.salary = salary }
        fun build() = Person(name, job, salary)
    }
}