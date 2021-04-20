package com.programabit.mediguard.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.programabit.mediguard.R;

public class ContactActivity extends BaseActivity {
    ImageButton imgMedicalEntityPhone;
    ImageButton imgChimeraDevsPhone;
    ImageButton imgMedicalEntityMail;
    ImageButton imgChimeraDevsMail;
    TextView tvMedicalEntityPhone;
    TextView tvChimeraDevsPhone;
    TextView tvMedicalEntityMail;
    TextView tvChimeraDevsMail;
    String entityPhone;
    String entityMail;
    String chimeraPhone;
    String chimeraMail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        entityPhone = "2486-5008"; //ASSE
        entityMail = String.valueOf(R.string.medical_entity_email);
        chimeraPhone = "+598-99-000-000";
        chimeraMail = String.valueOf(R.string.tech_support_email);
    }

    public void entityCall(View view) {
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", entityPhone, null)));
    }


    public void entityMail(View view) {

        Intent email = new Intent(Intent.ACTION_SEND);
        email.putExtra(Intent.EXTRA_EMAIL, new String[]{"youremail@yahoo.com"});
        email.putExtra(Intent.EXTRA_SUBJECT, "subject");
        email.putExtra(Intent.EXTRA_TEXT, "message");
        email.setType("message/rfc822");
        startActivity(Intent.createChooser(email, "Choose an Email client :"));

        //Intent intentEntity = new Intent(Intent.ACTION_SEND);
        //intentEntity.setType("text/plain");
        //intentEntity.putExtra(Intent.EXTRA_EMAIL, entityMail);
        //intentEntity.putExtra(Intent.EXTRA_SUBJECT, "Subject");
        //intentEntity.putExtra(Intent.EXTRA_TEXT, "I'm email body.");

        //startActivity(Intent.createChooser(intent, "Send Email"));
    }

    public void chimeraCall(View view) {
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", chimeraPhone, null)));
    }

    public void chimeraMail(View view) {
        Intent intentChimera = new Intent(Intent.ACTION_SEND);
        intentChimera.setType("text/plain");
        intentChimera.putExtra(Intent.EXTRA_EMAIL, chimeraMail);
        intentChimera.putExtra(Intent.EXTRA_SUBJECT, "Subject");
        intentChimera.putExtra(Intent.EXTRA_TEXT, "I'm email body.");

        startActivity(Intent.createChooser(intentChimera, "Send Email"));
    }
}