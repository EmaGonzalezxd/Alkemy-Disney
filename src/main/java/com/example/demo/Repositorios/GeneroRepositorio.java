package com.example.demo.Repositorios;

import com.example.demo.Entidades.Genero;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GeneroRepositorio extends JpaRepository<Genero, String> {

    @Query("SELECT c FROM Genero c WHERE c.nombre LIKE %:nombre%")
    public List<Genero> buscarPorGenero(@Param("nombre") String nombre);
}
