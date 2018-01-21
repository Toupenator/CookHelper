package uneebamerhenryaaronabdullah.uottawa.com.ca.cookhelper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class IngredientActivity extends AppCompatActivity {

    EditText etIngredientName;
    EditText etQuantity;
    EditText etUnit;
    Button bDone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient);

        etIngredientName = (EditText) findViewById(R.id.etIngredientName);
        etQuantity = (EditText) findViewById(R.id.etQuantity);
        etUnit = (EditText) findViewById(R.id.etUnit);
        bDone = (Button) findViewById(R.id.bDone);

        bDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IngredientActivity.this, AddRecipe.class);
                intent.putExtra("ingredientName", etIngredientName.getText().toString().trim());
                intent.putExtra("quantity", etQuantity.getText().toString().trim());
                intent.putExtra("unit", etUnit.getText().toString().trim());
                setResult(1, intent);
                finish();
            }
        });
    }
}
