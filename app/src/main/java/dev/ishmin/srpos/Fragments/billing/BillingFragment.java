package dev.ishmin.srpos.Fragments.billing;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.muddzdev.styleabletoastlibrary.StyleableToast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import dev.ishmin.srpos.MainActivity;
import dev.ishmin.srpos.Payment;
import dev.ishmin.srpos.R;
import dev.ishmin.srpos.ScannerActivity;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class BillingFragment extends Fragment implements EasyPermissions.PermissionCallbacks {

    static String res = null;
    static String name;
    static String mrp;
    static List<String> productlist;
    public static ListView billing;
    static ArrayAdapter<String> arrayAdapter;
    public static String sku;
    public static int flag1;
    int change;

    int reqCode = 123;

    static int index;
    static List<String> productname;
    static List<String> productcategory;
    static List<String> productsubcategory;
    static List<String> productbrand;
    static List<String> productbuyrate;
    static List<String> productmrp;
    static List<String> productsku;
    static List<String> productquantity;
    static List<String> productsupplier;
    static List<String> productunit;
    static CustomAdapter myCustomAdapter;

    public static float total;
    ImageButton totalbutton;
    public static TextView totalview;
    TextView textView;
    TextView textView2;
    ImageButton qscanner;
    ImageButton refreshBtn;

    //Create new bill method
    public static void refresh()
    {
        productlist.clear();
        productcategory.clear();
        productsubcategory.clear();
        productbrand.clear();
        productbuyrate.clear();
        productmrp.clear();
        productsku.clear();
        productquantity.clear();
        productsupplier.clear();
//        productunit.clear();
        total = 0;
        totalview.setText("");
        myCustomAdapter.notifyDataSetChanged();
    }

    public static void entry() {

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
            //arrayAdapter.notifyDataSetChanged();
            myCustomAdapter.notifyDataSetChanged();
        }
        else {
                       /* String myUrl = "http://smartretailpos.pe.hu/api/products.php?sku=" + sku;
                        String returned;
                        Connection connection = new Connection();
                        returned = connection.execute(myUrl).get();*/
            try {

                Cursor c = MainActivity.SRPOS.rawQuery("SELECT * FROM Productsnew WHERE sku=" + Long.parseLong(sku) + " AND adminno= " + Long.parseLong(MainActivity.sharedPreferences.getString("usernumber", "")), null);
                int name = c.getColumnIndex("name");
                int category = c.getColumnIndex("category");
                int subcategory = c.getColumnIndex("subcategory");
                int brand = c.getColumnIndex("brand");
                int mrp = c.getColumnIndex("mrp");
                int sku = c.getColumnIndex("sku");
                int quantity = c.getColumnIndex("quantity");
                int supplier = c.getColumnIndex("supplier");
              //  int unit = c.getColumnIndex("unit");
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
                 //   productunit.add(c.getString(unit));
                    productbuyrate.add(Float.toString(c.getFloat(buyrate)));

                    productquantity.add("1");
                    total += c.getFloat(mrp);
                    String newitem = c.getString(name) + "  1   " + Float.toString(c.getFloat(mrp));
                    productlist.add(newitem);
                    myCustomAdapter.notifyDataSetChanged();
                    c.moveToNext();
                }

            } catch (Exception e) {
                Log.i("Exception", e.getMessage());
            }
        }

    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_billing, container, false);

        final String sku = "";
        flag1 = 1;
        total = 0;
        change = 0;

        productlist = new ArrayList<>();
        productname = new ArrayList<>();
        productcategory = new ArrayList<>();
        productsubcategory = new ArrayList<>();
        productbrand = new ArrayList<>();
        productbuyrate = new ArrayList<>();
        productmrp = new ArrayList<>();
        productsku = new ArrayList<>();
        productquantity = new ArrayList<>();
        productsupplier = new ArrayList<>();
       // productunit = new ArrayList<>();

        billing = v.findViewById(R.id.billinglist);
        myCustomAdapter = new CustomAdapter(getContext(), productlist);
        billing.setAdapter(myCustomAdapter);

        totalbutton = v.findViewById(R.id.totalbutton);
        textView = v.findViewById(R.id.txtView);
        textView2 = v.findViewById(R.id.txtView2);
        totalview = v.findViewById(R.id.totaldisplay);
        qscanner = v.findViewById(R.id.scanner);
        final Button payment = v.findViewById(R.id.payment);
        refreshBtn = v.findViewById(R.id.refresh);
        //  arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, productlist);
        //billing.setAdapter(arrayAdapter);
        // arrayAdapter= new CustomAdapter(getContext(),productlist);
        // billing.setAdapter(new CustomAdapter(getActivity(),productlist));


        //Creates new bill
        refreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (productlist.isEmpty()) {
                    StyleableToast.makeText(getActivity(), "Billing list is already empty!", R.style.toastDesign).show();
                } else {
                    refresh();
                    textView.setVisibility(View.GONE);
                    totalview.setVisibility(View.GONE);
                    textView2.setVisibility(View.VISIBLE);
                    payment.setVisibility(View.GONE);
                }
            }
        });

        //Open Payment activity
        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!productlist.isEmpty()) {
                    try {
                        MainActivity.SRPOS.execSQL("CREATE TABLE IF NOT EXISTS Salesnew(billid INTEGER PRIMARY KEY, customerno LONG ,date DATE,billamount FLOAT,discount FLOAT, status VARCHAR,adminno LONG)");
                        MainActivity.SRPOS.execSQL("CREATE TABLE IF NOT EXISTS Solditemsnew(id INTEGER PRIMARY KEY, name VARCHAR ,mrp FLOAT,  quantity INTEGER,unit VARCHAR,date DATE,adminno LONG)");
                        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

                        for (int i = 0; i < productlist.size(); i++)
                        {
                            MainActivity x = new MainActivity();
                            MainActivity.SRPOS.execSQL("INSERT INTO Solditemsnew(name,mrp,quantity,date,adminno) VALUES('" + productname.get(i) + "'," + Float.parseFloat(productmrp.get(i)) + "," + Integer.parseInt(productquantity.get(i)) + ",'" + date + "'," + Long.parseLong(MainActivity.sharedPreferences.getString("usernumber", "")) + ")");
                            Log.i("name", productname.get(i));
                            Log.i("mrp", (productmrp.get(i)));
                            Log.i("quantity", productquantity.get(i));
                           // Log.i("unit", productunit.get(i));

                            Cursor c = MainActivity.SRPOS.rawQuery("SELECT stock FROM Productsnew WHERE sku=" + Long.parseLong(productsku.get(i)) + " AND adminno=" + Long.parseLong(MainActivity.sharedPreferences.getString("usernumber", "")), null);
                            c.moveToFirst();

                            int quantity2 = c.getColumnIndex("stock");
                            int stock = c.getInt(quantity2);
                            int newstock = stock - Integer.parseInt(productquantity.get(i));
                            Log.i("newstock", Integer.toString(newstock));
                            Log.i("sku", productsku.get(i));

                            MainActivity.SRPOS.execSQL("UPDATE Productsnew SET stock= " + newstock + " WHERE sku=" + Long.parseLong(productsku.get(i)) + " AND adminno=" + Long.parseLong(MainActivity.sharedPreferences.getString("usernumber", "")));
                        }

                        Intent i = new Intent(getActivity(), Payment.class);
                        startActivity(i);
                    } catch (Exception e) {
                        e.printStackTrace();

                    }
                }
            }
        });

        //Get total amount
        totalbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (productlist.isEmpty()) {
                    StyleableToast.makeText(getActivity(), "Please scan", R.style.toastDesign).show();
                } else if (!productlist.isEmpty()) {
                    textView2.setVisibility(View.GONE);
                    textView.setVisibility(View.VISIBLE);
                    totalview.setVisibility(View.VISIBLE);
                    totalview.setText(Float.toString(total));
                    payment.setVisibility(View.VISIBLE);
                }
            }
        });

        //Open Scanner
        qscanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.i("usernumber",(MainActivity.sharedPreferences.getString("usernumber", "")));
                openScanner();
            }
        });
        return v;
    }

    //Request CAMERA Permissions
    @AfterPermissionGranted(123)
    private void openScanner() {
        String[] perms = {Manifest.permission.CAMERA};
        if (EasyPermissions.hasPermissions(getActivity(), perms))
        {
            Intent i = new Intent(getActivity(), ScannerActivity.class); //open scanner
            startActivity(i);
        }
        else
            {
            EasyPermissions.requestPermissions(this, "Camera permissions are required to use QR/BarCode scanner", 123, perms);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        openScanner();
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            openScanner();
        }
    }
}