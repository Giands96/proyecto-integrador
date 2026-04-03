package com.cibertec.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cibertec.demo.modelo.AgenciaAduana;
import com.cibertec.demo.modelo.BL;
import com.cibertec.demo.modelo.Chofer;
import com.cibertec.demo.modelo.Cita;
import com.cibertec.demo.modelo.Cliente;
import com.cibertec.demo.modelo.EmpresaTransporte;
import com.cibertec.demo.repository.AgenciaAduanaRepository;
import com.cibertec.demo.repository.BLRepository;
import com.cibertec.demo.repository.CarretaRepository;
import com.cibertec.demo.repository.ChoferRepository;
import com.cibertec.demo.repository.CitaRepository;
import com.cibertec.demo.repository.ClienteRepository;
import com.cibertec.demo.repository.ContenedorRepository;
import com.cibertec.demo.repository.EmpresaTransporteRepository;
import com.cibertec.demo.repository.PlacaRepository;

@RestController
@RequestMapping("/cita")
public class CitaController {

    @Autowired
    private CitaRepository citaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private AgenciaAduanaRepository agenciaAduanaRepository;

    @Autowired
    private ChoferRepository choferRepository;

    @Autowired
    private EmpresaTransporteRepository empresaTransporteRepository;

    @Autowired
    private PlacaRepository placaRepository;

    @Autowired
    private CarretaRepository carretaRepository;

    @Autowired
    private BLRepository blRepository;

    @Autowired
    private ContenedorRepository contenedorRepository;

    @PostMapping("/guardar")
    public ResponseEntity<?> guardarCita(@RequestBody CitaRequest request) {

        try {
            Cliente cliente = clienteRepository.findById(request.getRucCliente()).orElse(null);
            if (cliente == null) {
                return ResponseEntity.badRequest().body("Cliente no encontrado");
            }

            AgenciaAduana agencia = agenciaAduanaRepository.findById(request.getRucAgencia()).orElse(null);
            if (agencia == null) {
                return ResponseEntity.badRequest().body("Agencia Aduana no encontrada");
            }

            BL bl = blRepository.buscarBLPorNumeroYCliente(request.getBl(), request.getRucCliente());
            if (bl == null) {
                return ResponseEntity.badRequest().body("BL no coincide con el cliente");
            }

            boolean contenedorValido = contenedorRepository.findByNumeroBL(request.getBl())
                    .stream()
                    .anyMatch(c -> c.getNumeroContenedor().equalsIgnoreCase(request.getNumeroContenedor()));

            if (!contenedorValido) {
                return ResponseEntity.badRequest().body("Contenedor no pertenece al BL");
            }

            Chofer chofer = choferRepository.findById(request.getDniConductor()).orElse(null);
            if (chofer == null) {
                chofer = new Chofer();
                chofer.setDniChofer(request.getDniConductor());
                chofer.setNombreChofer(request.getNombreConductor());
                choferRepository.save(chofer);
            }

            EmpresaTransporte transporte = empresaTransporteRepository.findById(request.getRucTransporte()).orElse(null);
            if (transporte == null) {
                transporte = new EmpresaTransporte();
                transporte.setRucEmpresa(request.getRucTransporte());
                transporte.setNombreEmpresa(request.getNombreTransporte());
                empresaTransporteRepository.save(transporte);
            }

            if (!placaRepository.existsByNumeroPlacaIgnoreCase(request.getPlaca())) {
                return ResponseEntity.badRequest().body("Placa no existe");
            }

            if (!carretaRepository.existsByNumeroCarretaIgnoreCase(request.getCarreta())) {
                return ResponseEntity.badRequest().body("Carreta no existe");
            }

            Cita cita = new Cita();
            cita.setCliente(cliente);
            cita.setAgencia(agencia);
            cita.setChofer(chofer);
            cita.setEmpresa(transporte);
            cita.setNumeroBL(request.getBl());
            cita.setNumeroContenedor(request.getNumeroContenedor());
            cita.setNumeroPlaca(request.getPlaca());
            cita.setNumeroCarreta(request.getCarreta());
            cita.setEstado("PENDIENTE");

            citaRepository.save(cita);

            return ResponseEntity.ok("Cita guardada correctamente");

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al guardar la cita: " + e.getMessage());
        }
    }

