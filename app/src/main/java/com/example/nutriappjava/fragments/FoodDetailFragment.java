package com.example.nutriappjava.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.example.nutriappjava.R;
import com.example.nutriappjava.entities.Food;
import com.example.nutriappjava.viewmodels.FoodDetailViewModel;

/**
 * Displays detailed information about a specific food item, including its nutritional content.
 * This fragment observes data from a {@link FoodDetailViewModel} to fetch and display information about the food item identified by a unique ID.
 */
public class FoodDetailFragment extends Fragment {
    private static final String ARG_FOOD_ID = "food_id";
    private long foodId;
    private FoodDetailViewModel foodDetailViewModel;

    private TextView description, category, nutrientDataBankNumber, alphaCarotene, betaCarotene,
            betaCryptoxanthin, carbohydrate, cholesterol, choline, fiber, luteinAndZeaxanthin,
            lycopene, niacin, protein, retinol, riboflavin, selenium, sugarTotal, thiamin, water,
            monosaturatedFat, polysaturatedFat, saturatedFat, totalLipid, calcium, copper, iron,
            magnesium, phosphorus, potassium, sodium, zinc, vitaminARae, vitaminB12, vitaminB6,
            vitaminC, vitaminE, vitaminK, calories;

    public static FoodDetailFragment newInstance(long foodId) {
        FoodDetailFragment fragment = new FoodDetailFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_FOOD_ID, foodId);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_food_detail, container, false);

        description = view.findViewById(R.id.food_description);
        category = view.findViewById(R.id.food_category);
        nutrientDataBankNumber = view.findViewById(R.id.food_nutrient_data_bank_number);
        alphaCarotene = view.findViewById(R.id.food_alpha_carotene);
        betaCarotene = view.findViewById(R.id.food_beta_carotene);
        betaCryptoxanthin = view.findViewById(R.id.food_beta_cryptoxanthin);
        carbohydrate = view.findViewById(R.id.food_carbohydrate);
        cholesterol = view.findViewById(R.id.food_cholesterol);
        choline = view.findViewById(R.id.food_choline);
        fiber = view.findViewById(R.id.food_fiber);
        luteinAndZeaxanthin = view.findViewById(R.id.food_lutein_and_zeaxanthin);
        lycopene = view.findViewById(R.id.food_lycopene);
        niacin = view.findViewById(R.id.food_niacin);
        protein = view.findViewById(R.id.food_protein);
        retinol = view.findViewById(R.id.food_retinol);
        riboflavin = view.findViewById(R.id.food_riboflavin);
        selenium = view.findViewById(R.id.food_selenium);
        sugarTotal = view.findViewById(R.id.food_sugar_total);
        thiamin = view.findViewById(R.id.food_thiamin);
        water = view.findViewById(R.id.food_water);
        monosaturatedFat = view.findViewById(R.id.food_monosaturated_fat);
        polysaturatedFat = view.findViewById(R.id.food_polysaturated_fat);
        saturatedFat = view.findViewById(R.id.food_saturated_fat);
        totalLipid = view.findViewById(R.id.food_total_lipid);
        calcium = view.findViewById(R.id.food_calcium);
        copper = view.findViewById(R.id.food_copper);
        iron = view.findViewById(R.id.food_iron);
        magnesium = view.findViewById(R.id.food_magnesium);
        phosphorus = view.findViewById(R.id.food_phosphorus);
        potassium = view.findViewById(R.id.food_potassium);
        sodium = view.findViewById(R.id.food_sodium);
        zinc = view.findViewById(R.id.food_zinc);
        vitaminARae = view.findViewById(R.id.food_vitamin_a_rae);
        vitaminB12 = view.findViewById(R.id.food_vitamin_b12);
        vitaminB6 = view.findViewById(R.id.food_vitamin_b6);
        vitaminC = view.findViewById(R.id.food_vitamin_c);
        vitaminE = view.findViewById(R.id.food_vitamin_e);
        vitaminK = view.findViewById(R.id.food_vitamin_k);
        calories = view.findViewById(R.id.food_calories);

        if (getArguments() != null) {
            foodId = getArguments().getLong(ARG_FOOD_ID);
        }

        foodDetailViewModel = new ViewModelProvider(this).get(FoodDetailViewModel.class);
        foodDetailViewModel.setFoodId(foodId);

        foodDetailViewModel.getFoodLiveData().observe(getViewLifecycleOwner(), new Observer<Food>() {
            @Override
            public void onChanged(Food food) {
                if (food != null) {
                    description.setText(food.getDescription());
                    category.setText("Category: " + food.getCategory());
                    nutrientDataBankNumber.setText("Nutrient Data Bank Number: " + food.getNutrientDataBankNumber());
                    alphaCarotene.setText("Alpha Carotene: " + food.getAlphaCarotene());
                    betaCarotene.setText("Beta Carotene: " + food.getBetaCarotene());
                    betaCryptoxanthin.setText("Beta Cryptoxanthin: " + food.getBetaCryptoxanthin());
                    carbohydrate.setText("Carbohydrate: " + food.getCarbohydrate() + "g");
                    cholesterol.setText("Cholesterol: " + food.getCholesterol());
                    choline.setText("Choline: " + food.getCholine() + "mg");
                    fiber.setText("Fiber: " + food.getFiber() + "g");
                    luteinAndZeaxanthin.setText("Lutein and Zeaxanthin: " + food.getLuteinAndZeaxanthin());
                    lycopene.setText("Lycopene: " + food.getLycopene());
                    niacin.setText("Niacin: " + food.getNiacin() + "mg");
                    protein.setText("Protein: " + food.getProtein() + "g");
                    retinol.setText("Retinol: " + food.getRetinol());
                    riboflavin.setText("Riboflavin: " + food.getRiboflavin() + "mg");
                    selenium.setText("Selenium: " + food.getSelenium() + "µg");
                    sugarTotal.setText("Sugar Total: " + food.getSugarTotal() + "g");
                    thiamin.setText("Thiamin: " + food.getThiamin() + "mg");
                    water.setText("Water: " + food.getWater() + "g");
                    monosaturatedFat.setText("Monosaturated Fat: " + food.getMonosaturatedFat() + "g");
                    polysaturatedFat.setText("Polysaturated Fat: " + food.getPolysaturatedFat() + "g");
                    saturatedFat.setText("Saturated Fat: " + food.getSaturatedFat() + "g");
                    totalLipid.setText("Total Lipid: " + food.getTotalLipid() + "g");
                    calcium.setText("Calcium: " + food.getCalcium() + "mg");
                    copper.setText("Copper: " + food.getCopper() + "mg");
                    iron.setText("Iron: " + food.getIron() + "mg");
                    magnesium.setText("Magnesium: " + food.getMagnesium() + "mg");
                    phosphorus.setText("Phosphorus: " + food.getPhosphorus() + "mg");
                    potassium.setText("Potassium: " + food.getPotassium() + "mg");
                    sodium.setText("Sodium: " + food.getSodium() + "mg");
                    zinc.setText("Zinc: " + food.getZinc() + "mg");
                    vitaminARae.setText("Vitamin A RAE: " + food.getVitaminARae());
                    vitaminB12.setText("Vitamin B12: " + food.getVitaminB12() + "µg");
                    vitaminB6.setText("Vitamin B6: " + food.getVitaminB6() + "mg");
                    vitaminC.setText("Vitamin C: " + food.getVitaminC() + "mg");
                    vitaminE.setText("Vitamin E: " + food.getVitaminE() + "mg");
                    vitaminK.setText("Vitamin K: " + food.getVitaminK() + "µg");
                    calories.setText("Calories: " + calculateCalories(food) + " kcal");
                }
            }
        });

        return view;
    }

    /**
     * Calculates the total calories of the food item based on its protein, carbohydrate, and lipid content.
     *
     * @param food The {@link Food} object containing the nutritional data of the food item
     * @return The calculated total calories as a string
     */
    private String calculateCalories(Food food) {
        float calories = (food.getProtein() * 4) + (food.getCarbohydrate() * 4) + (food.getTotalLipid() * 9);
        return String.valueOf(calories);
    }
}