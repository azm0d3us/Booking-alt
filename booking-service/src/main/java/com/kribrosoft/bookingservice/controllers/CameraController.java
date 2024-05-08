package com.kribrosoft.bookingservice.controllers;

import com.kribrosoft.bookingservice.exceptions.PhotoRetrievalException;
import com.kribrosoft.bookingservice.exceptions.ResourceNotFoundException;
import com.kribrosoft.bookingservice.models.Camera;
import com.kribrosoft.bookingservice.models.CameraPrenotata;
import com.kribrosoft.bookingservice.response.CameraResponse;
import com.kribrosoft.bookingservice.response.PrenotazioneResponse;
import com.kribrosoft.bookingservice.services.interfaces.ICameraService;
import com.kribrosoft.bookingservice.services.interfaces.IPrenotazioniService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/camere")
public class CameraController {

    @Autowired
    private ICameraService cameraService;
    @Autowired
    private IPrenotazioniService prenotazioniService;

    @PostMapping(value = "/aggiungi/nuova-camera", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addNewCamera (
            @RequestParam("foto") MultipartFile foto,
            @RequestParam("tipoCamera")String tipoCamera,
            @RequestParam("prezzoCamera")BigDecimal prezzoCamera) throws SQLException, IOException {
        Camera cameraAggiunta = cameraService.addNewCamera(foto, tipoCamera, prezzoCamera);
        CameraResponse response = new CameraResponse(cameraAggiunta.getIdCamera(),
                cameraAggiunta.getTipoCamera(), cameraAggiunta.getPrezzoCamera());
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/tipi")
    public List<String> tipiCamera() {
        return cameraService.getTipiCamera();
    }

    @GetMapping(value = "/tutte-le-camere")
    public ResponseEntity<?> getAllCamere() throws SQLException {
        List<Camera> camere = cameraService.getAll();
        List<CameraResponse> camereResponse = new ArrayList<>();
        for(Camera camera : camere) {
            byte[] fotoBytes = cameraService.getCameraFotoByIdCamera(camera.getIdCamera());
            if(fotoBytes != null && fotoBytes.length > 0) {
                String base64Foto = Base64.encodeBase64String(fotoBytes);
                CameraResponse cameraResponse = getCameraResponse(camera);
                cameraResponse.setFoto(base64Foto);
                camereResponse.add(cameraResponse);
            }
        }
        return ResponseEntity.ok(camereResponse);
    }

    @DeleteMapping(value = "/elimina/camera/{idCamera}")
    public ResponseEntity<?> deleteCamera(@PathVariable("idCamera") Long idCamera) {
        cameraService.deleteCamera(idCamera);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(value = "/modifica/camera/{idCamera}")
    public ResponseEntity<?> updateCamera(@PathVariable("idCamera") Long idCamera,
                                          @RequestParam(required = false) String tipoCamera,
                                          @RequestParam(required = false) BigDecimal prezzoCamera,
                                          @RequestParam(required = false) MultipartFile foto) throws SQLException, IOException {
        byte[] fotoBytes = foto != null && !foto.isEmpty() ?
                foto.getBytes() : cameraService.getCameraFotoByIdCamera(idCamera);
        Blob fotoBlob = fotoBytes != null && fotoBytes.length > 0 ? new SerialBlob(fotoBytes) : null;
        Camera camera = cameraService.updateCamera(idCamera, tipoCamera, prezzoCamera, fotoBytes);
        camera.setFoto(fotoBlob);
        CameraResponse cameraResponse = getCameraResponse(camera);
        return ResponseEntity.ok(cameraResponse);
    }

    @GetMapping("/camera/{idCamera}")
    public ResponseEntity<?> getCameraById(@PathVariable("idCamera") Long idCamera) {
        Optional<Camera> cameraOptional = cameraService.getCameraById(idCamera);
        return cameraOptional.map(camera -> {
            CameraResponse cameraResponse = getCameraResponse(camera);
            return ResponseEntity.ok(Optional.of(cameraResponse));
        }).orElseThrow(() -> new ResourceNotFoundException("Camera non trovata"));
    }

    private CameraResponse getCameraResponse(Camera camera) {
        List<CameraPrenotata> prenotazioni = getAllPrenotazioniByIdCamera(camera.getIdCamera());
//        List<PrenotazioneResponse> prenotazioniInfo = prenotazioni
//                .stream()
//                .map(prenotazione -> new PrenotazioneResponse(prenotazione.getIdPrenotazione(),
//                        prenotazione.getCheckInDate(),
//                        prenotazione.getCheckOutDate(),
//                        prenotazione.getCodiceConfermaPrenotazione())).toList();
        byte[] fotoBytes = null;
        Blob fotoBlob = camera.getFoto();
        if(fotoBlob != null) {
            try {
                fotoBytes = fotoBlob.getBytes(1, (int) fotoBlob.length());
            } catch (SQLException e) {
                throw new PhotoRetrievalException("Errore nel recupero della foto.");
            }
        }
        return new CameraResponse(camera.getIdCamera(),
                camera.getTipoCamera(),
                camera.getPrezzoCamera(),
                camera.isPrenotata(),
                fotoBytes);
    }

    private List<CameraPrenotata> getAllPrenotazioniByIdCamera(Long idCamera) {
        return prenotazioniService.getAllPrenotazioniByIdCamera(idCamera);
    }
}
