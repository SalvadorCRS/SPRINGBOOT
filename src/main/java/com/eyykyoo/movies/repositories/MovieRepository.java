package com.eyykyoo.movies.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.eyykyoo.movies.models.Movie;

public interface MovieRepository  extends JpaRepository<Movie, Long> {

}
