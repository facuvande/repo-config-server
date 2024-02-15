package com.facuvande.citiesservice.service;

import com.facuvande.citiesservice.dto.CityDTO;
import com.facuvande.citiesservice.model.City;
import com.facuvande.citiesservice.repository.IHotelsAPI;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CityService implements ICityService{

    @Autowired
    private IHotelsAPI hotelsAPI;

    List<City> cities = new ArrayList<>();

    @Override
    // Circuit Breaker va del lado del microservicio que hace la consulta
    // name= Nombre del servicio al cual consultamos
    // fallbackMethod = En caso de que algo no funcione o no pueda comunicarme, desviamos el flujo al metodo fallbackGetCitiesHotel
    @CircuitBreaker(name="hotels-service", fallbackMethod = "fallbackGetCitiesHotel")
    // Cuando se produzca el error, que al pasar un tiempo vuelva a intentar comunicarse para abrir el circuito nuevamente
    @Retry(name="hotels-service")
    public CityDTO getCitiesHotels(String name, String country) {
        // Buscamos ciudad original
        City city = this.findCity(name, country);

        // Creamos DTO de ciudad + lista hotele
        CityDTO cityDTO = new CityDTO();
        cityDTO.setCity_id(city.getCity_id());
        cityDTO.setName(city.getName());
        cityDTO.setCountry(city.getCountry());
        cityDTO.setContinent(city.getContinent());
        cityDTO.setState(city.getState());

        // buscamos lista de hoteles en la API y asignamos
        cityDTO.setHotelList(hotelsAPI.getHotelsByCityId(city.getCity_id()));
        //createException();

        return cityDTO;
    }


    public CityDTO fallbackGetCitiesHotel(Throwable throwable){
        return new CityDTO(9999999999L, "Fallido", "Fallido", "Fallido", "Fallido", null);
    }

    public void createException(){
        throw new IllegalArgumentException("Prueba Resilience y Circuit Breaker");
    }

    public City findCity(String name, String country){
        this.loadCities();
        for(City c : cities){
            if(c.getName().equals(name)){
                if(c.getCountry().equals(country)){
                    return c;
                }
            }

        }
        return null;
    }

    public void loadCities(){
        cities.add(new City(1L, "Buenos Aires", "South America", "Argentina", "Buenos Aires"));
        cities.add(new City(2L, "Obera", "South America", "Argentina", "Misiones"));
        cities.add(new City(3L, "Mexico City", "North America", "Mexico", "Mexico City"));
        cities.add(new City(4L, "Guadalajara", "North America", "Mexico", "Jalisco"));
        cities.add(new City(5L, "Bogota", "South America", "Colombia", "Cundinamarca"));
        cities.add(new City(6L, "Medellin", "South America", "Colombia", "Antioquia"));
        cities.add(new City(7L, "Santiago", "South America", "Chile", "Santiago Metropolitan"));
        cities.add(new City(8L, "Valparaiso", "South America", "Chile", "Valparaiso"));
        cities.add(new City(9L, "Asuncion", "South America", "Paraguay", "Asuncion"));
        cities.add(new City(10L, "Montevideo", "South America", "Uruguay", "Montevideo"));
        cities.add(new City(11L, "Madrid", "Europe", "Spain", "Community of Madrid"));
        cities.add(new City(12L, "Barcelona", "Europe", "Spain", "Catalunia"));
        cities.add(new City(13L, "Seville", "Europe", "Spain", "Andalucia"));
        cities.add(new City(14L, "Monterey", "North America", "Mexico", "Montecarlos"));
        cities.add(new City(15L, "Valencia", "Europe", "Spain", "Valencia"));
    }
}
