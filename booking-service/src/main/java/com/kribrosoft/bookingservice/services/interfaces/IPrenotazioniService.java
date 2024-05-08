package com.kribrosoft.bookingservice.services.interfaces;

import com.kribrosoft.bookingservice.models.CameraPrenotata;

import java.util.List;

public interface IPrenotazioniService {
    List<CameraPrenotata> getAllPrenotazioniByIdCamera(Long idCamera);
}
