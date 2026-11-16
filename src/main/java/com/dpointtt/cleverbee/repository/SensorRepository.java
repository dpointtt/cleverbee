package com.dpointtt.cleverbee.repository;

import com.dpointtt.cleverbee.model.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SensorRepository extends JpaRepository<Sensor, Long> {
}
