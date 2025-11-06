package edu.ucne.leodanny_maria_ap2_p2.data.remote

import edu.ucne.leodanny_maria_ap2_p2.data.remote.dto.GastoDto
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val gastosApi: GastosApi
) {
    suspend fun getGastos(): List<GastoDto> {
        val response = gastosApi.getGastos()
        if (response.isSuccessful) {
            return response.body() ?: emptyList()
        } else {
            throw Exception("Error: ${response.code()} ${response.message()}")
        }
    }
}