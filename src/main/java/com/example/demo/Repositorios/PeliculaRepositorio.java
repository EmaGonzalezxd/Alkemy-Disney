package com.example.demo.Repositorios;

import com.example.demo.Entidades.Pelicula;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PeliculaRepositorio extends JpaRepository<Pelicula, String> {

    @Query("SELECT c FROM Pelicula c WHERE c.genero LIKE %:genero%")
    public List<Pelicula> buscarPorGenero(@Param("genero") String genero);

    @Query("SELECT c FROM Pelicula c WHERE c.titulo LIKE %:titulo%")
    public Pelicula buscarPorTitulo(@Param("titulo") String titulo);

    @Query("SELECT c FROM Pelicula c WHERE c.fecha LIKE %:fecha%")
    public Pelicula buscarPorFecha(@Param("fecha") String fecha);
}
