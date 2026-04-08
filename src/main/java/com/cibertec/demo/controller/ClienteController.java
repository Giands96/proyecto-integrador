package com.cibertec.demo.controller;

import com.cibertec.demo.modelo.Cliente;
import com.cibertec.demo.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    @GetMapping
    public Page<Cliente> listar(@PageableDefault(size = 10) Pageable pageable) {
        return clienteRepository.findAll(pageable);
    }

    @PostMapping
    public Cliente crear(@RequestBody Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> actualizar(@PathVariable Long id, @RequestBody Cliente clienteDetalles) {
        return clienteRepository.findById(id)
                .map(cliente -> {
                    cliente.setTipoDocumento(clienteDetalles.getTipoDocumento());
                    cliente.setNumeroDocumento(clienteDetalles.getNumeroDocumento());
                    cliente.setNombresRazonSocial(clienteDetalles.getNombresRazonSocial());
                    cliente.setDepartamento(clienteDetalles.getDepartamento());
                    cliente.setProvincia(clienteDetalles.getProvincia());
                    cliente.setDistrito(clienteDetalles.getDistrito());
                    return ResponseEntity.ok(clienteRepository.save(cliente));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        return clienteRepository.findById(id)
                .map(cliente -> {
                    clienteRepository.delete(cliente);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
