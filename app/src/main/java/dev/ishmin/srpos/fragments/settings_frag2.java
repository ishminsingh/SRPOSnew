package dev.ishmin.srpos.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.w3c.dom.Text;

import dev.ishmin.srpos.R;

import static dev.ishmin.srpos.activities.SettingsActivity.MainLayout;
import static dev.ishmin.srpos.activities.SettingsActivity.frameLayout;
import static dev.ishmin.srpos.activities.SettingsActivity.layoutMid;

public class settings_frag2 extends Fragment {

    EditText editTextName;
    EditText editTextPhone;
    EditText editTextEmail;
    TextView textName;
    TextView textPhone;
    TextView textEmail;
    Button saveBtn;
    String getString;
    public static  String SHARED_PREF = "Shared";
    public static  String TEXT = "text";
    public static SharedPreferences preferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.settings_frag2, container, false);

        editTextName = v.findViewById(R.id.ownerName);
        editTextPhone = v.findViewById(R.id.ownerPhone);
        editTextEmail = v.findViewById(R.id.ownerEmail);
        saveBtn = v.findViewById(R.id.saveDetailsBtn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frameLayout.setVisibility(View.GONE);
                //MainLayout.setPadding(15,15,15,15);
                layoutMid.setVisibility(View.VISIBLE);
            }
        });
        preferences = this.getActivity().getSharedPreferences(SHARED_PREF,Context.MODE_PRIVATE);
        preferences.edit().putString(TEXT, editTextName.getText().toString()).apply();
        


        return v;
    }
}
