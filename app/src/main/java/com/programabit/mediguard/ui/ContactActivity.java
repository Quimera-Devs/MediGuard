package com.programabit.mediguard.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;
import com.programabit.mediguard.R;

public class ContactActivity extends BaseActivity {
    String entityPhone;
    String entityMail;
    String chimeraPhone;
    String chimeraMail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        entityPhone = getString(R.string.medical_entity_phone);
        entityMail = getString(R.string.medical_entity_email);
        chimeraPhone = getString(R.string.tech_support_phone);
        chimeraMail = getString(R.string.tech_support_email);
    }

    public void entityCall(View view) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + Uri.encode(entityPhone)));
        try {
            startActivity(intent);
        } catch (Exception e) {
            Log.i("contact dial error", e.getMessage());
            e.printStackTrace();
            Snackbar.make(view, R.string.call_error, Snackbar.LENGTH_LONG).show();
        }
    }

    public void entityMail(View view) {
        Intent intentEntity = new Intent(Intent.ACTION_SEND);
        intentEntity.setType("message/rfc822");
        intentEntity.putExtra(Intent.EXTRA_EMAIL, new String[]{entityMail});
        intentEntity.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.email_subject_to_entity));
        try {
            startActivity(Intent.createChooser(intentEntity, getString(R.string.email_chooser_text)));
        } catch (Exception e) {
            Log.i("contact email error", e.getMessage());
            e.printStackTrace();
            Snackbar.make(view, R.string.email_error, Snackbar.LENGTH_LONG).show();
        }
    }

    public void chimeraWpp(View view) {


        Intent sendIntent = new Intent(Intent.ACTION_VIEW);
        String uri = "whatsapp://send?phone=" + chimeraPhone + "&text=" + getString(R.string.email_subject_to_support);
        sendIntent.setData(Uri.parse(uri));
        try {
            startActivity(Intent.createChooser(sendIntent, getString(R.string.wpp)));
        } catch (android.content.ActivityNotFoundException e) {
            Log.i("whatsapp not found", e.getMessage());
            e.printStackTrace();
            Snackbar.make(view, R.string.wpp_error_toast, Snackbar.LENGTH_LONG).show();
        }
    }

    public void chimeraMail(View view) {
        Intent intentChimera = new Intent(Intent.ACTION_SEND);
        intentChimera.setType("message/rfc822");
        intentChimera.putExtra(Intent.EXTRA_EMAIL, new String[]{chimeraMail});
        intentChimera.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.email_subject_to_support));
        try {
            startActivity(Intent.createChooser(intentChimera, getString(R.string.email_chooser_text)));
        } catch (Exception e) {
            Log.i("contact email error", e.getMessage());
            e.printStackTrace();
            Snackbar.make(view, R.string.email_error, Snackbar.LENGTH_LONG).show();
        }
    }
}