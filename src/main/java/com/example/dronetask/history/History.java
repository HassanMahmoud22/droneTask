package com.example.dronetask.history;

import com.example.dronetask.model.Drone;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
@Component
public interface History {
    public void updateHistory(Drone drone, String message);
    public default String formatEventDate(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return formatter.format(LocalDateTime.now());
    }
}
