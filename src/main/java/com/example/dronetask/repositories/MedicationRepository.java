package com.example.dronetask.repositories;

import com.example.dronetask.models.Drone;
import com.example.dronetask.models.Medication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface MedicationRepository extends JpaRepository<Medication, String> {
    List<Medication> findAll();
    List<Medication> findByDroneId(Drone drone);
}
