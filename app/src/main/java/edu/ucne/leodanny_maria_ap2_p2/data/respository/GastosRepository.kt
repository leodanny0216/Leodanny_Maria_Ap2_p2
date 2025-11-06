package edu.ucne.leodanny_maria_ap2_p2.data.respository

import edu.ucne.leodanny_maria_ap2_p2.data.remote.GastosApi
import edu.ucne.leodanny_maria_ap2_p2.data.remote.Resource
import edu.ucne.leodanny_maria_ap2_p2.data.remote.dto.GastoDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GastosRepository @Inject constructor(
    private val api: GastosApi
) {
    fun getGastos(): Flow<Resource<List<GastoDto>>> = flow {
        emit(Resource.Loading())
        try {
            val response = api.getGastos()
            if (response.isSuccessful) {
                val gastos = response.body() ?: emptyList()
                emit(Resource.Success(gastos))
            } else {
                emit(Resource.Error("Error del servidor: ${response.code()} ${response.message()}"))
            }
        } catch (e: IOException) {
            emit(Resource.Error("Error de conexión: Verifica tu internet"))
        } catch (e: HttpException) {
            emit(Resource.Error("Error HTTP: ${e.message}"))
        } catch (e: Exception) {
            emit(Resource.Error("Error: ${e.message}"))
        }
    }

    suspend fun getGasto(id: Int): GastoDto? {
        return try {
            val response = api.getGasto(id)
            if (response.isSuccessful) response.body() else null
        } catch (e: Exception) {
            null
        }
    }

    suspend fun createGasto(gasto: GastoDto): Boolean {
        return try {
            val response = api.createGasto(gasto)
            response.isSuccessful
        } catch (e: Exception) {
            false
        }
    }

    suspend fun updateGasto(id: Int, gasto: GastoDto): Boolean {
        return try {
            val response = api.updateGasto(id, gasto)
            response.isSuccessful
        } catch (e: Exception) {
            false
        }
    }

    suspend fun deleteGasto(id: Int): Boolean {
        return try {
            val response = api.deleteGasto(id)
            response.isSuccessful
        } catch (e: Exception) {
            false
        }
    }
}