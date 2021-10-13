package c.bmartinez.yelpclone.network.interceptors

import c.bmartinez.yelpclone.utils.api_key
import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyAuthInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val requestBuilder = originalRequest.newBuilder()
            .header("Authorization", "Bearer $api_key")
        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}