package com.cibertec.demo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cibertec.demo.modelo.AgenciaAduana;
import com.cibertec.demo.modelo.BL;
import com.cibertec.demo.modelo.Chofer;
import com.cibertec.demo.modelo.Cita;
import com.cibertec.demo.modelo.Cliente;
import com.cibertec.demo.modelo.Contenedor;
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
import com.cibertec.demo.service.CitaService;

@Controller
public class Controlador {

    @Autowired
    private CitaService citaService;

    @Autowired
    private CitaRepository citaRepository;

    @GetMapping("/inicio")
    public String inicio(Model model, @RequestParam(value = "buscar", required = false) String buscar) {

        if (buscar == null || buscar.isEmpty()) {
            model.addAttribute("citas", citaService.listar());
        } else {
            model.addAttribute("citas", citaService.buscar(buscar));
        }

        return "inicio";
    }

    @GetMapping("/precita")
    public String precita() {
        return "precita";
    }

    @PostMapping("/cita/estado")
    @ResponseBody
    public ResponseEntity<?> actualizarEstado(@RequestParam Integer id, @RequestParam String estado) {

        Cita cita = citaRepository.findById(id).orElse(null);

        if (cita == null) {
            return ResponseEntity.badRequest().body("Cita no encontrada");
        }

        String estadoFinal = estado.toUpperCase().trim();

        if (!estadoFinal.equals("PENDIENTE")
                && !estadoFinal.equals("ATENDIDA")
                && !estadoFinal.equals("CANCELADA")) {
            return ResponseEntity.badRequest().body("Estado no válido");
        }

        cita.setEstado(estadoFinal);
        citaRepository.save(cita);

        return ResponseEntity.ok("Estado actualizado correctamente");
    }

    @RestController
    @RequestMapping("/api/agencia")
    public class AgenciaAduanaController {

        @Autowired
        private AgenciaAduanaRepository repo;

        @GetMapping("/buscar")
        public List<AgenciaAduana> buscar(@RequestParam String nombre) {
            return repo.buscarPorNombre(nombre);
        }
    }

    @RestController
    @RequestMapping("/api/cliente")
    public class ClienteController {

        @Autowired
        private ClienteRepository repo;

        @GetMapping("/buscar")
        public List<Cliente> buscar(@RequestParam String nombre) {
            return repo.buscarPorNombre(nombre);
        }
    }

    @RestController
    @RequestMapping("/api/bl")
    public class BLController {

        @Autowired
        private BLRepository blRepo;

        @Autowired
        private ContenedorRepository contenedorRepo;

        @GetMapping("/contenedores")
        public List<Contenedor> obtenerContenedores(
                @RequestParam String bl,
                @RequestParam String rucCliente
        ) {

            BL registroBL = blRepo.findById(bl).orElse(null);

            if (registroBL == null) {
                return List.of();
            }

            if (!registroBL.getRucCliente().equals(rucCliente)) {
                return List.of();
            }

            return contenedorRepo.findByNumeroBL(bl);
        }
    }

    @Controller
    @RequestMapping("/cita")
    public class CitaController {

        @Autowired
        private ContenedorRepository contenedorRepo;

        @Autowired
        private PlacaRepository placaRepository;

        @Autowired
        private CarretaRepository carretaRepository;

        @GetMapping("/form")
        public String mostrarFormulario(
                @RequestParam String contenedor,
                @RequestParam String bl,
                Model model
        ) {
            Contenedor c = contenedorRepo.findById(contenedor).orElse(null);

            model.addAttribute("numeroContenedor", contenedor);
            model.addAttribute("tipoContenedor", (c != null) ? c.getTipo() : "");
            model.addAttribute("bl", bl);

            return "fragments/formulario :: formGenerarCitaModal";
        }

        @GetMapping("/api/placa/validar")
        @ResponseBody
        public Map<String, Boolean> validarPlaca(@RequestParam String placa) {
            boolean valido = placaRepository.existsByNumeroPlacaIgnoreCase(placa.trim());
            return Map.of("valido", valido);
        }

        @GetMapping("/api/carreta/validar")
        @ResponseBody
        public boolean validarCarreta(@RequestParam String carreta) {
            return carretaRepository.existsById(carreta);
        }

        @GetMapping("/login")
        public String loginForm() {
            return "login";
        }

        @PostMapping("/login")
        public String loginSubmit(String username, String password, Model model) {

            if ("admin".equals(username) && "1234".equals(password)) {
                return "redirect:/inicio";
            } else {
                model.addAttribute("error", "Usuario o contraseña incorrectos");
                return "login";
            }
        }

        @RestController
        @RequestMapping("/api/chofer")
        public class ChoferController {

            @Autowired
            private ChoferRepository choferRepository;

            @GetMapping("/buscarDni")
            public ResponseEntity<?> buscarPorDni(@RequestParam String dni) {
                return choferRepository.findById(dni)
                        .map(ResponseEntity::ok)
                        .orElse(ResponseEntity.ok(new Chofer()));
            }

            @GetMapping("/buscarNombre")
            public List<Chofer> buscarPorNombre(@RequestParam String nombre) {
                return choferRepository.findByNombreChoferContainingIgnoreCase(nombre);
            }
        }

        @RestController
        @RequestMapping("/api/transporte")
        public class TransporteController {

            @Autowired
            private EmpresaTransporteRepository empresaTransporteRepository;

            @GetMapping("/buscarPorRuc")
            @ResponseBody
            public EmpresaTransporte buscarPorRuc(@RequestParam String ruc) {
                return empresaTransporteRepository.findById(ruc).orElse(new EmpresaTransporte());
            }
        }
    }
}