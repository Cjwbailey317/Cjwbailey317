package com.techelevator.dao;

import com.techelevator.model.CoffeeShop;
import java.util.List;

    public interface CoffeeShopDao {

        List<CoffeeShop> getAllCoffeeShops();

        List<CoffeeShop> getCoffeeShopsByLocation(String location);

        List<CoffeeShop> getCoffeeShopsByName(String name);

        CoffeeShop getCoffeeShopById(int id);

        List<CoffeeShop> getFavoriteCoffeeShops(int userId);

        CoffeeShop addCoffeeShopToFavorites(int userId, CoffeeShop coffeeShop);

        CoffeeShop removeCoffeeShopFromFavorites(int userId, int coffeeShopId);

        CoffeeShop createCoffeeShop(CoffeeShop coffeeShop);

        CoffeeShop updateCoffeeShop(CoffeeShop coffeeShop);

        boolean deleteCoffeeShopById(int id);
}