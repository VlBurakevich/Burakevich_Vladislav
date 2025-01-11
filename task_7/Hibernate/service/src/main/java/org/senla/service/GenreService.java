package org.senla.service;

import org.senla.di.annotations.Autowired;
import org.senla.di.annotations.Component;
import org.senla.entity.Genre;
import org.senla.repository.GenreRepository;

import java.util.List;

@Component
public class GenreService {
    @Autowired
    private GenreRepository genreRepository;

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
