package com.kribrosoft.bookingservice.repositories;

import com.kribrosoft.bookingservice.models.Camera;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface CameraRepository extends JpaRepository<Camera, Long> {

    @Transactional
    Camera save(Camera camera);

    @Query("SELECT DISTINCT c.tipoCamera FROM Camera c")
    List<String> findDistinctTipoCamera();
}
