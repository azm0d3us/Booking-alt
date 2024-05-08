package com.kribrosoft.bookingservice.services;

import com.kribrosoft.bookingservice.models.CameraPrenotata;
import com.kribrosoft.bookingservice.services.interfaces.IPrenotazioniService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrenotazioniService implements IPrenotazioniService {
    @Override
    public List<CameraPrenotata> getAllPrenotazioniByIdCamera(Long idCamera) {
        return null;
    }
}
