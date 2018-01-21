package uneebamerhenryaaronabdullah.uottawa.com.ca.cookhelper;

import java.util.ArrayList;

/**
 * Created by Uneeb on 05/12/2016.
 */

public class RecipeDetails {

    public String recipeName, category, ethnicity,ingr;
    public double prepTime;
    public ArrayList<Ingredient> ingredients;
    public ArrayList<Instruction> steps;

    public RecipeDetails(String recipeName, String category, String ethnicity, double prepTime,
                         ArrayList<Ingredient> ingredients, ArrayList<Instruction> steps) {
        this.recipeName = recipeName;
        this.category = category;
        this.ethnicity = ethnicity;
        this.prepTime = prepTime;
        this.ingredients = ingredients;
        this.steps = steps;
        this.ingr = this.ingredients.get(0).getName();
    }
}
