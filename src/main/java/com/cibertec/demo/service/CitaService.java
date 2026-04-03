package com.cibertec.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

import com.cibertec.demo.modelo.Cita;
import com.cibertec.demo.repository.CitaRepository;

@Service
public class CitaService {

    @Autowired
    private CitaRepository repo;

    public List<Cita> listar() {
        return repo.findAll();
    }

    public List<Cita> buscar(String numeroContenedor) {
        return repo.buscarPorContenedor(numeroContenedor);
    }
}