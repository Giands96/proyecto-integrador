package com.cibertec.demo.service;

import com.cibertec.demo.modelo.Usuario;
import com.cibertec.demo.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public Page<Usuario> listarEmpleados(Pageable pageable) {
        return usuarioRepository.findAll(pageable);
    }

}
