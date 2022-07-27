package com.example.demo.Servicios;

import com.example.demo.Entidades.Foto;
import com.example.demo.Entidades.Genero;
import com.example.demo.ErrorServicio.ErrorServicio;
import com.example.demo.Repositorios.GeneroRepositorio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class GeneroServicio {

    @Autowired
    private GeneroRepositorio generoRepositorio;

    @Autowired
    private FotoServicio fotoServicio;

    @Transactional(propagation = Propagation.REQUIRED)
    public void crear(MultipartFile archivo, String nombre) throws Exception {

        //Validacion   
        Validar(nombre);

        //Creo una instancia del genero
        Genero genero = new Genero();

        //Seteo valores
        genero.setNombre(nombre);

        //Guardo la foto
        Foto foto = fotoServicio.guardar(archivo);
        genero.setFoto(foto);

        //guardo el genero
        generoRepositorio.save(genero);
    }

    @Transactional
    public Genero buscarPorId(String id) throws ErrorServicio {
        Optional<Genero> respuesta = generoRepositorio.findById(id);

        if (respuesta.isPresent()) {
            Genero genero = respuesta.get();
            return genero;
        } else {
            throw new ErrorServicio("No se encontro el genero deseado");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void modificar(MultipartFile archivo, String id, String nombre) throws Exception {

        //VALIDO
        Validar(nombre);

        //BUSCO EL GENERO POR SU ID
        Optional<Genero> respuesta = generoRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Genero genero = respuesta.get();
            genero.setNombre(nombre);

            //VALIDO QUE LA FOTO NO SEA NULA
            String idFoto = null;
            if (genero.getFoto() != null) {
                idFoto = genero.getFoto().getId();
            }

            //GUARDO LA FOTO
            Foto foto = fotoServicio.actualizar(idFoto, archivo);
            genero.setFoto(foto);

            //GUARDO LOS CAMBIOS DEL GENERO
            generoRepositorio.save(genero);
        } else {
            throw new Exception("No se encontro el genero");
        }
    }

    //ELIMINAR GENERO
    @Transactional(propagation = Propagation.REQUIRED)
    public void eliminar(String id) {
        Optional<Genero> optional = generoRepositorio.findById(id);
        if (optional.isPresent()) {
            generoRepositorio.delete(optional.get());
        }
    }

    //MOSTRAR TODOS LOS GENEROS
    @Transactional
    public List<Genero> mostrarTodos() {
        return generoRepositorio.findAll();
    }
    
    //BUSCAR POR NOMBRE DEL GENERO
    @Transactional
    public List<Genero> buscarPorGenero(String nombre) throws Exception {

        try {
            List<Genero> genero = generoRepositorio.buscarPorGenero(nombre);
            return genero;
        } catch (Exception e) {
            throw new Exception("Este genero no existe");
        }
    }

    //VALIDACION
    public void Validar(String nombre) throws ErrorServicio {
        if (nombre == null || nombre.isEmpty()) {
            throw new ErrorServicio("El nombre no puede ser nulo");
        }
    }
}
