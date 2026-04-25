package com.cibertec.demo.service;

import com.cibertec.demo.modelo.Cita;
import com.cibertec.demo.modelo.DetalleCita;
import com.cibertec.demo.repository.CitaRepository;
import com.cibertec.demo.repository.DetalleCitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CitaService {

    @Autowired
    private CitaRepository citaRepo;

    @Autowired
    private DetalleCitaRepository detalleRepo;

    public Page<Cita> listarCitas(Pageable pageable) {
        return citaRepo.findAll(pageable);
    }

    public Page<DetalleCita> listarDetalles(Pageable pageable) {
        return detalleRepo.findAll(pageable);
    }

    public Cita guardarCita(Cita cita) {
        Cita nuevaCita = citaRepo.save(cita);
        //  Si la cita tiene detalles, guardarlos también
        if (cita.getDetalles() != null) {
            for (DetalleCita detalle : cita.getDetalles()) {
                detalle.setCita(nuevaCita); //  Asociar el detalle con la cita recién creada
                detalleRepo.save(detalle);
            }
        }

    }

    public DetalleCita guardarDetalle(DetalleCita detalle) {
        return detalleRepo.save(detalle);
    }

    public DetalleCita actualizarEstado(Long id, DetalleCita.EstadoDetalle nuevoEstado) {
        return detalleRepo.findById(id)
                .map(d -> {
                    d.setEstado(nuevoEstado);
                    return detalleRepo.save(d);
                })
                .orElseThrow(() -> new RuntimeException("Detalle no encontrado"));
    }

}
