package com.example.lab_1_2_vergeldelacruz_835208_android;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.lab_1_2_vergeldelacruz_835208_android.entity.Product;
import com.example.lab_1_2_vergeldelacruz_835208_android.util.ProductRoomDatabase;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ProductRoomDatabase productRoomDatabase;
    private List<Product> productList;
    private ListView lv_products;
    private TextView tv_total_product_count;
    private Button btn_add_product;
    private Button btn_search_product;
    private EditText et_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        productRoomDatabase = ProductRoomDatabase.getInstance(this);
        lv_products = findViewById(R.id.lv_products);
        tv_total_product_count =  findViewById(R.id.tv_total_product_count);
        btn_add_product = findViewById(R.id.btn_add_product);
        btn_add_product.setOnClickListener(this);
        btn_search_product = findViewById(R.id.btn_search_product);
        btn_search_product.setOnClickListener(this);
        et_search = findViewById(R.id.et_search);
        loadProducts();
    }
    private void loadProducts() {
        productList = productRoomDatabase.productDao().getAllProducts();
        tv_total_product_count.setText("Total Product Count: " + productList.size());
        ProductAdapter productAdapter = new ProductAdapter(this,R.layout.list_layout_product,
                productList);
        lv_products.setAdapter(productAdapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_add_product:
                addProduct();
                break;
            case R.id.btn_search_product:
                searchProducts();
        }
    }
    public void addProduct() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View view = layoutInflater.inflate(R.layout.dialog_add_product,null);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        EditText nameET = view.findViewById(R.id.et_name);
        EditText descriptionET = view.findViewById(R.id.et_description);
        EditText priceET = view.findViewById(R.id.et_price);
        EditText latitudeET = view.findViewById(R.id.et_latitude);
        EditText longitudeET = view.findViewById(R.id.et_longitude);


        view.findViewById(R.id.btn_add_product).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameET.getText().toString().trim();
                String description = descriptionET.getText().toString().trim();
                String price = priceET.getText().toString().trim();
                String latitude = latitudeET.getText().toString().trim();
                String longitude = longitudeET.getText().toString().trim();

                if (name.isEmpty()) {
                    nameET.setError("name field is empty");
                    nameET.requestFocus();
                    return;
                }
                if (description.isEmpty()) {
                    descriptionET.setError("description field is empty");
                    descriptionET.requestFocus();
                    return;
                }
                if (price.isEmpty()) {
                    priceET.setError("price field is empty");
                    priceET.requestFocus();
                    return;
                }
                if (latitude.isEmpty()) {
                    latitudeET.setError("latitude field is empty");
                    latitudeET.requestFocus();
                    return;
                }
                if (longitude.isEmpty()) {
                    longitudeET.setError("longitude field is empty");
                    longitudeET.requestFocus();
                    return;
                }
                Product product = new Product(name,description,
                        latitude,longitude,Double.parseDouble(price));

                productRoomDatabase.productDao().insertProduct(product);
                loadProducts();
                alertDialog.dismiss();
            }
        });
    }
    public void searchProducts() {
        productList = productRoomDatabase.productDao().getAllProductsMatchingNameOrDesc(et_search.getText().toString());
        tv_total_product_count.setText("Total Product Count: " + productList.size());
        ProductAdapter productAdapter = new ProductAdapter(this,R.layout.list_layout_product,
                productList);
        lv_products.setAdapter(productAdapter);
    }
}