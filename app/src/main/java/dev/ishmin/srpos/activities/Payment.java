package dev.ishmin.srpos.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
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
    EditText cname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        Bundle bundle = getIntent().getExtras();

        getSupportActionBar().setTitle("Payment");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        cno = findViewById(R.id.cno);
        discount = findViewById(R.id.discout);
        done = findViewById(R.id.Done);
        radio = (RadioGroup) findViewById(R.id.radiostaus);
        textView = findViewById(R.id.total); //set total amount from billing frag
        textView.setText(Float.toString(BillingFragment.total));
        cname=findViewById(R.id.cname);

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int idselected = radio.getCheckedRadioButtonId();
                    radioButton = (RadioButton) findViewById(idselected);
                    String status = (String) radioButton.getText();
                    String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                    if (  cno.getText().toString().length()>0&&cno.getText().toString().length() == 10 && discount.getText().toString().length() > 0 && Integer.parseInt(discount.getText().toString()) <= BillingFragment.total) {
                        try {
                            MainActivity x = new MainActivity();
                            MainActivity.SRPOS.execSQL("INSERT INTO Salesnew1(cutomername,customerno,date,billamount,discount,status,adminno)VALUES('"+cname.getText().toString()+"'," + Long.parseLong(cno.getText().toString()) + ",'" + date + "'," + BillingFragment.total + "," + Float.parseFloat(discount.getText().toString()) + ",'" + status + "'," + Long.parseLong(MainActivity.sharedPreferences.getString("usernumber", "")) + ")");

                            for (int i = 0; i < BillingFragment.productlist.size(); i++)
                            {

                                MainActivity.SRPOS.execSQL("INSERT INTO Solditemsnew1(name,mrp,quantity,date,adminno,sku) VALUES('" + BillingFragment.productname.get(i) + "'," + Float.parseFloat(BillingFragment.productmrp.get(i)) + "," + Integer.parseInt(BillingFragment.productquantity.get(i)) + ",'" + date + "'," + Long.parseLong(MainActivity.sharedPreferences.getString("usernumber", "")) + ","+Long.parseLong(BillingFragment.productsku.get(i))+")");
                                Log.i("name", BillingFragment.productname.get(i));
                                Log.i("mrp", (BillingFragment.productmrp.get(i)));
                                Log.i("quantity", BillingFragment.productquantity.get(i));
                                // Log.i("unit", productunit.get(i));

                                Cursor c = MainActivity.SRPOS.rawQuery("SELECT stock FROM Productsnew WHERE sku=" + Long.parseLong(BillingFragment.productsku.get(i)) + " AND adminno=" + Long.parseLong(MainActivity.sharedPreferences.getString("usernumber", "")), null);
                                c.moveToFirst();

                                int quantity2 = c.getColumnIndex("stock");
                                int stock = c.getInt(quantity2);
                                int newstock = stock - Integer.parseInt(BillingFragment.productquantity.get(i));
                                Log.i("newstock", Integer.toString(newstock));
                                Log.i("sku", BillingFragment.productsku.get(i));

                                MainActivity.SRPOS.execSQL("UPDATE Productsnew SET stock= " + newstock + " WHERE sku=" + Long.parseLong(BillingFragment.productsku.get(i)) + " AND adminno=" + Long.parseLong(MainActivity.sharedPreferences.getString("usernumber", "")));
                            }

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
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

