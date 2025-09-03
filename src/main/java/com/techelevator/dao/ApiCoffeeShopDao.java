//
//package com.techelevator.dao;
//
//import com.techelevator.model.CoffeeShop;
//import org.springframework.stereotype.Component;
//import org.springframework.web.client.RestTemplate;
//import org.springframework.web.client.HttpClientErrorException;
//
//import java.util.ArrayList;
//import java.util.List;
//
////@Component
//public abstract class ApiCoffeeShopDao implements CoffeeShopDao {
//
//    private static final String API_URL = "https://api.example.com/coffeeShops";  //TODO: Replace with actual API URL, see link and key below
////     API Key: AIzaSyAJmcx-ggOtNDyCnYvTEIOzo_sTKjP-aw4
////
////    <script async defer
////      src="https://maps.googleapis.com/maps/api/js?key=YOUR_API_KEY"> </script>
////
////    https://www.googleapis.com/auth/cloud-platform
//
//    private final RestTemplate restTemplate;
//
//    public ApiCoffeeShopDao(RestTemplate restTemplate) {
//        this.restTemplate = restTemplate;
//    }
//
//    @Override
//    public List<CoffeeShop> getAllCoffeeShops() {
//        String url = API_URL + "?term=coffee"; // Modify the URL parameters as needed
//
//        List<CoffeeShop> coffeeShops = new ArrayList<>();
//
//        try {
//            // Make HTTP GET request to fetch coffee shops
//            CoffeeShop[] apiCoffeeShops = restTemplate.getForObject(url, CoffeeShop[].class);
//
//            // Add all coffee shops from the API response to the list
//            if (apiCoffeeShops != null) {
//                for (CoffeeShop apiCoffeeShop : apiCoffeeShops) {
//                    coffeeShops.add(apiCoffeeShop);
//                }
//            }
//        } catch (HttpClientErrorException e) {
//            // Handle HTTP client error
//            System.out.println("Error fetching coffee shops: " + e.getMessage());
//        } catch (Exception e) {
//            // Handle other exceptions
//            System.out.println("An error occurred while fetching coffee shops: " + e.getMessage());
//        }
//
//        return coffeeShops;
//    }
//
//    @Override
//    public CoffeeShop getCoffeeShopById(int id) {
//        String url = API_URL + "/" + id; // Modify as needed to match the API's URL pattern
//
//        try {
//            // Make HTTP GET request to fetch a single coffee shop by ID
//            return restTemplate.getForObject(url, CoffeeShop.class);
//        } catch (HttpClientErrorException e) {
//            // Handle HTTP client error (e.g., 404 if coffee shop is not found)
//            System.out.println("Error fetching coffee shop with ID " + id + ": " + e.getMessage());
//            return null;
//        } catch (Exception e) {
//            // Handle other exceptions
//            System.out.println("An error occurred while fetching the coffee shop: " + e.getMessage());
//            return null;
//        }
//    }
//
//    @Override
//    public List<CoffeeShop> getCoffeeShopsByName(String name) {
//        String url = API_URL + "?name=" + name;  // Modify the URL parameters as needed
//
//        List<CoffeeShop> coffeeShops = new ArrayList<>();
//
//        try {
//            // Make HTTP GET request to fetch coffee shops by name
//            CoffeeShop[] apiCoffeeShops = restTemplate.getForObject(url, CoffeeShop[].class);
//
//            if (apiCoffeeShops != null) {
//                for (CoffeeShop apiCoffeeShop : apiCoffeeShops) {
//                    coffeeShops.add(apiCoffeeShop);
//                }
//            }
//        } catch (HttpClientErrorException e) {
//            // Handle HTTP client error
//            System.out.println("Error fetching coffee shops by name: " + e.getMessage());
//        } catch (Exception e) {
//            // Handle other exceptions
//            System.out.println("An error occurred while fetching coffee shops by name: " + e.getMessage());
//        }
//
//        return coffeeShops;
//    }
//
//    @Override
//    public List<CoffeeShop> getCoffeeShopsByLocation(String location) {
//        String url = API_URL + "?location=" + location;  // Modify the URL parameters as needed
//
//        List<CoffeeShop> coffeeShops = new ArrayList<>();
//
//        try {
//            // Make HTTP GET request to fetch coffee shops by location
//            CoffeeShop[] apiCoffeeShops = restTemplate.getForObject(url, CoffeeShop[].class);
//
//            if (apiCoffeeShops != null) {
//                for (CoffeeShop apiCoffeeShop : apiCoffeeShops) {
//                    coffeeShops.add(apiCoffeeShop);
//                }
//            }
//        } catch (HttpClientErrorException e) {
//            // Handle HTTP client error
//            System.out.println("Error fetching coffee shops by location: " + e.getMessage());
//        } catch (Exception e) {
//            // Handle other exceptions
//            System.out.println("An error occurred while fetching coffee shops by location: " + e.getMessage());
//        }
//
//        return coffeeShops;
//    }
//
//
//    @Override
//    public boolean deleteCoffeeShopById(int id) {
//        // Most APIs do not support deleting objects directly, so this might not be applicable
//        return false;
//    }
//
//    @Override
//    public CoffeeShop createCoffeeShop(CoffeeShop coffeeShop) {
//        // API may not support direct creation of coffee shops through POST requests
//        return null;
//    }
//
//    @Override
//    public CoffeeShop updateCoffeeShop(CoffeeShop coffeeShop) {
//        // API may not support direct updating of coffee shops
//        return null;
//    }
//
//    // Helper method to map API response to CoffeeShop (if necessary)
//    private CoffeeShop mapToCoffeeShop(Object apiResponse) {
//        // If the API returns a complex structure, you may need to manually map it to a CoffeeShop object
//        // For now, this is a placeholder as the API response is assumed to be directly mappable to CoffeeShop
//        return (CoffeeShop) apiResponse;
//    }
//}

