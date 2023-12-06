package com.example.dronetask.repositories;

import com.example.dronetask.models.Drone;
import com.example.dronetask.models.DroneState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface DroneRepository extends JpaRepository<Drone, String> {
    Drone findBySerialNumberAndState(String serialNumber, DroneState droneState);
    List<Drone> findAll();
    List<Drone> findByState(DroneState state);
}
