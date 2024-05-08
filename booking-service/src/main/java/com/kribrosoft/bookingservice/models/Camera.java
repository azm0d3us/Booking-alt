package com.kribrosoft.bookingservice.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.catalina.util.StringUtil;
import org.apache.commons.lang3.RandomStringUtils;

import java.math.BigDecimal;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
public class Camera {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCamera;

    private String tipoCamera;
    private BigDecimal prezzoCamera;
    private boolean isPrenotata = false;

    @Lob
    private Blob foto;

    @OneToMany(mappedBy = "camera", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<CameraPrenotata> prenotazioni;

    public Camera() {
        this.prenotazioni = new ArrayList<>();
    }

    public void addPrenotazioni(CameraPrenotata prenotazione) {
        if(prenotazioni == null) {
            prenotazioni = new ArrayList<>();
        }
        prenotazioni.add(prenotazione);
        prenotazione.setCamera(this);
        isPrenotata = true;
        String codicePrenotazione = RandomStringUtils.randomNumeric(10);
        prenotazione.setCodiceConfermaPrenotazione(codicePrenotazione);
    }
}
