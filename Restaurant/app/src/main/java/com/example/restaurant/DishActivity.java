package com.example.restaurant;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;


import android.view.Gravity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;


public class DishActivity extends AppCompatActivity {
    private ArrayList<Dish> Dishes = new ArrayList<>();
    static int checkboxes1[];
    static int dishesOfCuisines[] = new int[4];

    void addDishes(Cuisine C, int noOfDishes) {
        dishesOfCuisines[C.ordinal()] = noOfDishes;
        for (int i = 0; i < noOfDishes; i++) {
            this.Dishes.add(new Dish(C, C.toString().toLowerCase() + (i + 1), (i + 1) * 50));
        }
    }

    private static int findTotalNoOfDishes() {
        int totalNoOfDishes = 0;
        for (int i = 0; i < 4; i++)
            totalNoOfDishes += dishesOfCuisines[i];
        return totalNoOfDishes;
    }

    void showSummary(int noOfDishesSelected, StringBuilder selectedDishes, int totalPrice) {
        new AlertDialog.Builder(this)
                .setTitle("Summary")
                .setMessage(noOfDishesSelected + " Dishes selected:\n" + selectedDishes + "\nTotal Price: " + totalPrice)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .create()
                .show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dish);
        final TableLayout tableLayout = findViewById(R.id.table_parent);
        Intent i = getIntent();
        checkboxes1 = i.getIntArrayExtra("checkboxes");
        this.addDishes(Cuisine.CHINESE, 1);
        this.addDishes(Cuisine.ITALIAN, 2);
        this.addDishes(Cuisine.INDIAN, 3);
        this.addDishes(Cuisine.ARABIAN, 4);
        int noOfDishesToBeDisplayed = findTotalNoOfDishes();
        int sno = 1;
        for (int n = 0; n < noOfDishesToBeDisplayed; n++) {
            if (checkboxes1[Dishes.get(n).getCuisine().ordinal()] == 1) {
                TableRow row = new TableRow(this);
                TextView serialNo = new TextView(this);
                RadioButton radio = new RadioButton(this);
                TextView price = new TextView(this);
                serialNo.setTypeface(null, Typeface.BOLD);
                serialNo.setText(Integer.toString(sno++));
                radio.setId(View.generateViewId());
                radio.setText(Dishes.get(n).getDish());
                price.setText(Integer.toString(Dishes.get(n).getPrice()));
                serialNo.setGravity(Gravity.CENTER);
                radio.setGravity(Gravity.CENTER);
                price.setGravity(Gravity.CENTER);
                row.addView(serialNo);
                row.addView(radio);
                row.addView(price);
                tableLayout.addView(row);
            }
        }
        findViewById(R.id.closeBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.showPriceBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int childCount = tableLayout.getChildCount();
                StringBuilder selectedDishes = new StringBuilder();
                int totalPrice = 0;
                int noOfDishesSelected = 0;
                for (int i = 0; i < childCount; i++) {
                    TableRow row = (TableRow) tableLayout.getChildAt(i + 2);
                    if (row == null)
                        break;
                    RadioButton radio = (RadioButton) row.getChildAt(1);
                    if (radio.isChecked()) {
                        selectedDishes.append(radio.getText().toString()).append("\n");
                        TextView priceView = (TextView) row.getChildAt(2);
                        String priceString = priceView.getText().toString();
                        int price = Integer.parseInt(priceString);
                        totalPrice += price;
                        noOfDishesSelected++;
                    }
                }
                showSummary(noOfDishesSelected, selectedDishes, totalPrice);
            }
        });
    }
}
