App Contains these files for the required project

MainActivity
MovieListScreen
MovieDetailScreen
MovieViewModel

RetrofitClient
MovieApiService
MovieResponse


**MainActivity** ->
defined navController
contains composable screens routes

**MovieListScreen** ->
contains a search bar composable function -> contains a lambda function to change the filtered image
movie grid -> 2 column vertical list
movie item -> rounded corner container with image and a text below

**MovieDetailScreen** ->
 contains a app bar -> with back button and name of the movie
 scafflod -> rouned corner image ; title; and details

 **RetrofitClient** ->
 a retro fit builder with oKhttp client and loggingInterceptor to get info about the api call.
movieService 
    val movieApiService: MovieApiService by lazy {
        retrofit.create(MovieApiService::class.java)
    }

**MovieApiService** ->
 an interface for service to call the api,with query params and response defined 
  interface MovieApiService {
      @GET("trending/movie/day")
      fun getTrendingMovies(
          @Query("api_key") apiKey: String,
          @Query("language") language: String = "en-US"
      ): Call<MovieResponse>
  }






 



