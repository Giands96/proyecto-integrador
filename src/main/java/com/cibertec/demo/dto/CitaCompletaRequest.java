package com.cibertec.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CitaCompletaRequest {
    private Long idCliente;
    private Long idDestinatario;
    private Long idTerminalOrigen;
    private Long idTerminalDestino;
    private Long idCarga;
    private Long idUsuario;
    private Long idCamion;
    private Integer diasEstimados;
    private String observacion;

}
