package com.cibertec.demo.repository;

import com.cibertec.demo.modelo.Terminal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TerminalRepository extends JpaRepository<Terminal, Integer> {
}
