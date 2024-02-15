package com.facuvande.hotelsservice.service;

import com.facuvande.hotelsservice.model.Hotel;

import java.util.List;

public interface IHotelService {

    public List<Hotel> getHotelsByCityId(Long city_id);

}
