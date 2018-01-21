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
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterScreen extends AppCompatActivity {

    private EditText etEmail;
    private EditText etName;
    private EditText etPassword;
    private EditText etPassConfirm;
    private Button bRegister;
    private ProgressDialog progress;

    private FirebaseAuth authentication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_screen);

        etEmail = (EditText) findViewById(R.id.etEmail);
        etName = (EditText) findViewById(R.id.etName);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etPassConfirm = (EditText) findViewById(R.id.etPassConfirm);
        bRegister = (Button) findViewById(R.id.bRegister);
        progress = new ProgressDialog(this);

        authentication = FirebaseAuth.getInstance();

        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
    }

    private void register() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String passConfirm = etPassConfirm.getText().toString().trim();

        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(passConfirm)) { //Empty Fields
            Toast.makeText(this, "Please enter all fields", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!(password.equals(passConfirm))) { //Passwords don't match
            Toast.makeText(this, "Passwords don't match", Toast.LENGTH_SHORT).show();
            return;
        }

        progress.setMessage("Registering. Please wait.");
        progress.show();

        authentication.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progress.dismiss();
                if(task.isSuccessful()) {
                    Toast.makeText(RegisterScreen.this, "Registration successful", Toast.LENGTH_SHORT).show();
                    finish();
                    RegisterScreen.this.startActivity(new Intent(RegisterScreen.this, LoginScreen.class));
                }
                else {
                    Toast.makeText(RegisterScreen.this, "Registration failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
