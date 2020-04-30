package dev.ishmin.srpos.Fragments.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import dev.ishmin.srpos.R;

public class DashboardFragment extends Fragment {

    private CardView cardAlert;
    private CardView cardSales;
    private CardView cardPayments;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_dashboard, container, false);

         cardAlert = v.findViewById(R.id.cardViewAlert);
         cardSales = v.findViewById(R.id.cardViewSales);
         cardPayments = v.findViewById(R.id.cardViewPayments);

        return v;
    }
}
