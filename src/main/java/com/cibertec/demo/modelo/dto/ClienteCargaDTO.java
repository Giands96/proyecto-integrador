package com.cibertec.demo.modelo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClienteCargaDTO {

    private Long idCliente;
    private String nombres;
    private String apellidos;

}
