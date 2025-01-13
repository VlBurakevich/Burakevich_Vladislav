package org.senla.service;

import lombok.AllArgsConstructor;
import org.senla.entity.Genre;
import org.senla.repository.GenreRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GenreService {
    private final GenreRepository genreRepository;

    public Genre getById(Long id) {
        return genreRepository.getById(id);
    }

    public List<Genre> getAll() {
        return genreRepository.fetchLimitedRandom(20);
    }

    public void insert(Genre genre) {
        genreRepository.save(genre);
    }

    public void update(Genre genre) {
        genreRepository.update(genre);
    }

    public void delete(Long id) {
        genreRepository.deleteById(id);
    }
}
