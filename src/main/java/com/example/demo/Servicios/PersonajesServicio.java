package com.example.demo.Servicios;

import com.example.demo.Entidades.Foto;
import com.example.demo.Entidades.Personaje;
import com.example.demo.ErrorServicio.ErrorServicio;
import com.example.demo.Repositorios.PersonajeRepositorio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PersonajesServicio {

    @Autowired
    private PersonajeRepositorio personajeRepositorio;

    @Autowired
    private FotoServicio FotoServicio;

    @Transactional(propagation = Propagation.REQUIRED)
    public void crear(MultipartFile archivo, String nombre, String edad, String peso, String historia) throws ErrorServicio, Exception {

        //Validacion        
        Validar(nombre, edad, peso, historia);

        //Creo una instancia del personaje
        Personaje personaje = new Personaje();

        //Seteo valores
        personaje.setNombre(nombre);
        personaje.setEdad(edad);
        personaje.setPeso(peso);
        personaje.setHistoria(historia);

        //Guardo la foto
        Foto foto = FotoServicio.guardar(archivo);
        personaje.setFoto(foto);

        //guardo el personaje
        personajeRepositorio.save(personaje);
    }

    @Transactional
    public Personaje buscarPorId(String id) throws ErrorServicio {
        Optional<Personaje> respuesta = personajeRepositorio.findById(id);

        if (respuesta.isPresent()) {
            Personaje personaje = respuesta.get();
            return personaje;
        } else {
            throw new ErrorServicio("No se encontro el personaje que buscaba.");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void modificar(MultipartFile archivo, String id, String nombre, String edad, String peso, String historia) throws ErrorServicio, Exception {

        
        //Validacion
        Validar(nombre, edad, peso, historia);

        Optional<Personaje> respuesta = personajeRepositorio.findById(id);
        if (respuesta.isPresent()) {

            //Creo una instancia del personaje
            Personaje personaje = new Personaje();

            //Seteo valores
            personaje.setNombre(nombre);
            personaje.setEdad(edad);
            personaje.setPeso(peso);
            personaje.setHistoria(historia);

            String idFoto = null;
            if (personaje.getFoto() != null) {
                idFoto = personaje.getFoto().getId();
            }
            //Actualizo la foto
            Foto foto = FotoServicio.actualizar(idFoto, archivo);
            personaje.setFoto(foto);

            //Actulizo el personaje
            personajeRepositorio.save(personaje);

        } else {
            throw new Exception("No se encontro el personaje");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void eliminar(String id) {
        Optional<Personaje> optional = personajeRepositorio.findById(id);
        if (optional.isPresent()) {
            personajeRepositorio.delete(optional.get());
        }
    }

    //MOSTRAR TODO
    @Transactional
    public List<Personaje> mostrarTodos() {
        return personajeRepositorio.findAll();
    }

    //BUSCAR POR NOMBRE
    @Transactional
    public List<Personaje> buscarPorNombre(String nombre) throws Exception {

        try {
            List<Personaje> personaje = personajeRepositorio.buscarPorNombre(nombre);
            return personaje;
        } catch (Exception e) {
            throw new Exception("Este personaje no existe");
        }
    }

    //BUSCAR POR EDAD
    @Transactional
    public Personaje buscarPorEdad(String edad) throws Exception {

        try {
            Personaje personaje = personajeRepositorio.buscarPorEdad(edad);
            return personaje;
        } catch (Exception e) {
            throw new Exception("Este personaje no existe");
        }
    }

    //BUSCAR POR PESO
    @Transactional
    public Personaje buscarPorPeso(String peso) throws Exception {

        try {
            Personaje personaje = personajeRepositorio.buscarPorPeso(peso);
            return personaje;
        } catch (Exception e) {
            throw new Exception("Este personaje no existe");
        }
    }

    //BUSCAR POR HISTORIA
    @Transactional
    public Personaje buscarPorHistoria(String historia) throws Exception {

        try {
            Personaje personaje = personajeRepositorio.buscarPorHistoria(historia);
            return personaje;
        } catch (Exception e) {
            throw new Exception("Este personaje no existe");
        }
    }
    
    //BUSCAR POR PELICULA
    @Transactional
    public List<Personaje> buscarPorPelicula(String pelicula) throws Exception {

        try {
            List<Personaje> personaje = personajeRepositorio.buscarPorPelicula(pelicula);
            return personaje;
        } catch (Exception e) {
            throw new Exception("Este personaje no existe");
        }
    }

    //VALIDACIONES
    public void Validar(String nombre, String edad, String peso, String historia) throws ErrorServicio {
        if (nombre == null || nombre.isEmpty()) {
            throw new ErrorServicio("El nombre no puede ser nulo");
        }
        if (edad == null || edad.isEmpty()) {
            throw new ErrorServicio("La edad no puede ser nulo");
        }
        if (peso == null || peso.isEmpty()) {
            throw new ErrorServicio("El peso no puede ser nulo");
        }
        if (historia == null || historia.isEmpty()) {
            throw new ErrorServicio("La historia no puede ser nulo");
        }
    }

    public void modificar(String nombre, String edad, String peso, String historia) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

 
}
