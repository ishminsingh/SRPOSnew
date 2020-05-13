package dev.ishmin.srpos.fragments.billing;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import dev.ishmin.srpos.R;

public class CustomAdapter extends BaseAdapter
{
    private Context mContext;
    private List<String> mArrSchoolData= new ArrayList<String>();

    public CustomAdapter(Context context, List<String> arrSchoolData) {
        super();
        mContext = context;
        mArrSchoolData = arrSchoolData;
    }
    @Override
    public int getCount() {
        return mArrSchoolData.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent)
    {
        // inflate the layout for each item of listView
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.listviewrow, parent, false);


        // get the reference of textView and button
        final TextView textView = view.findViewById(R.id.txtSchoolTitle);
        ImageButton plus = view.findViewById(R.id.btnplus);
        ImageButton minus = view.findViewById(R.id.btnminus);

        // Set the title and button name
       textView.setText(mArrSchoolData.get(position));
//        plus.setText("Action " + position);

        // Click listener of button
        plus.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                // Logic goes here
              int  index = position;

                String tempmrp = BillingFragment.productmrp.get(index);
                float tempMRP = Float.parseFloat(tempmrp.trim());
                BillingFragment.total += tempMRP;
                String tempname = BillingFragment.productname.get(index);

                String tempquantity = BillingFragment.productquantity.get(index);
                int quantity = Integer.parseInt(tempquantity.trim());
                quantity++;
                BillingFragment.productquantity.set(index, Integer.toString(quantity));

                tempMRP *= quantity;

                String update = tempname + "  " + quantity + "  " + Float.toString(tempMRP);

                   /* String tempproduct=productlist.get(index);
                    String[] split= tempproduct.split("\\s");
                    Log.i("splitting",split[1]);
                    tempMRP+=Integer.parseInt(split[1]);
                    String update=split[0]+(Float.toString(tempMRP));*/

                BillingFragment.productlist.set(index, update);
              //  arrayAdapter.notifyDataSetChanged();
                textView.setText(BillingFragment.productlist.get(index).toString());

            }
        });

     minus.setOnClickListener(new View.OnClickListener()
     {
    @Override
    public void onClick(View v) {
        int index =position;

        String tempmrp = BillingFragment.productmrp.get(index);
        float tempMRP = Float.parseFloat(tempmrp.trim());
        BillingFragment.total -= tempMRP;


        String tempquantity = BillingFragment.productquantity.get(index);
        int quantity = Integer.parseInt(tempquantity.trim());
        quantity--;

        if(quantity!=0)

        { String tempname = BillingFragment.productname.get(index);
            BillingFragment.productquantity.set(index, Integer.toString(quantity));

            tempMRP *= quantity;

            String update = tempname + "  " + quantity + "  " + Float.toString(tempMRP);

                   /* String tempproduct=productlist.get(index);
                    String[] split= tempproduct.split("\\s");
                    Log.i("splitting",split[1]);
                    tempMRP+=Integer.parseInt(split[1]);
                    String update=split[0]+(Float.toString(tempMRP));*/

            BillingFragment.productlist.set(index, update);
            textView.setText(BillingFragment.productlist.get(index).toString());
            //arrayAdapter.notifyDataSetChanged();
            }
        else
        {
            BillingFragment.productname.remove(index);
            BillingFragment.productcategory.remove(index);
            BillingFragment.productsubcategory.remove(index);
            BillingFragment.productbrand.remove(index);
            BillingFragment.productmrp.remove(index);
            BillingFragment.productsku.remove(index);
            BillingFragment.productsupplier.remove(index);
          //  BillingFragment.productunit.remove(index);
            BillingFragment.productbuyrate.remove(index);

            BillingFragment.productquantity.remove(index);


            BillingFragment.productlist.remove(index);
         //   textView.setText(BillingFragment.productlist.get(index).toString());
            BillingFragment.myCustomAdapter.notifyDataSetChanged();
            //.notifyDataSetChanged();
            //remove from all lists and listview;
        }


    }
});
        return view;
    }
}
