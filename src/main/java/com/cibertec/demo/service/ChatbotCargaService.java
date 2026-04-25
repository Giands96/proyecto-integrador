package com.cibertec.demo.service;

import com.cibertec.demo.modelo.Carga;
import com.cibertec.demo.repository.CargaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatbotCargaService {

    private final ChatClient chatCLient;
    private final CargaRepository cargaRepository;

    public String consultarEstadoCargaBot(String idSeguimiento, String nroDocumento, String preguntaUsuario){
        Optional<Carga> cargaOpt = cargaRepository.findByCodigoSeguimiento(idSeguimiento);

        if(cargaOpt.isEmpty()){
            return "No se encontró la carga con ID: " + idSeguimiento;
        }

        Carga carga = cargaOpt.get();

        //?  Validar seguridad: El documento debe coincidir con el cliente dueño de la carga
        //if(!carga.getCliente().getNumeroDocumento().equals(nroDocumento)){
          //  return "El número de documento no coincide con el cliente asociado a la carga.";
       // }

        //? Construir el contexto inyectando los datos reales de la base de datos
        String contexto = String.format(
                "Eres un asistente virtual de logística para la empresa RANSA. " +
                        "Un cliente está preguntando sobre su envío. Usa la siguiente información para responderle de forma natural, resumida y servicial:\n" +
                        "- Código de Seguimiento: %s\n" +
                        "- Tipo de Carga: %s\n" +
                        "- Estado Actual: %s\n" +
                        "- Descripción: %s\n" +
                        "Pregunta del cliente: %s",
                carga.getCodigoSeguimiento(), carga.getTipoCarga(),
                carga.getEstado().name(), carga.getDescripcionCarga(), preguntaUsuario
        );

        try {
            return chatCLient.prompt()
                    .system("...")
                    .user(contexto)
                    .call()
                    .content();
        } catch (Exception e) {
            return "Lo siento, el asistente está saturado en este momento. Por favor, intenta de nuevo en un minuto.";
        }
    }

}



