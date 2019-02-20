package br.com.hivecode.supergames.data.entity

data class Game(val name: String?,
                val popularity: Number?,
                val _id: Number?,
                val giantbomb_id: Number?,
                val logo: Logo?,
                val localized_name:
                String?, val locale: String?)

data class Logo (val large: String?,
                 val medium: String?,
                 val small: String?,
                 val template: String?)