package com.kribrosoft.bookingservice.services;

import com.kribrosoft.bookingservice.exceptions.InternalServerException;
import com.kribrosoft.bookingservice.exceptions.ResourceNotFoundException;
import com.kribrosoft.bookingservice.models.Camera;
import com.kribrosoft.bookingservice.repositories.CameraRepository;
import com.kribrosoft.bookingservice.services.interfaces.ICameraService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CameraServiceImpl implements ICameraService {

    @Autowired
    private CameraRepository cameraRepository;

    @Override
    public Camera addNewCamera(MultipartFile file, String tipoCamera, BigDecimal prezzoCamera) throws SQLException, IOException {
        Camera camera = new Camera();
        camera.setTipoCamera(tipoCamera);
        camera.setPrezzoCamera(prezzoCamera);
        if(!file.isEmpty()){
            byte[] fotoBytes = file.getBytes();
            Blob fotoBlob = new SerialBlob(fotoBytes);
            camera.setFoto(fotoBlob);
        }
        return cameraRepository.save(camera);
    }

    @Override
    public List<String> getTipiCamera() {
        return cameraRepository.findDistinctTipoCamera();
    }

    @Override
    public List<Camera> getAll() {
        return cameraRepository.findAll();
    }

    @Override
    public byte[] getCameraFotoByIdCamera(Long idCamera) throws SQLException {
        Optional<Camera> cameraOptional = cameraRepository.findById(idCamera);
        if(!cameraOptional.isPresent()) {
            throw new ResourceNotFoundException("Spiacente, Camera non trovata");
        }
        Blob fotoBlob = cameraOptional.get().getFoto();
        if(fotoBlob != null) {
            return fotoBlob.getBytes(1, (int) fotoBlob.length());
        }
        return null;
    }

    @Override
    public void deleteCamera(Long idCamera) {
        Optional<Camera> cameraOptional = cameraRepository.findById(idCamera);
        if(!cameraOptional.isPresent()) {
            throw new ResourceNotFoundException("Spiacente, Camera non trovata");
        }
        cameraRepository.delete(cameraOptional.get());
    }

    @Override
    public Camera updateCamera(Long idCamera, String tipoCamera, BigDecimal prezzoCamera, byte[] fotoBytes) {
        Camera camera = cameraRepository.findById(idCamera)
                .orElseThrow(() -> new ResourceNotFoundException("Camera non trovata"));
        if(tipoCamera != null) camera.setTipoCamera(tipoCamera);
        if(prezzoCamera != null) camera.setPrezzoCamera(prezzoCamera);
        if(fotoBytes != null && fotoBytes.length > 0) {
            try{
                camera.setFoto(new SerialBlob(fotoBytes));
            } catch (SQLException e) {
                throw new InternalServerException("Errore nell'aggiornamento della camera.");
            }
        }
        return cameraRepository.save(camera);
    }

    @Override
    public Optional<Camera> getCameraById(Long idCamera) {
        return Optional.of(cameraRepository.findById(idCamera).get());
    }
}
