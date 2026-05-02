package com.cibertec.demo.repository;

import com.cibertec.demo.modelo.Carga;
import com.cibertec.demo.modelo.CargaEstado;
import com.cibertec.demo.modelo.Cliente;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

//* PRUEBAS DE INTEGRACION CON BASE DE DATOS REAL (H2) PARA CARGA Y CLIENTE

@DataJpaTest
public class CargaRepositoryIntegrationTest {

    @Autowired
    private CargaRepository cargaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Test
    void guardarYRecuperarCargaConRelacionAClienteEnBdReal() {
        System.out.println("\n==================================================");
        System.out.println(" INICIANDO PRUEBA DE INTEGRACIÓN: CARGA Y CLIENTE");
        System.out.println("==================================================");

        //* ARRANGE
        System.out.println(" [PASO 1 - ARRANGE]: Preparando datos iniciales...");
        Cliente cliente = new Cliente();
        cliente.setTipoDocumento("RUC");
        cliente.setNumeroDocumento("20512345678");
        cliente.setNombresRazonSocial("RANSA S.A.");
        cliente.setDepartamento("Lima");
        cliente.setProvincia("Huaral");
        cliente.setDistrito("Huaral");

        Cliente clienteGuardado = clienteRepository.save(cliente);
        System.out.println("  Cliente corporativo creado en BD -> ID: " + clienteGuardado.getIdCliente() + " | Razón Social: " + clienteGuardado.getNombresRazonSocial());

        Carga nuevaCarga = new Carga();
        nuevaCarga.setTipoCarga("FRAGIL");
        nuevaCarga.setDescripcionCarga("Cristaleria");
        nuevaCarga.setEstado(CargaEstado.PENDIENTE);
        nuevaCarga.setCliente(clienteGuardado);
        System.out.println("  Carga lista para registrar -> Tipo: " + nuevaCarga.getTipoCarga());

        //* ACT
        System.out.println("\n [PASO 2 - ACT]: Ejecutando operación contra la BD...");
        Carga cargaGuardada = cargaRepository.save(nuevaCarga);
        Carga cargaRecuperada = cargaRepository.findById(cargaGuardada.getIdCarga()).orElse(null);
        System.out.println("  Carga insertada exitosamente con ID generado: " + cargaGuardada.getIdCarga());

        //* ASSERT
        System.out.println("\n[PASO 3 - ASSERT]: Validando resultados...");
        assertNotNull(cargaRecuperada, "La carga debe existir en la base de datos H2");
        assertNotNull(cargaRecuperada.getIdCarga(), "Hibernate debió autogenerar un ID numérico");
        assertEquals("FRAGIL", cargaRecuperada.getTipoCarga());
        assertEquals(clienteGuardado.getIdCliente(), cargaRecuperada.getCliente().getIdCliente());
        System.out.println("  Todas las aserciones pasaron.");

        System.out.println("==================================================\n");
    }

}
