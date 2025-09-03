package com.techelevator.dao;

import com.techelevator.model.CoffeeShop;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;


//TODO: Consider adding Service layer for Jdbc and API

@Component
@Primary
public class JdbcCoffeeShopDao implements CoffeeShopDao{

    private final JdbcTemplate jdbcTemplate;

    public JdbcCoffeeShopDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<CoffeeShop> getAllCoffeeShops() {
        String sql = "SELECT id, name, description, location FROM coffee_shops ORDER BY name";

        List<CoffeeShop> coffeeShops = new ArrayList<>();

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while (results.next()) {
                coffeeShops.add(mapRowToCoffeeShop(results));
            }
        } catch (Exception e) {
            System.out.println("Error getting coffee shops: " + e.getMessage());
        }

        return coffeeShops;
    }

    @Override
    public List<CoffeeShop> getCoffeeShopsByName(String name) {

        String sql = "SELECT id, name, description, location FROM coffee_shops WHERE name ILIKE ? ORDER BY name";

        List<CoffeeShop> coffeeShops = new ArrayList<>();

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, "%" + name + "%");

        while (results.next()) {
            coffeeShops.add(mapRowToCoffeeShop(results));
        }

        return coffeeShops;
    }


    @Override
    public List<CoffeeShop> getCoffeeShopsByLocation(String location) {
        String sql = "SELECT id, name, description, location FROM coffee_shops WHERE location ILIKE ?";

        List<CoffeeShop> coffeeShops = new ArrayList<>();

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, "%" + location + "%");
            while (results.next()) {
                coffeeShops.add(mapRowToCoffeeShop(results));
            }
        } catch (Exception e) {
            System.out.println("Error getting coffee shops by location: " + e.getMessage());
        }

        return coffeeShops;
    }

    @Override
    public List<CoffeeShop> getFavoriteCoffeeShops(int userId) {
        String sql = "SELECT cs.id, cs.name, cs.description, cs.location " +
                "FROM coffee_shops cs " +
                "JOIN user_favorites uf ON cs.id = uf.coffee_shop_id " +
                "WHERE uf.user_id = ?";

        List<CoffeeShop> coffeeShops = new ArrayList<>();

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
            while (results.next()) {
                coffeeShops.add(mapRowToCoffeeShop(results));
            }
        } catch (Exception e) {
            System.out.println("Error getting favorite coffee shops: " + e.getMessage());
        }

        return coffeeShops;
    }


    @Override
    public CoffeeShop getCoffeeShopById(int coffeeShopId) {
        String sql = "SELECT id, name, description, location FROM coffee_shops WHERE id = ?";

        CoffeeShop coffeeShop = null;
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, coffeeShopId);

        if (results.next()) {
            coffeeShop = mapRowToCoffeeShop(results);
        }
        return coffeeShop;
    }

    @Override
    public CoffeeShop addCoffeeShopToFavorites(int userId, CoffeeShop coffeeShop) {
        String sql = "INSERT INTO user_favorites (user_id, coffee_shop_id) VALUES (?, ?) RETURNING coffee_shop_id";

        CoffeeShop updatedCoffeeShop = null;

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId, coffeeShop.getId());
            if (results.next()) {
                return coffeeShop;
            }
        } catch (Exception e) {
            System.out.println("Error adding coffee shop to favorites: " + e.getMessage());
        }

        return null;
    }

    @Override
    public CoffeeShop removeCoffeeShopFromFavorites(int userId, int coffeeShopId) {
        String deleteSql = "DELETE FROM user_favorites WHERE user_id = ? AND coffee_shop_id = ?";
        String selectSql = "SELECT id, name, description, location FROM coffee_shops WHERE id = ?";

        try {
            int rowsDeleted = jdbcTemplate.update(deleteSql, userId, coffeeShopId);
            if (rowsDeleted > 0) {
                SqlRowSet results = jdbcTemplate.queryForRowSet(selectSql, coffeeShopId);
                if (results.next()) {
                    return mapRowToCoffeeShop(results);
                }
            }
        } catch (Exception e) {
            System.out.println("Error removing coffee shop from favorites: " + e.getMessage());
        }

        return null;
    }

    @Override
    public CoffeeShop updateCoffeeShop(CoffeeShop coffeeShop) {
        String sql = "UPDATE coffee_shops SET name = ?, description = ?, location = ? WHERE id = ? RETURNING id, name, description, location";

        CoffeeShop updatedCoffeeShop = null;

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, coffeeShop.getName(), coffeeShop.getDescription(),
                    coffeeShop.getLocation(), coffeeShop.getId());
            if (results.next()) {
                updatedCoffeeShop = mapRowToCoffeeShop(results);
            }
        } catch (Exception e) {
            System.out.println("Error updating coffee shop: " + e.getMessage());
        }

        return updatedCoffeeShop;
    }

    @Override
    public boolean deleteCoffeeShopById(int id) {
        String sql = "DELETE FROM coffee_shops WHERE id = ?";

        int rowsAffected = jdbcTemplate.update(sql, id);

        return rowsAffected > 0;
    }


    @Override
    public CoffeeShop createCoffeeShop(CoffeeShop coffeeShop) {
        String sql = "INSERT INTO coffee_shops (name, description, location) VALUES (?, ?, ?) RETURNING id";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, coffeeShop.getName(), coffeeShop.getDescription(),
                coffeeShop.getLocation());

        if (results.next()) {
            coffeeShop.setId(results.getInt("id"));
            return coffeeShop;
        }

        return null;
    }



    private CoffeeShop mapRowToCoffeeShop(SqlRowSet resultSet) {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        String description = resultSet.getString("description");
        String location = resultSet.getString("location");
        return new CoffeeShop(id, name, description, location);
    }
}
