package com.cibertec.demo.service;

import com.cibertec.demo.modelo.Carga;
import com.cibertec.demo.modelo.CargaEstado;
import com.cibertec.demo.repository.CargaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CargaService {

    private final CargaRepository cargaRepo;


    public Optional<Carga> actualizarEstado(Long idCarga, CargaEstado nuevoEstado) {
        return cargaRepo.findById(idCarga)
                .map(carga -> {
                    carga.setEstado(nuevoEstado);
                    return cargaRepo.save(carga);
                });
    }



}
