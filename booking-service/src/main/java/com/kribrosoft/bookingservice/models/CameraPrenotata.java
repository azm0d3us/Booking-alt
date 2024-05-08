package com.kribrosoft.bookingservice.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CameraPrenotata {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPrenotazione;

    @Column(name = "check_in")
    private LocalDate checkInDate;

    @Column(name = "check_out")
    private LocalDate checkOutDate;

    @Column(name = "nome_ospite")
    private String nomeOspite;

    @Column(name = "email_ospite")
    private String emailOspite;

    @Column(name = "numero_adulti")
    private int numeroAdulti;

    @Column(name = "numero_bambini")
    private int numeroBambini;

    @Column(name = "numero_ospiti")
    private int numeroTotaleOspiti;

    @Column(name = "codice_conferma")
    private String codiceConfermaPrenotazione;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_camera")
    private Camera camera;

    public void calcolaNumeroOspiti() {
        this.numeroTotaleOspiti = this.numeroAdulti + this.numeroBambini;
    }

    public void setNumeroAdulti(int numeroAdulti) {
        this.numeroAdulti = numeroAdulti;
        calcolaNumeroOspiti();
    }

    public void setNumeroBambini(int numeroBambini) {
        this.numeroBambini = numeroBambini;
        calcolaNumeroOspiti();
    }

    public void setCodiceConfermaPrenotazione(String codiceConfermaPrenotazione) {
        this.codiceConfermaPrenotazione = codiceConfermaPrenotazione;
    }
}
