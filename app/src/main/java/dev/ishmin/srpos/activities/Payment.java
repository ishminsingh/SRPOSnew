package dev.ishmin.srpos.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.muddzdev.styleabletoastlibrary.StyleableToast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import dev.ishmin.srpos.fragments.billing.BillingFragment;
import dev.ishmin.srpos.R;


public class Payment extends AppCompatActivity {

    Button done;
    EditText cno;
    EditText discount;
    RadioGroup radio;
    RadioButton radioButton;
    TextView textView;
   // EditText cname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        Bundle bundle = getIntent().getExtras();

        cno = findViewById(R.id.cno);
        discount = findViewById(R.id.discout);
        done = findViewById(R.id.Done);
        radio = (RadioGroup) findViewById(R.id.radiostaus);
        textView = findViewById(R.id.total); //set total amount from billing frag
        textView.setText(Float.toString(BillingFragment.total));

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int idselected = radio.getCheckedRadioButtonId();
                    radioButton = (RadioButton) findViewById(idselected);
                    String status = (String) radioButton.getText();
                    String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                    if ( cno.getText().toString().length() == 10 && discount.getText().toString().length() > 0 && Integer.parseInt(discount.getText().toString()) <= BillingFragment.total) {
                        try {
                            MainActivity x = new MainActivity();
                            MainActivity.SRPOS.execSQL("INSERT INTO Salesnew(customerno,date,billamount,discount,status,adminno)VALUES(" + Long.parseLong(cno.getText().toString()) + ",'" + date + "'," + BillingFragment.total + "," + Float.parseFloat(discount.getText().toString()) + ",'" + status + "'," + Long.parseLong(MainActivity.sharedPreferences.getString("usernumber", "")) + ")");
                            StyleableToast.makeText(Payment.this, "Payment Made", R.style.toastDesign).show();
                            onBackPressed();
                        } catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    } else {
                        StyleableToast.makeText(Payment.this, "Invalid entry", R.style.toastDesign).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

    }
}

