package com.techelevator.controller;


import com.techelevator.dao.CoffeeShopDao;
import com.techelevator.dao.UserDao;
import com.techelevator.model.CoffeeShop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import jakarta.validation.Valid;

import java.security.Principal;
import java.util.List;



//TODO: update authorizations for each methods

@CrossOrigin
@RestController
@RequestMapping("/coffeeshops") //TODO: double check this once API is integrated
@PreAuthorize("isAuthenticated()")
public class CoffeeShopController {

    private final CoffeeShopDao coffeeShopDao;
    private UserDao userDao;

@Autowired
    public CoffeeShopController(CoffeeShopDao coffeeShopDAO, UserDao userDao) {
        this.coffeeShopDao = coffeeShopDAO;
        this.userDao = userDao;
        }

    @RequestMapping(path = "", method = RequestMethod.GET)
    public List<CoffeeShop> getAllCoffeeShops() {
        return coffeeShopDao.getAllCoffeeShops();
    }


    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public CoffeeShop getCoffeeShop(@PathVariable int id) {
        CoffeeShop coffeeShop = coffeeShopDao.getCoffeeShopById(id);
        if (coffeeShop == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Coffee Shop Not Found");
        }
        return coffeeShop;
    }


    @RequestMapping(path = "", method = RequestMethod.POST)
    public CoffeeShop createCoffeeShop(@RequestBody CoffeeShop newCoffeeShop) {
        return coffeeShopDao.createCoffeeShop(newCoffeeShop);
    }


    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    public CoffeeShop updateCoffeeShop(@RequestBody CoffeeShop updatedCoffeeShop, @PathVariable int id) {
        updatedCoffeeShop.setId(id);
        CoffeeShop coffeeShop = coffeeShopDao.updateCoffeeShop(updatedCoffeeShop);
        if (coffeeShop == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Coffee Shop Not Found");
        }
        return coffeeShop;
    }


    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public void deleteCoffeeShop(@PathVariable int id) {
        boolean deleted = coffeeShopDao.deleteCoffeeShopById(id);
        if (!deleted) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Coffee Shop Not Found");
        }
    }


    @RequestMapping(path = "/search", method = RequestMethod.GET)
    public List<CoffeeShop> searchCoffeeShopsByName(@RequestParam String name) {
        return coffeeShopDao.getCoffeeShopsByName(name);
    }

    @RequestMapping(path = "/location", method = RequestMethod.GET)
    public List<CoffeeShop> getCoffeeShopsByLocation(@RequestParam String locationName) {
        return coffeeShopDao.getCoffeeShopsByLocation(locationName);
    }

    @RequestMapping(path = "/{id}/favorite", method = RequestMethod.POST)
    public void addToFavorites(@PathVariable int id, @RequestParam boolean isFavorite, Principal principal) {
        int userId = userDao.getUserByUsername(principal.getName()).getId();

        CoffeeShop coffeeShop = coffeeShopDao.getCoffeeShopById(id);
        if (coffeeShop == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Coffee shop not found.");
        }

        if (isFavorite) {
            CoffeeShop added = coffeeShopDao.addCoffeeShopToFavorites(userId, coffeeShop);
            if (added == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Coffee shop could not be added to favorites.");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid favorite status.");
        }
    }

    @RequestMapping(path = "/{id}/favorite", method = RequestMethod.DELETE)
    public void removeFromFavorites(@PathVariable int id, Principal principal) {
        int userId = userDao.getUserByUsername(principal.getName()).getId();

        CoffeeShop coffeeShop = coffeeShopDao.getCoffeeShopById(id);
        if (coffeeShop == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Coffee shop not found.");
        }

        CoffeeShop removedCoffeeShop = coffeeShopDao.removeCoffeeShopFromFavorites(userId, id);
        if (removedCoffeeShop == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Coffee shop not found in favorites or could not be removed.");
        }
    }


    @RequestMapping(path = "/favorites", method = RequestMethod.GET)
    public List<CoffeeShop> getFavoriteCoffeeShops(Principal principal) {
        int userId = userDao.getUserByUsername(principal.getName()).getId();
        return coffeeShopDao.getFavoriteCoffeeShops(userId);
    }
}