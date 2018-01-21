package uneebamerhenryaaronabdullah.uottawa.com.ca.cookhelper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class FindRecipe extends AppCompatActivity {

    private EditText etIngredients;
    private Button bSearch, bCancel;
    private Spinner sCategory, sEthnicity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_recipe);

        etIngredients = (EditText) findViewById(R.id.etIngredients);
        bSearch = (Button) findViewById(R.id.bSearch);
        bCancel = (Button) findViewById(R.id.bCancel);
        sEthnicity = (Spinner) findViewById(R.id.sEthnicity);
        sCategory = (Spinner) findViewById(R.id.sCategory);

        bSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchRecipe();
            }
        });
    }

    private void searchRecipe() {
        String ingredients = etIngredients.getText().toString().trim();
        String category = sCategory.getSelectedItem().toString().trim();
        String ethnicity = sEthnicity.getSelectedItem().toString().trim();

        Intent intent = new Intent(FindRecipe.this, SearchResults.class);
        intent.putExtra("ingredients", ingredients);
        intent.putExtra("category", category);
        intent.putExtra("ethnicity", ethnicity);

        startActivity(intent);
    }
}
