package com.example.lab_week_13

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.example.lab_week_13.model.Movie
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import androidx.lifecycle.Lifecycle
import androidx.databinding.DataBindingUtil
import com.example.lab_week_13.databinding.ActivityMainBinding

class MainActivity : ComponentActivity(), MovieAdapter.MovieClickListener {

    private val movieAdapter = MovieAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 🔹 Step 9: ganti setContentView dengan DataBinding
        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)

        // Masih sama: set adapter ke RecyclerView (sekarang via binding)
        val recyclerView: RecyclerView = binding.movieList
        recyclerView.adapter = movieAdapter

        val movieRepository = (application as MovieApplication).movieRepository

        val movieViewModel = ViewModelProvider(
            this,
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return MovieViewModel(movieRepository) as T
                }
            }
        )[MovieViewModel::class.java]

        // 🔹 Step 10: bind viewModel + lifecycleOwner ke layout
        binding.viewModel = movieViewModel
        binding.lifecycleOwner = this

        // 🔹 Step 11: kosongkan isi lifecycleScope.launch (struktur tetap ada)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                // Isi lama (collectLatest dsb) dihapus sesuai instruksi modul.
                // Sekarang update list ditangani via Data Binding + BindingAdapter.
            }
        }
    }

    override fun onMovieClick(movie: Movie) {
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra(DetailsActivity.EXTRA_MOVIE_ID, movie.id)
        startActivity(intent)
    }

}
