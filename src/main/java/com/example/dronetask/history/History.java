package com.example.dronetask.history;

import com.example.dronetask.model.Drone;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
@Component
public interface History {

    /**
     * updates The History with new data
     *
     * @param drone   drone updated data
     * @param event   The event which changed the data
     */
    public void updateHistory(Drone drone, String event);

    /**
     * Formats the DateTime
     *
     * @return  String of DateTime after formation
     */
    default String formatEventDate(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return formatter.format(LocalDateTime.now());
    }
}
