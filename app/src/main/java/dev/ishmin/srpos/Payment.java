package dev.ishmin.srpos;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.muddzdev.styleabletoastlibrary.StyleableToast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import dev.ishmin.srpos.Fragments.billing.BillingFragment;


public class Payment extends AppCompatActivity {

    Button done;
     EditText cno ;
     EditText discount;
     RadioGroup radio ;
     RadioButton radioButton;
     TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        Bundle bundle = getIntent().getExtras();

        cno =findViewById(R.id.cno);
        discount =findViewById(R.id.discout);
        done=findViewById(R.id.Done);
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
                if (cno.getText().toString().length() == 10 && discount.getText().toString().length() > 0)
                {
                    try
                    {
                        MainActivity.SRPOS.execSQL("INSERT INTO Sales(customerno,date,billamount,discount,status)VALUES(" + Long.parseLong(cno.getText().toString()) + ",'" + date + "'," + BillingFragment.total + "," + Float.parseFloat(discount.getText().toString()) + ",'" + status + "')");
                        //Toast.makeText(Payment.this, "Payment Made", Toast.LENGTH_SHORT).show();
                        StyleableToast.makeText(Payment.this,"Payment Made", R.style.toastDesign).show();
                        onBackPressed();
                        onBackPressed();
                    } catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
                else
                    {
                    //Toast.makeText(Payment.this, "invalid entry", Toast.LENGTH_SHORT).show();
                    StyleableToast.makeText(Payment.this,"Invalid entry", R.style.toastDesign).show();
                    }
            }
            catch(Exception e)
                {
                  e.printStackTrace();
                }

            }
        });

    }
}

