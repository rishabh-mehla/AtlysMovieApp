package com.example.atlysmovieapp

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.await

class MovieViewModel : ViewModel() {

    private val _movies = MutableLiveData<MovieResponse?>()
    val movies: LiveData<MovieResponse?> = _movies

    private var originalMoviesList: List<Movie> = emptyList()

    init {
        loadMovies()
    }

    private fun loadMovies() {
        viewModelScope.launch {
            val result = fetchMovies("9c31dbcdf6972c7f5c9fc41945aa4685")
            result.onSuccess { movieResponse ->
                Log.d("API_SUCCESS", "Movies: $movieResponse")
                _movies.value = movieResponse
                originalMoviesList = movieResponse.results
            }.onFailure { exception ->
                _movies.value = null
                Log.e("API_ERROR", "Error: ${exception.message}")
            }
        }
    }

    fun searchMovies(query: String) {
        val filteredMovies = if (query.isEmpty()) {
            originalMoviesList
        } else {
            originalMoviesList.filter { movie ->
                movie.title.contains(query, ignoreCase = true) ||
                        movie.originalTitle.contains(query, ignoreCase = true)
            }
        }
        _movies.value = _movies.value?.copy(results = filteredMovies)
    }

    private suspend fun fetchMovies(apiKey: String): Result<MovieResponse> {
        return try {
            val response =
                movieApiService.getTrendingMovies(apiKey = apiKey, language = "en-US").await()
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
