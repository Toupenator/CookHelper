package uneebamerhenryaaronabdullah.uottawa.com.ca.cookhelper;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginScreen extends AppCompatActivity {

    private EditText etEmail;
    private EditText etPassword;
    private Button bLogin;
    private Button bRegister;
    private ProgressDialog progress;

    private FirebaseAuth authentication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        bLogin = (Button) findViewById(R.id.bLogin);
        bRegister = (Button) findViewById(R.id.bRegister);
        progress = new ProgressDialog(this);

        authentication = FirebaseAuth.getInstance();

        if(authentication.getCurrentUser() != null) { //User is already logged in
            finish();
            LoginScreen.this.startActivity(new Intent(LoginScreen.this, MainMenu.class));
        }

        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                LoginScreen.this.startActivity(new Intent(LoginScreen.this, RegisterScreen.class));
            }
        });

        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    private void login() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) { //Empty Fields
            Toast.makeText(this, "Please enter all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        progress.setMessage("Logging in. Please wait.");
        progress.show();

        authentication.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progress.dismiss();
                if(task.isSuccessful()) {
                    //open the app
                    finish();
                    LoginScreen.this.startActivity(new Intent(LoginScreen.this, MainMenu.class));
                }
                else {
                    Toast.makeText(LoginScreen.this, "Email or password was incorrect", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