    public static class CitaRequest {
        private String numeroContenedor;
        private String bl;
        private String placa;
        private String carreta;
        private String dniConductor;
        private String nombreConductor;
        private String rucTransporte;
        private String nombreTransporte;
        private String rucCliente;
        private String rucAgencia;
        private String fecha;

        public String getNumeroContenedor() {
            return numeroContenedor;
        }

        public void setNumeroContenedor(String numeroContenedor) {
            this.numeroContenedor = numeroContenedor;
        }

        public String getBl() {
            return bl;
        }

        public void setBl(String bl) {
            this.bl = bl;
        }

        public String getPlaca() {
            return placa;
        }

        public void setPlaca(String placa) {
            this.placa = placa;
        }

        public String getCarreta() {
            return carreta;
        }

        public void setCarreta(String carreta) {
            this.carreta = carreta;
        }

        public String getDniConductor() {
            return dniConductor;
        }

        public void setDniConductor(String dniConductor) {
            this.dniConductor = dniConductor;
        }

        public String getNombreConductor() {
            return nombreConductor;
        }

        public void setNombreConductor(String nombreConductor) {
            this.nombreConductor = nombreConductor;
        }

        public String getRucTransporte() {
            return rucTransporte;
        }

        public void setRucTransporte(String rucTransporte) {
            this.rucTransporte = rucTransporte;
        }

        public String getNombreTransporte() {
            return nombreTransporte;
        }

        public void setNombreTransporte(String nombreTransporte) {
            this.nombreTransporte = nombreTransporte;
        }

        public String getRucCliente() {
            return rucCliente;
        }

        public void setRucCliente(String rucCliente) {
            this.rucCliente = rucCliente;
        }

        public String getRucAgencia() {
            return rucAgencia;
        }

        public void setRucAgencia(String rucAgencia) {
            this.rucAgencia = rucAgencia;
        }

        public String getFecha() {
            return fecha;
        }

        public void setFecha(String fecha) {
            this.fecha = fecha;
        }
    }

    @GetMapping("/buscar")
    public List<Cita> buscarPorContenedor(@RequestParam String numeroContenedor) {
        return citaRepository.buscarPorContenedor(numeroContenedor);
    }

    @GetMapping("/detalle")
    public ResponseEntity<?> obtenerDetalle(@RequestParam Integer id) {
        Cita cita = citaRepository.findById(id).orElse(null);

        if (cita == null) {
            return ResponseEntity.badRequest().body("Cita no encontrada");
        }

        return ResponseEntity.ok(cita);
    }

    @PostMapping("/editar")
    public ResponseEntity<?> editarCita(
            @RequestParam Integer id,
            @RequestParam String placa,
            @RequestParam String carreta,
            @RequestParam String estado) {

        Cita cita = citaRepository.findById(id).orElse(null);

        if (cita == null) {
            return ResponseEntity.badRequest().body("Cita no encontrada");
        }

        if (!placaRepository.existsByNumeroPlacaIgnoreCase(placa.trim())) {
            return ResponseEntity.badRequest().body("La placa no existe");
        }

        if (!carretaRepository.existsByNumeroCarretaIgnoreCase(carreta.trim())) {
            return ResponseEntity.badRequest().body("La carreta no existe");
        }

        String estadoFinal = estado.toUpperCase().trim();

        if (!estadoFinal.equals("PENDIENTE")
                && !estadoFinal.equals("ATENDIDA")
                && !estadoFinal.equals("CANCELADA")) {
            return ResponseEntity.badRequest().body("Estado no válido");
        }

        cita.setNumeroPlaca(placa.trim());
        cita.setNumeroCarreta(carreta.trim());
        cita.setEstado(estadoFinal);

        citaRepository.save(cita);

        return ResponseEntity.ok("Cita editada correctamente");
    }
}