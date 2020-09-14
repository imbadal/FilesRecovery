package com.example.imagerecover.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.imagerecover.R;
import com.example.imagerecover.utils.NetworkUtil;

public class UserAgreementActivity extends AppCompatActivity {

    Context context;
    TextView privacyPolicyText;
    TextView disclosureText;
    TextView termsAndConditionText;
    TextView agreeText;

    CheckBox checkBox;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_agreement);

        context = this;
        privacyPolicyText = findViewById(R.id.privacy_policy_text);
        disclosureText = findViewById(R.id.disclosure_text);
        termsAndConditionText = findViewById(R.id.terms_and_conditions_text);
        agreeText = findViewById(R.id.agree_text);

        checkBox = findViewById(R.id.agreement_check);

        privacyPolicyText.setOnClickListener(v -> {

            if (!NetworkUtil.isInternetConnected(context)) {
                Toast.makeText(UserAgreementActivity.this, "Please Connect to internet ", Toast.LENGTH_SHORT).show();
            } else {
                intent = new Intent(getApplicationContext(), AgreementWebViewActivity.class);
                intent.putExtra("key", "Privacy Policy");
                startActivity(intent);
            }
        });

        disclosureText.setOnClickListener(v -> {
            if (!NetworkUtil.isInternetConnected(context)) {
                Toast.makeText(UserAgreementActivity.this, "Please Connect to internet ", Toast.LENGTH_SHORT).show();
            } else {
                intent = new Intent(getApplicationContext(), AgreementWebViewActivity.class);
                intent.putExtra("key", "Disclosure");
                startActivity(intent);
            }
        });

        termsAndConditionText.setOnClickListener(v -> {
            if (!NetworkUtil.isInternetConnected(context)) {
                Toast.makeText(UserAgreementActivity.this, "Please Connect to internet ", Toast.LENGTH_SHORT).show();
            } else {
                intent = new Intent(getApplicationContext(), AgreementWebViewActivity.class);
                intent.putExtra("key", "Terms And Conditions");
                startActivity(intent);
            }
        });

        agreeText.setOnClickListener(v -> {
            if (checkBox.isChecked()) {
                intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(UserAgreementActivity.this, "Please click the box to make sure that you have gone through the Privacy Policy and Teams & Conditions.", Toast.LENGTH_SHORT).show();
            }
        });


    }

}