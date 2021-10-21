package c.bmartinez.yelpclone.di.interceptors

import c.bmartinez.yelpclone.utils.YelpConstants
import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyAuthInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val requestBuilder = originalRequest.newBuilder()
            .header("Authorization", "Bearer ${YelpConstants.API_KEY}")
        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}