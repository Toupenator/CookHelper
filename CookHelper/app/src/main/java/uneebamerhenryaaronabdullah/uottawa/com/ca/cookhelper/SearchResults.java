package uneebamerhenryaaronabdullah.uottawa.com.ca.cookhelper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class SearchResults extends AppCompatActivity {

    ListView lvResults;
    ArrayAdapter adapter;
    ArrayList<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        list = new ArrayList<String>();
        lvResults = (ListView) findViewById(R.id.lvResults);
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);

        lvResults.setAdapter(adapter);

        Intent intent = getIntent();
        String ingredients = intent.getStringExtra("ingredients");
        String category = intent.getStringExtra("category");
        String ethnicity = intent.getStringExtra("ethnicity");

        if(ingredients.equals("")) { //If no ingredients are specified
            for(RecipeDetails r : MainMenu.recipes) {
                adapter.add(r.recipeName);
                adapter.notifyDataSetChanged();
            }
        }

        else {
            StringTokenizer ingredientTokenizer = new StringTokenizer(ingredients, "OR");
            ArrayList<RecipeDetails> temp = new ArrayList<RecipeDetails>();
            for(RecipeDetails r : MainMenu.recipes) {
                for(Ingredient i : r.ingredients) {
                    String name = i.getName();
                    while(ingredientTokenizer.hasMoreTokens()) {
                        if(name.equalsIgnoreCase(ingredientTokenizer.nextToken().trim())) {
                            temp.add(r);
                            break;
                        }
                    }
                }
            }
            if(ethnicity != "All") { //If Ethnicity is specified
                for(RecipeDetails r : MainMenu.recipes) {
                    if(r.ethnicity.equals(ethnicity)) {
                        adapter.add(r.recipeName);
                        adapter.notifyDataSetChanged();
                    }
                }
            }
            else if(category != "All") { //Or Category is specified
                for(RecipeDetails r : MainMenu.recipes) {
                    if(r.category.equals(category)) {
                        adapter.add(r.recipeName);
                        adapter.notifyDataSetChanged();
                    }
                }
            }
            else { //If neither are specified, display all recipes with the ingredients
                for(RecipeDetails r : MainMenu.recipes) {
                    adapter.add(r.recipeName);
                    adapter.notifyDataSetChanged();
                }
            }
        }
    }
}
