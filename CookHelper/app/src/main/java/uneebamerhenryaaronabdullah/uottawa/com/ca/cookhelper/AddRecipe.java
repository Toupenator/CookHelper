package uneebamerhenryaaronabdullah.uottawa.com.ca.cookhelper;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AddRecipe extends AppCompatActivity {

    private Spinner sEthnicity, sCategory;
    private Button bCreate, bCancel;
    private ListView lvIngredients, lvSteps;
    private IngredientAdapter ingredientAdapter;
    private ImageButton ibAddIngredient, ibAddSteps;
    private ArrayAdapter stepsAdapter;
    private EditText etAddNewStep, etRecipeName,etPrepTime;
    private int stepCounter;

    private ArrayList<Ingredient> ingredients;
    private ArrayList<String> stepString;
    private ArrayList<Instruction> stepAll;

    private FirebaseAuth authentication;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        authentication = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        stepCounter = 1; //Keep track of which direction for the steps you are on
        bCancel = (Button) findViewById(R.id.bCancel);
        bCreate = (Button) findViewById(R.id.bCreate);
        etAddNewStep = (EditText) findViewById(R.id.etAddNewStep);
        sEthnicity = (Spinner) findViewById(R.id.sEthnicity);
        sCategory = (Spinner) findViewById(R.id.sCategory);
        etRecipeName = (EditText) findViewById(R.id.etRecipeName);
        etPrepTime = (EditText) findViewById(R.id.etPrepTime);
        ibAddIngredient = (ImageButton) findViewById(R.id.ibAddIngredient);
        ibAddSteps = (ImageButton) findViewById(R.id.ibAddSteps);
        lvIngredients = (ListView) findViewById(R.id.lvIngredients);
        lvIngredients.setOnTouchListener(new View.OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        bCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveRecipe();
            }
        });

        lvSteps = (ListView) findViewById(R.id.lvSteps);
        lvSteps.setOnTouchListener(new View.OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        //IngredientsAdapter
        ingredients = new ArrayList<Ingredient>();
        ingredientAdapter = new IngredientAdapter(this, R.layout.ingredient_layout, ingredients);

        lvIngredients.setAdapter(ingredientAdapter);
        ingredients = new ArrayList<Ingredient>();

        stepString = new ArrayList<String>();
        stepAll = new ArrayList<Instruction>();
        stepsAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, stepString);
        lvSteps.setAdapter(stepsAdapter);

        ibAddIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Get data from IngredientActivity class
                startActivityForResult(new Intent(AddRecipe.this, IngredientActivity.class), 1);
            }
        });

        ibAddSteps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Get the step
                String step = etAddNewStep.getText().toString().trim();
                stepsAdapter.add(step);
                stepAll.add(new Instruction(step, stepCounter));
                stepCounter++;
                ingredientAdapter.notifyDataSetChanged();
                etAddNewStep.setText("");
            }
        });

        bCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Cancel adding a recipe
                finish();
                startActivity(new Intent(AddRecipe.this, MainMenu.class));
            }
        });
    }

    //Method called when saving recipe to database
    private void saveRecipe() {
        String name = etRecipeName.getText().toString().trim();
        double prepTime = Double.parseDouble(etPrepTime.getText().toString().trim());
        String category = sCategory.getSelectedItem().toString().trim();
        String ethnicity = sEthnicity.getSelectedItem().toString().trim();

        RecipeDetails recipe = new RecipeDetails(name, category, ethnicity, prepTime, ingredients, stepAll);

        FirebaseUser user = authentication.getCurrentUser();

        MainMenu.recipes.add(recipe);

        Toast.makeText(this, "Recipe Saved", Toast.LENGTH_LONG);
        finish();
    }

    //This method will be called after the data is retrieved from the IngredientActivity class
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1) {
            Ingredient ingredient = new Ingredient(data.getStringExtra("ingredientName"),
                    data.getStringExtra("quantity"), data.getStringExtra("unit")); //Create a new instance of Ingredient

            ingredientAdapter.add(ingredient);
            ingredients.add(ingredient);
            ingredientAdapter.notifyDataSetChanged();
        }
    }

    //Nested class for adaptor
    public class IngredientAdapter extends ArrayAdapter {

        ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();

        public IngredientAdapter(Context context, int resource, ArrayList<Ingredient> ingredients) {
            super(context, resource);
            this.ingredients = ingredients;
        }

        public void add(Ingredient ingredient) {
            ingredients.add(ingredient);
        }

        @Override
        public int getCount() {
            return ingredients.size();
        }

        @Nullable
        @Override
        public Ingredient getItem(int position) {
            return ingredients.get(position);
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View row = convertView;
            Data data = new Data();

            if(convertView == null) { //If there is already a view with the required data
                LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                row = inflater.inflate(R.layout.ingredient_layout, parent, false);
                data.ingredientName = (TextView) row.findViewById(R.id.tvIngredientName);
                data.quantity = (TextView) row.findViewById(R.id.tvQuantity);
                data.unit = (TextView) row.findViewById(R.id.tvUnit);
                row.setTag(data);
            }

            else {
                data = (Data) row.getTag();
            }

            Ingredient ingredient = getItem(position); //Ingredient the user entered
            data.ingredientName.setText(ingredient.getName());
            data.quantity.setText(ingredient.getQuantity());
            data.unit.setText(ingredient.getUnit());

            return row;
        }
    }

    //Class to store the data from ingredient_layout
    public class Data {
        TextView ingredientName, quantity, unit;
    }
}

