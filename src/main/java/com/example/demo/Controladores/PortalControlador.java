package com.example.demo.Controladores;

import com.example.demo.Entidades.Genero;
import com.example.demo.Entidades.Pelicula;
import com.example.demo.Entidades.Personaje;
import com.example.demo.Entidades.Usuario;
import com.example.demo.ErrorServicio.ErrorServicio;
import com.example.demo.Servicios.GeneroServicio;
import com.example.demo.Servicios.PeliculaServicio;
import com.example.demo.Servicios.PersonajesServicio;
import com.example.demo.Servicios.UsuarioServicio;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("")
public class PortalControlador {

    @Autowired
    private GeneroServicio generoServicio;

    @Autowired
    private UsuarioServicio usuarioServicio;

    @Autowired
    private PeliculaServicio peliculaServicio;

    @Autowired
    private PersonajesServicio personajesServicio;

    @GetMapping("/")
    public String inedx() {
        return "inicio.html";
    }

    @GetMapping("/index")
    public String index(ModelMap modelo) {

        List<Pelicula> peliculas = peliculaServicio.mostrarTodos();
        modelo.put("peli", peliculas);

        return "index.html";
    }

    @GetMapping("/buscador")
    public String buscador(ModelMap model, @RequestParam String nombre) {
        try {
            Personaje personaje = (Personaje) personajesServicio.buscarPorNombre(nombre);

            return "redirect:/buscarPersonaje/" + personaje.getId();
        } catch (Exception e) {
            e.printStackTrace();
            model.put("error", e.getMessage());
            return "redirect:/index";
        }
    }

    //REGISTRO
    @PostMapping("/registrar")
    public String registrar(ModelMap modelo, @RequestParam MultipartFile foto, @RequestParam String nombre, @RequestParam String apellido, @RequestParam String email, @RequestParam String contrasenia) {

        try {

            usuarioServicio.crear(foto, nombre, apellido, email, contrasenia);

            modelo.put("exito", "Felicidades! Usuario registrado satisfactoriamente.");

            return "inicio.html";

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
            modelo.put("error", ("No se pudo crear el usuario"));
            return "inicio.html";
        }
    }

    @GetMapping("/pelicula/{id}")
    public String pelicula(ModelMap modelo, @PathVariable String id) throws ErrorServicio, Exception {
        Pelicula pelicula = peliculaServicio.buscarPorId(id);
        List<Personaje> personaje = personajesServicio.buscarPorPelicula(id);
        modelo.addAttribute("personaje", personaje);
        modelo.addAttribute("pelicula", pelicula);
        return "pelicula.html";
    }

    //GET DE LA VISTA AGREGAR PELICULA//
    @GetMapping("/agregarPeli")
    public String agregarPeli(ModelMap modelo) {
        List<Pelicula> pelicula = peliculaServicio.mostrarTodos();
        modelo.addAttribute("personaje", personajesServicio.mostrarTodos());
        modelo.put("pelicula", pelicula);
        return "agregarPeli.html";

    }

    //POST FORMULARIO PARA AGREGAR PELICULA//
    @PostMapping("/crearPeli")
    public String crearPeli(ModelMap modelo, ModelMap modelo2, @RequestParam MultipartFile foto, @RequestParam String titulo, @RequestParam String fecha, @RequestParam String calificacion,
            @RequestParam String genero, @RequestParam Personaje personaje) {

        try {
            peliculaServicio.crear(foto, titulo, fecha, calificacion, genero, personaje);

            modelo.addAttribute("personaje", personaje);
            modelo.put("titulo", "Felicidades!");
            modelo.put("descripcion", "Persistida la pelicula con exito.");

            return "agregarPeli.html";

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
            modelo.put("error", ex.getMessage());
            return "agregarPeli.html";
        }

    }

    //GET DE LA VISTA AGREGAR GENERO//
    @GetMapping("/agregarGenero")
    public String agregarGenero(ModelMap modelo) {
        List<Genero> genero = generoServicio.mostrarTodos();
        modelo.put("genero", genero);
        return "agregarGenero.html";

    }

    //POST FORMULARIO PARA AGREGAR GENERO//
    @PostMapping("/crearGenero")
    public String crearGenero(ModelMap modelo, ModelMap modelo2, @RequestParam MultipartFile foto, @RequestParam String nombre) {

        try {

            generoServicio.crear(foto, nombre);

            modelo.put("titulo", "Felicidades!");
            modelo.put("descripcion", "Persistida el genero con exito.");

            return "agregarGenero.html";

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
            modelo.put("error", ex.getMessage());
            return "agregarGenero.html";
        }

    }

