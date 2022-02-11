package com.example.lab_1_2_vergeldelacruz_835208_android.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.lab_1_2_vergeldelacruz_835208_android.entity.Product;

import java.util.List;

@Dao
public interface ProductDao {
    @Insert
    void insertProduct(Product product);

    @Query("DELETE FROM product")
    void deleteAllProducts();

    @Query("DELETE FROM product where id = :id")
    void deleteProduct(int id);

    @Query("UPDATE product SET name = :name, description = :description, latitude = :latitude, longitude = :longitude, price = :price  WHERE id = :id")
    int updateProduct(int id, String name, String description, String latitude, String longitude, double price);

    @Query("SELECT * FROM product ORDER BY name")
    List<Product> getAllProducts();

    @Query("SELECT * FROM product WHERE name like :search or description like :search ORDER BY name")
    List<Product> getAllProductsMatchingNameOrDesc(String search);
}
