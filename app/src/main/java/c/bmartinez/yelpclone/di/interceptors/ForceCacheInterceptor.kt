package c.bmartinez.yelpclone.di.interceptors

import android.content.Context
import c.bmartinez.yelpclone.utils.hasNetwork
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class ForceCacheInterceptor constructor(private val context: Context): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder: Request.Builder = chain.request().newBuilder()
        if(!hasNetwork(context)) {
            builder.cacheControl(CacheControl.FORCE_CACHE)
        }
        return chain.proceed(builder.build())
    }
}