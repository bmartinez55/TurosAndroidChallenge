package c.bmartinez.yelpclone.network.interceptors

import android.content.Context
import c.bmartinez.yelpclone.utils.hasNetwork
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class OfflineCacheInterceptor(val context: Context): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request: Request = chain.request()

        if(!hasNetwork(context = context)) {
            val maxStale = 60 * 60 * 24 * 30 // Offline cache available for 30 days
            request = request.newBuilder()
                .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                .removeHeader("Pragma")
                .build()
        }
        return chain.proceed(request)
    }
}