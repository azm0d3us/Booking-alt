package com.kribrosoft.bookingservice.services.interfaces;

import com.kribrosoft.bookingservice.models.Camera;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface ICameraService {
    Camera addNewCamera(MultipartFile foto, String tipoCamera, BigDecimal prezzoCamera) throws SQLException, IOException;

    List<String> getTipiCamera();

    List<Camera> getAll();

    byte[] getCameraFotoByIdCamera(Long idCamera) throws SQLException;

    void deleteCamera(Long idCamera);

    Camera updateCamera(Long idCamera, String tipoCamera, BigDecimal prezzoCamera, byte[] fotoBytes);

    Optional<Camera> getCameraById(Long idCamera);
}
