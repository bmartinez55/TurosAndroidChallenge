package c.bmartinez.yelpclone.domain.use_case.get_business_details

import c.bmartinez.yelpclone.data.remote.dto.business_details.toBusinessDetails
import c.bmartinez.yelpclone.data.remote.dto.business_search.toBusinessSearch
import c.bmartinez.yelpclone.domain.model.business_detail.BusinessDetails
import c.bmartinez.yelpclone.domain.model.business_search.BusinessSearch
import c.bmartinez.yelpclone.domain.repository.BusinessRepository
import c.bmartinez.yelpclone.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetBusinessDetailsUseCase@Inject constructor(
    private val businessRepository: BusinessRepository
) {
    operator fun invoke(businessId: String): Flow<Resource<BusinessDetails>> = flow {
        try {
            emit(Resource.Loading())
            val business = businessRepository.getBusinessDetails(businessId).toBusinessDetails()
            emit(Resource.Success(business))
        } catch(e: HttpException) { //Catches if response is not the same to http error codes
            emit(Resource.Error<BusinessDetails>(e.localizedMessage ?: "An unexpected error occurred."))
        } catch (e: IOException) { //Catches if there's an error with connecting with the DB/Remote API
            emit(Resource.Error<BusinessDetails>("Couldn't reach server. Check your internet connection."))
        }
    }
}