package com.techelevator.dao;
import com.techelevator.model.CoffeeShop;
import com.techelevator.model.dto.GoogleGeocodeResponse;
import com.techelevator.model.dto.GooglePlaceResult;
import com.techelevator.model.dto.GooglePlacesResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Component
public class ApiCoffeeShopDao implements CoffeeShopDao {

    private static final String API_KEY = "AIzaSyAJmcx-ggOtNDyCnYvTEIOzo_sTKjP-aw4";

    private final RestTemplate restTemplate;

    public ApiCoffeeShopDao(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<CoffeeShop> getCoffeeShopsByLocation(String locationName) {
        String geocodeUrl = "https://maps.googleapis.com/maps/api/geocode/json?address=" + locationName + "&key=" + API_KEY;
        GoogleGeocodeResponse geoResponse = restTemplate.getForObject(geocodeUrl, GoogleGeocodeResponse.class);

        if (geoResponse == null || geoResponse.results == null || geoResponse.results.isEmpty()) {
            return new ArrayList<>();
        }

        double lat = geoResponse.results.get(0).geometry.location.lat;
        double lng = geoResponse.results.get(0).geometry.location.lng;

        String placesUrl = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="
                + lat + "," + lng + "&radius=5000&keyword=coffee&key=" + API_KEY;

        GooglePlacesResponse placesResponse = restTemplate.getForObject(placesUrl, GooglePlacesResponse.class);

        if (placesResponse == null || placesResponse.results == null || placesResponse.results.isEmpty()) {
            System.out.println("No coffee shops found near: " + locationName);
            return new ArrayList<>();
        }
        return mapPlacesToCoffeeShops(placesResponse);
    }

    @Override
    public List<CoffeeShop> getCoffeeShopsByName(String name) {

        String geocodeUrl = "https://maps.googleapis.com/maps/api/geocode/json?address=New+York&key=" + API_KEY;
        GoogleGeocodeResponse geoResponse = restTemplate.getForObject(geocodeUrl, GoogleGeocodeResponse.class);

        if (geoResponse == null || geoResponse.results == null || geoResponse.results.isEmpty()) {
            return new ArrayList<>();
        }

        double lat = geoResponse.results.get(0).geometry.location.lat;
        double lng = geoResponse.results.get(0).geometry.location.lng;

        String placesUrl = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="
                + lat + "," + lng + "&radius=5000&keyword=" + name + "&key=" + API_KEY;

        GooglePlacesResponse placesResponse = restTemplate.getForObject(placesUrl, GooglePlacesResponse.class);
        return mapPlacesToCoffeeShops(placesResponse);
    }

    @Override
    public List<CoffeeShop> getAllCoffeeShops() {
        return getCoffeeShopsByLocation("New York");
    }

    private List<CoffeeShop> mapPlacesToCoffeeShops(GooglePlacesResponse response) {
        List<CoffeeShop> coffeeShops = new ArrayList<>();
        if (response != null && response.results != null) {
            for (GooglePlaceResult result : response.results) {
                CoffeeShop shop = new CoffeeShop();
                shop.setId(result.place_id.hashCode());
                shop.setName(result.name);
                shop.setLocation(result.vicinity);
                shop.setDescription("Imported from Google Places");
                coffeeShops.add(shop);
            }
        }
        return coffeeShops;
    }

    // Stubbed or unsupported methods

    @Override
    public CoffeeShop getCoffeeShopById(int id) {
        return null;
    }

    @Override
    public List<CoffeeShop> getFavoriteCoffeeShops(int userId) {
        return new ArrayList<>();
    }

    @Override
    public CoffeeShop addCoffeeShopToFavorites(int userId, CoffeeShop coffeeShop) {
        return null;
    }

    @Override
    public CoffeeShop removeCoffeeShopFromFavorites(int userId, int coffeeShopId) {
        return null;
    }

    @Override
    public CoffeeShop createCoffeeShop(CoffeeShop coffeeShop) {
        return null;
    }

    @Override
    public CoffeeShop updateCoffeeShop(CoffeeShop coffeeShop) {
        return null;
    }

    @Override
    public boolean deleteCoffeeShopById(int id) {
        return false;
    }
}