package com.programabit.mediguard.ui;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.programabit.mediguard.R;
import com.programabit.mediguard.data.MedicRestRepositoryAsync;
import com.programabit.mediguard.data.preferences.TokenPreference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static com.programabit.mediguard.ui.DashboardActivity.myself;

public class UserUpdateActivity extends BaseActivity {
    private MedicRestRepositoryAsync medicRepo;
    private String myToken;
    private final ArrayList<String> departments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_update);
        EditText phone = findViewById(R.id.edPhone);
        EditText dir = findViewById(R.id.edDir);
        TokenPreference preference = new TokenPreference(this);
        myToken = preference.getToken();

        setDepartmentList();
        Spinner departmentSpinner = findViewById(R.id.spinnerDepartment);
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


}