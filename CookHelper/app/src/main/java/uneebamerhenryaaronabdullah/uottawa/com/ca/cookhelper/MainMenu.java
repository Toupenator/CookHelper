package uneebamerhenryaaronabdullah.uottawa.com.ca.cookhelper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class MainMenu extends AppCompatActivity {

    //Variable Declaration
    public static ArrayList<RecipeDetails> recipes = new ArrayList<RecipeDetails>();

    private Button bAddRecipe;
    private Button bFindRecipe;
    private Button bShoppingList;
    private Button bHelpPage;
    private Button bLogout;

    private FirebaseAuth authentication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        //Getting inputs from activity
        bAddRecipe = (Button) findViewById(R.id.bAddRecipe);
        bFindRecipe = (Button) findViewById(R.id.bFindRecipe);
        bShoppingList = (Button) findViewById(R.id.bShoppingList);
        bHelpPage = (Button) findViewById(R.id.bHelpButton);
        bLogout = (Button) findViewById(R.id.bLogout);

        authentication = FirebaseAuth.getInstance();

        FirebaseUser user = authentication.getCurrentUser();

        if(user == null) { //User is not logged in
            finish();
            startActivity(new Intent(this, LoginScreen.class));
        }


        //OnClickListeners
        bAddRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainMenu.this, AddRecipe.class));
            }
        });

        bFindRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainMenu.this, FindRecipe.class));
            }
        });

        bShoppingList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainMenu.this, ShoppingList.class));
            }
        });

        bHelpPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainMenu.this, helpActivity.class));
            }
        });

        bLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                authentication.signOut();
                finish();
                startActivity(new Intent(MainMenu.this, LoginScreen.class));
            }
        });
    }
}
