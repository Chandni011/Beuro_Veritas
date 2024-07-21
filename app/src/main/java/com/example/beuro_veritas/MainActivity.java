package com.example.beuro_veritas;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.beuro_veritas.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        mAuth = FirebaseAuth.getInstance();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        binding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.loginemail.getText().toString().trim();
                String password = binding.loginPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    binding.loginemail.setError("Email is required.");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    binding.loginPassword.setError("Password is required.");
                    return;
                }

                if (password.length() < 6) {
                    binding.loginPassword.setError("Password must be >= 6 characters");
                    return;
                }

                binding.progressbar.setVisibility(View.VISIBLE);

                // Register user
                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "Account created", Toast.LENGTH_SHORT).show();
                            binding.progressbar.setVisibility(View.GONE);
                            Intent i = new Intent(MainActivity.this, home_page.class);
                            startActivity(i);
                            finish();

                        } else {
                            Toast.makeText(MainActivity.this, "Mismatch!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            binding.progressbar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });

        binding.loginForgetpwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent j = new Intent(MainActivity.this, forget_password.class);
                startActivity(j);
                finish();
            }
        });
        binding.signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent k = new Intent(MainActivity.this, Sign_up_page.class);
                startActivity(k);
                finish();
            }
        });
    }
}