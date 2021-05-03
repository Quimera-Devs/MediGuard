package com.programabit.mediguard.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.programabit.mediguard.R;
import com.programabit.mediguard.data.MedicRestRepositoryAsync;
import com.programabit.mediguard.data.preferences.TokenPreference;
import com.programabit.mediguard.domain.MedicDto;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static com.programabit.mediguard.ui.DashboardActivity.myself;

public class UserUpdateActivity extends BaseActivity {
    private MedicRestRepositoryAsync medicRepo;
    private String myToken;
    protected EditText phone;
    protected EditText dir;
    protected Spinner departmentSpinner;

    private final ArrayList<String> departments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_update);
        phone = findViewById(R.id.edPhone);
        dir = findViewById(R.id.edDir);
        TokenPreference preference = new TokenPreference(this);
        myToken = preference.getToken();

        setDepartmentList();
        departmentSpinner = findViewById(R.id.spinnerDepartment);
        String[] items = departments.toArray(new String[0]);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        departmentSpinner.setAdapter(adapter);

        if (!myToken.isEmpty()) {
            Log.i("Settings", "got token correctly");
            medicRepo = new MedicRestRepositoryAsync(this.getApplication(), myToken);
            medicRepo.execute(myToken);
            try {
                myself = medicRepo.get();
            } catch (ExecutionException e) {
                Log.i("excecute exception", e.getMessage());
                e.printStackTrace();
            } catch (InterruptedException e) {
                Log.i("interrup exception", e.getMessage());
                e.printStackTrace();
            }
            if (myself != null) {
                dir.setText(myself.getDireccion());
                phone.setText(String.valueOf(myself.getTelefono()));
                int userDepartmentIndex = departments.indexOf(myself.getDepartamento());
                departmentSpinner.setSelection(userDepartmentIndex);
                Log.i("Settings", "filled user data correctly");
            }
        }
    }

    private void setDepartmentList() {
        departments.add(getString(R.string.artigas));
        departments.add(getString(R.string.canelones));
        departments.add(getString(R.string.cerro_largo));
        departments.add(getString(R.string.colonia));
        departments.add(getString(R.string.durazno));
        departments.add(getString(R.string.flores));
        departments.add(getString(R.string.florida));
        departments.add(getString(R.string.lavalleja));
        departments.add(getString(R.string.maldonado));
        departments.add(getString(R.string.montevideo));
        departments.add(getString(R.string.paysandu));
        departments.add(getString(R.string.rio_megro));
        departments.add(getString(R.string.rivera));
        departments.add(getString(R.string.rocha));
        departments.add(getString(R.string.salto));
        departments.add(getString(R.string.san_jose));
        departments.add(getString(R.string.soriano));
        departments.add(getString(R.string.tacuarembo));
        departments.add(getString(R.string.trenta_y_tres));
    }


    public void configUserEdir(View view) {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(getString(R.string.update_confirmation_title))
                .setMessage(R.string.update_confirmation_msg)
                .setPositiveButton(R.string.delete_confirmation_yes, new DialogInterface.OnClickListener()
                {
                    int getPhone = Integer.parseInt(String.valueOf(phone.getText()));
                    String getDir = dir.getText().toString();
                    String getDepartment = departmentSpinner.getSelectedItem().toString();

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.i("User Input", getDir + ", " + phone.getText());
                        try {
                            myself.setTelefono(getPhone);
                            myself.setDireccion(getDir);
                            myself.setDepartamento(getDepartment);
                            //Snackbar.make(view, R.string.user_edit_success, Snackbar.LENGTH_LONG).show();
                            Toast msg = Toast.makeText(UserUpdateActivity.this, R.string.user_edit_success, Toast.LENGTH_LONG);
                            msg.show();
                            finish();
                            Log.i("Acepted User Edit",  phone.getText() + ", " + getDir + ", " + getDepartment);
                        } catch (Exception e) {
                            e.printStackTrace();
                            Snackbar.make(view, R.string.user_edit_error, Snackbar.LENGTH_LONG).show();
                        }
                    }

                })
                .setNegativeButton(R.string.delete_confirmation_no, null)
                .show();
        //
    }
}