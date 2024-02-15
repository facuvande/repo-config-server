package com.facuvande.hotelsservice.controller;

import com.facuvande.hotelsservice.model.Hotel;
import com.facuvande.hotelsservice.service.IHotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// HOTELS SOLO VA A RECIBIR PETICION Y RESPONDER CUALES SON LOS HOTELES QUE ESTAN EN ESA CIUDAD QUE ENVIAMOS POR ID
@RestController
@RequestMapping("/hotels")
public class HotelsController {

    @Autowired
    private IHotelService servHotel;

    @GetMapping("/{city_id}")
    public List<Hotel> getHotelsByCityId(@PathVariable Long city_id){
        return servHotel.getHotelsByCityId(city_id);
    }

}
