package dev.ishmin.srpos.Fragments.billing;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import dev.ishmin.srpos.MainActivity;
import dev.ishmin.srpos.Payment;
import dev.ishmin.srpos.R;
import dev.ishmin.srpos.ScannerActivity;

public class BillingFragment extends Fragment {
    public static TextView textView;
    static String res = null;
    static String name;
    static String mrp;
    static List<String> productlist;
    public static ListView billing;
    static ArrayAdapter<String> arrayAdapter;
    public static String sku;
    public static int flag1;
    int change=0;

    static int index;
    static List<String> productname ;
    static List<String> productcategory;
    static List<String> productsubcategory ;
    static List<String> productbrand ;
    static List<String> productbuyrate ;
    static List<String> productmrp ;
    static List<String> productsku;
    static List<String> productquantity;
    static List<String> productsupplier ;
    static List<String> productunit;

    public static float total;
    Button totalbutton;
    TextView totalview;
    ImageButton qscanner;

    public static void entry()
    {

        if (productsku.contains(sku))
        {
            index = productsku.indexOf(sku);

            String tempmrp = productmrp.get(index);
            float tempMRP = Float.parseFloat(tempmrp.trim());
            total += tempMRP;
            String tempname = productname.get(index);

            String tempquantity = productquantity.get(index);
            int quantity = Integer.parseInt(tempquantity.trim());
            quantity++;
            productquantity.set(index, Integer.toString(quantity));

            tempMRP *= quantity;

            String update = tempname + "  " + quantity + "  " + Float.toString(tempMRP);

                   /* String tempproduct=productlist.get(index);
                    String[] split= tempproduct.split("\\s");
                    Log.i("splitting",split[1]);
                    tempMRP+=Integer.parseInt(split[1]);
                    String update=split[0]+(Float.toString(tempMRP));*/

            productlist.set(index, update);
            arrayAdapter.notifyDataSetChanged();
        }
        else
            {
                       /* String myUrl = "http://smartretailpos.pe.hu/api/products.php?sku=" + sku;
                        String returned;
                        Connection connection = new Connection();
                        returned = connection.execute(myUrl).get();*/
            try {
                Cursor c = MainActivity.SRPOS.rawQuery("SELECT * FROM Products1 WHERE sku=" + Long.parseLong(sku), null);
                int name = c.getColumnIndex("name");
                int category = c.getColumnIndex("category");
                int subcategory = c.getColumnIndex("subcategory");
                int brand = c.getColumnIndex("brand");
                int mrp = c.getColumnIndex("mrp");
                int sku = c.getColumnIndex("sku");
                int quantity = c.getColumnIndex("quantity");
                int supplier = c.getColumnIndex("supplier");
                int unit = c.getColumnIndex("unit");
                int buyrate = c.getColumnIndex("buyrate");

                c.moveToFirst();

                while (!c.isAfterLast()) {
                    //Log.i("name", c.getString(name));
                    //Log.i("sku", c.getString(sku));
                    productname.add(c.getString(name));
                    productcategory.add(c.getString(category));
                    productsubcategory.add(c.getString(subcategory));
                    productbrand.add(c.getString(brand));
                    productmrp.add(Float.toString(c.getFloat(mrp)));
                    productsku.add(Long.toString(c.getLong(sku)));
                    productsupplier.add(c.getString(supplier));
                    productunit.add(c.getString(unit));
                    productbuyrate.add(Float.toString(c.getFloat(buyrate)));

                    productquantity.add("1");
                    total += c.getFloat(mrp);
                    String newitem = c.getString(name) + "  1   " + Float.toString(c.getFloat(mrp));
                    productlist.add(newitem);
                    arrayAdapter.notifyDataSetChanged();
                    c.moveToNext();
                }

            } catch (Exception e) {
                //Toast.makeText(,"not scanned",Toast.LENGTH_SHORT).show();
                Log.i("Exception", e.getMessage());
            }
        }

    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_billing, container, false);

        final String sku = "";
        flag1 = 1;
        total=0;
        //textView = v.findViewById(R.id.txtView);

