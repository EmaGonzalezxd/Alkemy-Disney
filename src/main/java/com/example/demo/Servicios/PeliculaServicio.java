package com.example.demo.Servicios;

import com.example.demo.Entidades.Foto;
import com.example.demo.Entidades.Pelicula;
import com.example.demo.Entidades.Personaje;
import com.example.demo.ErrorServicio.ErrorServicio;
import com.example.demo.Repositorios.PeliculaRepositorio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PeliculaServicio {

    @Autowired
    private PeliculaRepositorio peliculaRepositorio;

    @Autowired
    private FotoServicio fotoServicio;

    @Transactional(propagation = Propagation.REQUIRED)
    public void crear(MultipartFile archivo, String titulo, String fecha, String calificacion, String genero, Personaje personaje) throws Exception {

        //VALIDACION
        Validar(titulo, fecha, calificacion, genero, personaje);

        //CREO UNA INSTANCIA DE LA PELICULA
        Pelicula pelicula = new Pelicula();

        //SETEO LOS VALORES
        pelicula.setTitulo(titulo);
        pelicula.setFecha(fecha);
        pelicula.setCalificacion(calificacion);
        pelicula.setPersonaje(personaje);

        //GUARDO LA FOTO DE LA PELICULA
        Foto foto = fotoServicio.guardar(archivo);
        pelicula.setFoto(foto);

        //GUARDO LA PELICULA
        peliculaRepositorio.save(pelicula);
    }

    @Transactional
    public Pelicula buscarPorId(String id) throws ErrorServicio {
        Optional<Pelicula> respuesta = peliculaRepositorio.findById(id);

        if (respuesta.isPresent()) {
            Pelicula pelicula = respuesta.get();
            return pelicula;
        } else {
            throw new ErrorServicio("No se encontro la pelicula deseada");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void modificar(MultipartFile archivo, String id, String titulo, String fecha, String calificacion) throws Exception {

        Optional<Pelicula> respuesta = peliculaRepositorio.findById(id);
        if (respuesta.isPresent()) {

            Pelicula pelicula = respuesta.get();

            //LE SETEO LOS NUEVOS VALORES
            pelicula.setTitulo(titulo);
            pelicula.setFecha(fecha);
            pelicula.setCalificacion(calificacion);

            String idFoto = null;
            if (pelicula.getFoto() != null) {
                idFoto = pelicula.getFoto().getId();
            }

            //GUARDO LA NUEVA FOTO
            Foto foto = fotoServicio.actualizar(idFoto, archivo);
            pelicula.setFoto(foto);

            //GUARDO LOS CAMBIOS
            peliculaRepositorio.save(pelicula);
        } else {
            throw new Exception("No se encontro la pelicula");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void eliminar(String id) {
        Optional<Pelicula> optional = peliculaRepositorio.findById(id);
        if (optional.isPresent()) {
            peliculaRepositorio.delete(optional.get());
        }
    }

    //MOSTRAR PELICULAS POR FILTROS
    @Transactional
    public List<Pelicula> mostrarTodos() {
        return peliculaRepositorio.findAll();
    }

    @Transactional
    public Pelicula buscarPorTitulo(String titulo) throws Exception {

        try {
            Pelicula pelicula = peliculaRepositorio.buscarPorTitulo(titulo);
            return pelicula;
        } catch (Exception e) {
            throw new Exception("Esta pelicula no existe");
        }
    }

    @Transactional
    public List<Pelicula> buscarPorGenero(String genero) throws Exception {

        try {
            List<Pelicula> pelicula = peliculaRepositorio.buscarPorGenero(genero);
            return pelicula;
        } catch (Exception e) {
            throw new Exception("Esta pelicula no existe");
        }
    }

    @Transactional
    public Pelicula buscarPorFecha(String fecha) throws Exception {

        try {
            Pelicula pelicula = peliculaRepositorio.buscarPorFecha(fecha);
            return pelicula;
        } catch (Exception e) {
            throw new Exception("Esta pelicula no existe");
        }
    }

    //VALIDACIONES
    public void Validar(String titulo, String fecha, String calificacion, String genero, Personaje personaje) throws Exception {

        if (titulo == null || titulo.isEmpty()) {
            throw new Exception("Coloque el titulo de la pelicula");
        }
        if (fecha == null || fecha.isEmpty()) {
            throw new Exception("Coloque la fecha decreación de la pelicula");
        }
        if (calificacion == null) {
            throw new ErrorServicio("La calificacion no puede ser nulo");
        }
        if (genero == null) {
            throw new ErrorServicio("El genero no puede ser nulo");
        }
        if (personaje == null) {
            throw new ErrorServicio("La pelicula no puede ser nulo");
        }
    }

}