    //GET DE LA VISTA AGREGAR PERSONAJE//
    @GetMapping("/agregarPersoanje")
    public String agregarPersonaje(ModelMap modelo) {
        List<Personaje> personaje = personajesServicio.mostrarTodos();
        modelo.put("personaje", personaje);
        return "agregarPersonaje.html";

    }

    //POST FORMULARIO PARA AGREGAR PERSONAJE//
    @PostMapping("/crearPersonaje")
    public String crearPersonaje(ModelMap modelo, ModelMap modelo2, @RequestParam MultipartFile foto, @RequestParam String nombre, @RequestParam String edad,
            @RequestParam String peso, @RequestParam String historia) {

        try {

            personajesServicio.crear(foto, nombre, edad, peso, historia);

            modelo.put("titulo", "Felicidades!");
            modelo.put("descripcion", "Persistida la pelicula con exito.");

            return "agregarPersonaje.html";

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
            modelo.put("error", ex.getMessage());
            return "agregarPersonaje.html";
        }
    }

    //LUEGO SEGUIR CON ESTO
    @PostMapping("/modificarPersonaje")
    public String modificar(ModelMap modelo, @RequestParam String nombre, @RequestParam String edad,
            @RequestParam String peso, @RequestParam String historia) {
        try {

            personajesServicio.modificar(nombre, edad, peso, historia);
            modelo.put("exito", "Personaje modificado con éxito");

        } catch (Exception e) {
            modelo.put("error", "Error al modificar");
        }

        return "modificar";
    }

//    @GetMapping("/modificar")
//    public String formularioModificar(ModelMap modelo) {
//        modelo.addAttribute("usuarios", usuarioServicio.mostrarTodos());
//        modelo.addAttribute("direcciones", direccionServicio.mostrarTodos());
//        return "modificar";
//    }
    //GET DE LA VISTA MI PERFIL
    @GetMapping("/miperfil")
    public String miPerfil(HttpSession session, ModelMap modelo) {
        modelo.addAttribute("usuario", session.getAttribute("usuariosession"));
        return "miperfil.html";
    }

    //POST DE FORM MODIFICAR USUARIO
    @PostMapping("/editarUsuario")
    public String editarUsuario(HttpSession session, ModelMap modelo, @RequestParam MultipartFile foto, @RequestParam String nombre, @RequestParam String apellido,
            @RequestParam String email, @RequestParam String contrasenia) {

        try {
            Usuario usuario = (Usuario) session.getAttribute("usuariosession");
            usuarioServicio.modificar(usuario.getId(), foto, nombre, apellido, email, contrasenia);
            modelo.put("exito", "!Usuario actualizado¡");
            return "miperfil.html";
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
            return "miperfil.html";
        }
    }

    //GET DE LA VISTA MODIFICAR
    @GetMapping("/modificar")
    public String modificar(HttpSession session, ModelMap modelo) {
        Usuario login = (Usuario) session.getAttribute("usuariosession");
        if (login == null) {
            return "modificar.html";
        }
        List<Personaje> personaje = personajesServicio.mostrarTodos();
        List<Pelicula> pelicula = peliculaServicio.mostrarTodos();
        List<Genero> genero = generoServicio.mostrarTodos();
        modelo.put("personaje", personaje);
        modelo.put("pelicula", pelicula);
        modelo.put("genero", genero);
        return "modificar.html";
    }

    //GET DE ELIMINAR PELIS
    @GetMapping("/eliminarPelicula/{id}")
    public String eliminarPelicula(@PathVariable String id) {
        try {
            peliculaServicio.eliminar(id);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
        return "redirect:/modificar";
    }

    //GET DE ELIMINAR PERSONAJES
    @GetMapping("/eliminarPersonaje/{id}")
    public String eliminarPersonaje(@PathVariable String id) {
        try {
            personajesServicio.eliminar(id);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
        return "redirect:/modificar";
    }

    //GET DE ELIMINAR GENEROS
    @GetMapping("/eliminarGenero/{id}")
    public String eliminarGenero(@PathVariable String id) {
        try {
            generoServicio.eliminar(id);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
        return "redirect:/modificar";
    }

    //GET DE LA VISTA BUSCAR PERSONAJE
    @GetMapping("/buscarPersonaje")
    public String buscarPersonaje(ModelMap modelo, @RequestParam(value = "query", required = false) String nombre) throws Exception {
        try {

            List<Personaje> personaje = this.personajesServicio.buscarPorNombre(nombre);
            modelo.addAttribute("personaje", personaje);
            return "buscarPersonaje";
            
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
        return "buscarPersonaje";
    }
}
