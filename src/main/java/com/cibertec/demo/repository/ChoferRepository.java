package com.cibertec.demo.repository;

import com.cibertec.demo.modelo.Chofer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChoferRepository extends JpaRepository<Chofer, Integer> {
}