        productlist = new ArrayList<String>();
         productname = new ArrayList<String>();
         productcategory = new ArrayList<String>();
         productsubcategory = new ArrayList<String>();
        productbrand = new ArrayList<String>();
       productbuyrate = new ArrayList<String>();
         productmrp = new ArrayList<String>();
         productsku = new ArrayList<String>();
         productquantity = new ArrayList<String>();
       productsupplier = new ArrayList<String>();
         productunit = new ArrayList<String>();
        billing = v.findViewById(R.id.billinglist);
        arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, productlist);
        billing.setAdapter(arrayAdapter);
        totalbutton = v.findViewById(R.id.totalbutton);
        totalview = v.findViewById(R.id.totaldisplay);
        qscanner = v.findViewById(R.id.scanner);

        final Button payment = v.findViewById(R.id.payment);

      //add nutton to choose remove or add
        billing.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (change==0)
                {
                    index =position;

                    String tempmrp = productmrp.get(index);
                    float tempMRP = Float.parseFloat(tempmrp.trim());
                    total += tempMRP;
                    String tempname = productname.get(index);

                    String tempquantity = productquantity.get(index);
                    int quantity = Integer.parseInt(tempquantity.trim());
                    quantity++;
                    productquantity.set(index, Integer.toString(quantity));

                    tempMRP *= quantity;

                    String update = tempname + "  " + quantity + "  " + Float.toString(tempMRP);

                   /* String tempproduct=productlist.get(index);
                    String[] split= tempproduct.split("\\s");
                    Log.i("splitting",split[1]);
                    tempMRP+=Integer.parseInt(split[1]);
                    String update=split[0]+(Float.toString(tempMRP));*/

                    productlist.set(index, update);
                    arrayAdapter.notifyDataSetChanged();

                }
                else
                {
                    index =position;

                    String tempmrp = productmrp.get(index);
                    float tempMRP = Float.parseFloat(tempmrp.trim());
                    total -= tempMRP;


                    String tempquantity = productquantity.get(index);
                    int quantity = Integer.parseInt(tempquantity.trim());
                    quantity--;

                    if(quantity!=0)
                    { String tempname = productname.get(index);
                        productquantity.set(index, Integer.toString(quantity));

                    tempMRP *= quantity;

                    String update = tempname + "  " + quantity + "  " + Float.toString(tempMRP);

                   /* String tempproduct=productlist.get(index);
                    String[] split= tempproduct.split("\\s");
                    Log.i("splitting",split[1]);
                    tempMRP+=Integer.parseInt(split[1]);
                    String update=split[0]+(Float.toString(tempMRP));*/

                    productlist.set(index, update);
                    arrayAdapter.notifyDataSetChanged(); }
                    else
                    {
                        //remove from all lists and listview;
                    }

                }
            }
        });

        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //payment activity.
               if(!productlist.isEmpty())
               {
                   try {
                       MainActivity.SRPOS.execSQL("CREATE TABLE IF NOT EXISTS Sales(billid INTEGER PRIMARY KEY, customerno LONG ,date DATE,billamount FLOAT,discount FLOAT, status VARCHAR)");
                       MainActivity.SRPOS.execSQL("CREATE TABLE IF NOT EXISTS Solditems(id INTEGER PRIMARY KEY, name VARCHAR ,mrp FLOAT,  quantity INTEGER,unit VARCHAR,date DATE)");
                       String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

                       for (int i = 0; i < productlist.size(); i++)
                       {

                           MainActivity.SRPOS.execSQL("INSERT INTO Solditems(name,mrp,quantity,unit,date) VALUES('" + productname.get(i) + "'," + Float.parseFloat(productmrp.get(i)) + "," + Integer.parseInt(productquantity.get(i)) + ",'" + productunit.get(i) + "','" + date + "')");
                           Log.i("name", productname.get(i));
                           Log.i("mrp", (productmrp.get(i)));
                           Log.i("quantity", productquantity.get(i));
                           Log.i("unit", productunit.get(i));

                           Cursor c = MainActivity.SRPOS.rawQuery("SELECT stock FROM Products1 WHERE sku=" + Long.parseLong(productsku.get(i)), null);
                           c.moveToFirst();

                           int quantity2 = c.getColumnIndex("stock");
                           int stock = c.getInt(quantity2);
                           int newstock = stock - Integer.parseInt(productquantity.get(i));
                           Log.i("newstock", Integer.toString(newstock));
                           Log.i("sku", productsku.get(i));

                           MainActivity.SRPOS.execSQL("UPDATE Products1 SET stock= " + newstock + " WHERE sku=" + Long.parseLong(productsku.get(i)));



                       }
                       Intent i = new Intent(getActivity(), Payment.class); //open scanner
                       startActivity(i);
                   }
                   catch (Exception e)
                   {
                       e.printStackTrace();

                   }
               }
               else
                   {
                       Toast.makeText(getActivity(), "List Empty", Toast.LENGTH_SHORT).show();
                   }
            }
        });

        totalbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (productlist.isEmpty()) {
                    Toast.makeText(getActivity(), "Please scan", Toast.LENGTH_SHORT).show();
                } else
                    totalview.setText(Float.toString(total));
            }
        });

// put qr action listener here ..and return the code scanned in String sku.

        qscanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ScannerActivity.class); //open scanner
                startActivity(i);
            }
        });

        return v;
    }
}
