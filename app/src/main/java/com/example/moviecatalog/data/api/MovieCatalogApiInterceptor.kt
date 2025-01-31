import com.example.moviecatalog.data.preferences.AuthPreferences
import okhttp3.Interceptor
import okhttp3.Response

class MovieCatalogApiInterceptor(private val authPreferences: AuthPreferences) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val token = authPreferences.getToken()

        return if (token != null) {
            val modifiedRequest = originalRequest.newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()

            chain.proceed(modifiedRequest)
        } else {
            chain.proceed(originalRequest)
        }
    }
}
