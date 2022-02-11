package com.example.lab_1_2_vergeldelacruz_835208_android;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.lab_1_2_vergeldelacruz_835208_android.entity.Product;
import com.example.lab_1_2_vergeldelacruz_835208_android.util.ProductRoomDatabase;

import java.util.Arrays;
import java.util.List;

public class ProductAdapter extends ArrayAdapter {
    Context context;
    int layoutRes;
    List<Product> productList;
    ProductRoomDatabase productRoomDatabase;

    public ProductAdapter(@NonNull Context context, int resource,
                          @NonNull List<Product> productList) {
        super(context, resource, productList);
        this.context = context;
        this.layoutRes = resource;
        this.productList = productList;
        productRoomDatabase = ProductRoomDatabase.getInstance(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = convertView;
        if (view == null) {
            view = layoutInflater.inflate(layoutRes, null);
        }
        TextView nameTV =  view.findViewById(R.id.row_name);
        TextView descriptionTV =  view.findViewById(R.id.row_description);
        TextView priceTV =  view.findViewById(R.id.row_price);

        Product product = productList.get(position);
        nameTV.setText(product.getName());
        descriptionTV.setText(product.getDescription());
        priceTV.setText(String.valueOf(product.getPrice()));

        view.findViewById(R.id.btn_update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateProduct(product);
            }
        });
        view.findViewById(R.id.btn_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteProduct(product);
            }
        });
        view.findViewById(R.id.btn_view_map).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewProductInMap(product);
            }
        });

        return view;
    }
    public void viewProductInMap(Product product) {
        /*
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.dialog_map_product,null);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        alertDialog.show(); */
        Intent i = new Intent(getContext(),ProductMapsActivity.class);
        i.putExtra("name",product.getName());
        i.putExtra("latitude",product.getLatitude());
        i.putExtra("longitude",product.getLongitude());
        getContext().startActivity(i);
    }

    public void updateProduct(Product product) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.dialog_update_product,null);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        EditText nameET = view.findViewById(R.id.et_name);
        EditText descriptionET = view.findViewById(R.id.et_description);
        EditText priceET = view.findViewById(R.id.et_price);
        EditText latitudeET = view.findViewById(R.id.et_latitude);
        EditText longitudeET = view.findViewById(R.id.et_longitude);

        nameET.setText(product.getName());
        descriptionET.setText(String.valueOf(product.getDescription()));
        priceET.setText(String.valueOf(product.getPrice()));
        latitudeET.setText(product.getLatitude());
        longitudeET.setText(product.getLongitude());

        view.findViewById(R.id.btn_update_product).setOnClickListener(new View.OnClickListener() {
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
                productRoomDatabase.productDao().updateProduct(product.getId(),name,description,
                        latitude,longitude,Double.parseDouble(price));
                loadProducts();
                alertDialog.dismiss();
            }
        });
    }

    private void deleteProduct(Product product) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Are you sure ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                productRoomDatabase.productDao().deleteProduct(product.getId());
                loadProducts();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(context,"The product is not deleted", Toast.LENGTH_LONG).show();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    private void loadProducts() {
        productList = productRoomDatabase.productDao().getAllProducts();
        notifyDataSetChanged();
    }
}
