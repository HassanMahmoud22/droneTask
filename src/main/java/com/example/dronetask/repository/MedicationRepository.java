package com.example.dronetask.repository;

import com.example.dronetask.model.Drone;
import com.example.dronetask.model.Medication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface MedicationRepository extends JpaRepository<Medication, String> {
    List<Medication> findAll();
    List<Medication> findByDroneId(Drone drone);
}
