package com.cibertec.demo.service;

import com.cibertec.demo.modelo.Cita;
import com.cibertec.demo.modelo.DetalleCita;
import com.cibertec.demo.repository.CitaRepository;
import com.cibertec.demo.repository.DetalleCitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CitaService {

    @Autowired
    private CitaRepository citaRepo;

    @Autowired
    private DetalleCitaRepository detalleRepo;

    public List<Cita> listarCitas() {
        return citaRepo.findAll();
    }

    public List<DetalleCita> listarDetalles() {
        return detalleRepo.findAll();
    }

    public Cita guardarCita(Cita cita) {
        return citaRepo.save(cita);
    }

    public DetalleCita guardarDetalle(DetalleCita detalle) {
        return detalleRepo.save(detalle);
    }
}
