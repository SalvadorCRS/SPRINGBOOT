package com.eyykyoo.movies.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eyykyoo.movies.models.Movie;
import com.eyykyoo.movies.repositories.MovieRepository;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    @Autowired
    private MovieRepository movieRepository;

    @CrossOrigin
    @GetMapping
    public List<Movie> getAllMovies() {
        return  movieRepository.findAll();
    
    }

    @CrossOrigin
    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovieById (@PathVariable long id ) {
       Optional<Movie> movie = movieRepository.findById(id);
       return movie.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

    }

    @CrossOrigin
    @PostMapping
    public ResponseEntity<Movie> createMovie(@RequestBody Movie movie) {
        Movie savedMovie = movieRepository.save(movie);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedMovie);
    }

    @CrossOrigin
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id){
        if(!movieRepository.existsById(id)){
            return ResponseEntity.notFound().build();
        }

        movieRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @CrossOrigin
    @PutMapping("/{id}")
    public ResponseEntity<Movie> UpdateMovie(@PathVariable long id, @RequestBody Movie updatedMovie){
        if (!movieRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        updatedMovie.setId(id);
        Movie savedMovie = movieRepository.save(updatedMovie);
        return ResponseEntity.ok(savedMovie);
    }
    @CrossOrigin
    @PostMapping("/vote/{id}/{rating}")
    public ResponseEntity<Movie> rateMovie (@PathVariable Long id, @PathVariable double rating) {
        if (!movieRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

       Optional<Movie> optional = movieRepository.findById(id);
        Movie movie = optional.get();

        double newRating = ((movie.getVotes() + movie.getRating()) + movie.getRating()) / (movie.getVotes() + 1);
        movie.setVotes(movie.getVotes() + 1);
        movie.setRating(newRating);
        Movie savedMovie = movieRepository.save(movie);
        return ResponseEntity.ok(savedMovie);

    }       
}   