package c.bmartinez.yelpclone.domain.use_case.get_search_businesses

import c.bmartinez.yelpclone.data.remote.dto.business_search.toBusinessSearch
import c.bmartinez.yelpclone.domain.model.business_search.BusinessSearch
import c.bmartinez.yelpclone.domain.repository.BusinessRepository
import c.bmartinez.yelpclone.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetSearchBusinessesUseCase @Inject constructor(
    private val businessRepository: BusinessRepository
) {
    operator fun invoke(
        searchTerm: String,
        latitude: Double,
        longitude: Double
    ): Flow<Resource<BusinessSearch>> = flow {
        try {
            emit(Resource.Loading())
            val businesses = businessRepository.getSearchBusinesses(
                searchTerm,
                latitude,
                longitude
            ).toBusinessSearch()
            emit(Resource.Success(businesses))
        } catch(e: HttpException) {
            emit(Resource.Error<BusinessSearch>(e.localizedMessage ?: "An unexpected error occurred."))
        } catch (e: IOException) { //Catches if there's an error with connecting with the DB/Remote API
            emit(Resource.Error<BusinessSearch>("Couldn't reach server. Check your internet connection."))
        }
    }
}