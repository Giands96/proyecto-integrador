package com.cibertec.demo.repository;

import com.cibertec.demo.modelo.Destinatario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DestinatarioRepository extends JpaRepository<Destinatario, Integer> {
}
