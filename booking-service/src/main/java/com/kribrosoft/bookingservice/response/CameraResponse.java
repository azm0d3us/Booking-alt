package com.kribrosoft.bookingservice.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
public class CameraResponse {
    private Long idCamera;
    private String tipoCamera;
    private BigDecimal prezzoCamera;
    private boolean isPrenotata;
    private String foto;
    private List<PrenotazioneResponse> prenotazioni;

    public CameraResponse(Long idCamera, String tipoCamera, BigDecimal prezzoCamera) {
        this.idCamera = idCamera;
        this.tipoCamera = tipoCamera;
        this.prezzoCamera = prezzoCamera;
    }

    public CameraResponse(Long idCamera, String tipoCamera, BigDecimal prezzoCamera,
                          boolean isPrenotata, byte[] fotoBytes) {
        this.idCamera = idCamera;
        this.tipoCamera = tipoCamera;
        this.prezzoCamera = prezzoCamera;
        this.isPrenotata = isPrenotata;
        this.foto = fotoBytes != null ? Base64.encodeBase64String(fotoBytes) : null;
//        this.prenotazioni = prenotazioni;
    }
}
