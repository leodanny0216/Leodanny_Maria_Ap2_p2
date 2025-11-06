package edu.ucne.leodanny_maria_ap2_p2.data.remote.dto

import com.squareup.moshi.Json

data class GastoDto(
    @Json(name = "gastoId") val gastoId: Int = 0,
    @Json(name = "fecha") val fecha: String? = null,
    @Json(name = "suplidor") val suplidor: String? = null,
    @Json(name = "ncf") val ncf: String? = null,
    @Json(name = "itbis") val itbis: Double = 0.0,
    @Json(name = "monto") val monto: Double = 0.0
)