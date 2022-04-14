package com.techelevator.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techelevator.model.Address;
import com.techelevator.model.Coordinates;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class GeocodingService {
    //US Census Geocoding API
    //Base URL for single line formatting address lookup: https://geocoding.geo.census.gov/geocoder/locations/onelineaddress
    //Required query parameters: address = (single line street address in format: street address, city, zip, state)
    //                           benchmark = Public_AR_Current
    //                           format = json
    //
    //          example API call: https://geocoding.geo.census.gov/geocoder/locations/onelineaddress?address=4823+Ridgebury+Blvd%2C+Lyndhurst+%2C+OH+44124&benchmark=Public_AR_Current&format=json


    public static final String GEOCODING_API_BASE_URL = "https://geocoding.geo.census.gov/geocoder/locations/onelineaddress";


    private RestTemplate restTemplate = new RestTemplate();

    public String getGeocodingResultAsString(Address address){
        ResponseEntity<String> response = restTemplate.getForEntity(GEOCODING_API_BASE_URL + "?address=" + address.toString() + "&benchmark=Public_AR_Current&format=json", String.class);
        String geocodingResult = response.getBody();
        return geocodingResult;
    }

    public Coordinates getGeocodingResultAsCoordinates(Address address){
        ResponseEntity<String> response = restTemplate.getForEntity(GEOCODING_API_BASE_URL + "?address=" + address.toString() + "&benchmark=Public_AR_Current&format=json", String.class);
        ObjectMapper jsonMapper = new ObjectMapper();
        JsonNode root = null;
        Coordinates coordinates = null;
        try {
            root = jsonMapper.readTree(response.getBody());
            JsonNode results = root.get("result");
            JsonNode matches = results.get("addressMatches");
            JsonNode firstMatch = matches.get(0);
            if(firstMatch==null){
                return new Coordinates(0,0);
            }
            JsonNode foundCoordinates = firstMatch.get("coordinates");
            JsonNode longitude = foundCoordinates.get("x");
            JsonNode latitude = foundCoordinates.get("y");
            coordinates = new Coordinates(latitude.asDouble(), longitude.asDouble());
        } catch (JsonProcessingException e) {
            System.out.println("Error converting address to coordinates: " + address.toString());
            return new Coordinates(0,0);
        }
        return coordinates;
    }

}