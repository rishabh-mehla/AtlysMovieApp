package com.example.atlysmovieapp

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage


@Composable
fun MovieListScreen(viewModel: MovieViewModel, navController: NavController) {
    val movies by viewModel.movies.observeAsState()

    Column {
        SearchBar(viewModel::searchMovies)
        if (movies != null) {
            MovieGrid(movies = movies!!, navController = navController)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(onSearch: (String) -> Unit) {
    var searchQuery by remember { mutableStateOf("") }

    TextField(
        value = searchQuery,
        onValueChange = {
            searchQuery = it
            onSearch(searchQuery)
        },
        placeholder = { Text("Search movies", color = Color.Gray) }, // Hint text color
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(Color.White, shape = RoundedCornerShape(8.dp)) // Background color and rounded corners
            .border(1.dp, Color.Gray, shape = RoundedCornerShape(8.dp)), // Grey outline
        leadingIcon = {
            Icon(imageVector = Icons.Default.Search, contentDescription = "Search Icon", tint = Color.Gray) // Icon color
        },
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.White, // Inside background color
            focusedIndicatorColor = Color.Transparent, // No underline when focused
            unfocusedIndicatorColor = Color.Transparent, // No underline when unfocused
            textColor = Color.Black // Text color inside the search bar
        )
    )
}


@Composable
fun MovieGrid(movies: MovieResponse, navController: NavController) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(0.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(movies.results) { movie ->
            MovieItem(movie = movie, navController = navController)
        }
    }
}

@Composable
fun MovieItem(movie: Movie?, navController: NavController) {
    Column() {
        Card(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .clickable {
                    navController.navigate("movieDetail/${movie?.id}")
                },
            shape = RoundedCornerShape(8.dp)
        ) {
            Column {
                AsyncImage(
                    model = "https://image.tmdb.org/t/p/w500${movie?.posterPath}",
                    contentDescription = movie?.title,
                    modifier = Modifier
                        .height(180.dp)
                        .width(172.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop
                )
            }
        }
        movie?.originalTitle?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}
