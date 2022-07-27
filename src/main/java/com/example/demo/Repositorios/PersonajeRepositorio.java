package com.example.demo.Repositorios;

import com.example.demo.Entidades.Personaje;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PersonajeRepositorio extends JpaRepository<Personaje, String> {

    @Query("SELECT c FROM Personaje c WHERE c.id = :id")
    public Personaje buscarPorId(@Param("id") String id);

    @Query("SELECT c FROM Personaje c WHERE c.nombre LIKE %:nombre%")
    public List<Personaje> buscarPorNombre(@Param("nombre") String nombre);

    @Query("SELECT c FROM Personaje c WHERE c.edad LIKE %:edad%")
    public Personaje buscarPorEdad(@Param("edad") String edad);

    @Query("SELECT c FROM Personaje c WHERE c.peso LIKE %:peso%")
    public Personaje buscarPorPeso(@Param("peso") String peso);

    @Query("SELECT c FROM Personaje c WHERE c.historia LIKE %:historia%")
    public Personaje buscarPorHistoria(@Param("historia") String historia);

    @Query("SELECT c FROM Personaje c WHERE c.pelicula = :pelicula")
    public List<Personaje> buscarPorPelicula(@Param("pelicula") String pelicula);
}
