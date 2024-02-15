package com.facuvande.citiesservice.repository;

import com.facuvande.citiesservice.dto.HotelDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

// mismo nombre de la app en eureka
@FeignClient(name = "hotels-service")
public interface IHotelsAPI {

    @GetMapping("hotels/{city_id}")
    public List<HotelDTO> getHotelsByCityId(@PathVariable("city_id") Long city_id);

}
