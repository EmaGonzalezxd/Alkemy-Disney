package com.example.demo.Repositorios;

import com.example.demo.Entidades.Foto;
import org.springframework.data.jpa.repository.JpaRepository;


public interface FotoRepositorio extends JpaRepository<Foto, String>{ 
    
}
