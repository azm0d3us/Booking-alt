package com.kribrosoft.bookingservice.response;

import com.kribrosoft.bookingservice.models.Camera;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrenotazioneResponse {
    private Long idPrenotazione;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private String nomeOspite;
    private String emailOspite;
    private int numeroAdulti;
    private int numeroBambini;
    private int numeroTotaleOspiti;
    private String codiceConfermaPrenotazione;
    private CameraResponse camera;

    public PrenotazioneResponse(Long idPrenotazione, LocalDate checkInDate, LocalDate checkOutDate,
                                String codiceConfermaPrenotazione) {
        this.idPrenotazione = idPrenotazione;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.codiceConfermaPrenotazione = codiceConfermaPrenotazione;
    }
}
