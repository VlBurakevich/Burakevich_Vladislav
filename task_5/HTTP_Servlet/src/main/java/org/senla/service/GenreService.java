package org.senla.service;

import org.senla.dao.GenreDao;
import org.senla.di.annotations.Autowired;
import org.senla.di.annotations.Component;
import org.senla.entity.Genre;

import java.util.List;

@Component
public class GenreService {
    @Autowired
    private GenreDao genreDao;

    public Genre getById(Long id) {
        return genreDao.getById(id);
    }

    public List<Genre> getAll() {
        return genreDao.getAll();
    }

    public void insert(Genre genre) {
        genreDao.insert(genre);
    }

    public void update(Genre genre) {
        genreDao.update(genre);
    }

    public void delete(Long id) {
        genreDao.delete(id);
    }
}
