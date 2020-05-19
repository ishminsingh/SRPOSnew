package dev.ishmin.srpos.fragments.sales;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import dev.ishmin.srpos.R;
import dev.ishmin.srpos.fragments.billing.BillingFragment;

public class CustomAdapterSales extends BaseAdapter
{
    private Context mContext;
    private List<String> mArrSchoolData= new ArrayList<String>();

    public CustomAdapterSales(Context context, List<String> arrSchoolData) {
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
        View v1 = inflater.inflate(R.layout.adapter_view_sales, parent, false);


        // get the reference of textViews
        TextView textCustName = v1.findViewById(R.id.customerNo);
        TextView textBill = v1.findViewById(R.id.bill);
        TextView textDiscount = v1.findViewById(R.id.discount);
        TextView textDate = v1.findViewById(R.id.date);
        TextView textStatus = v1.findViewById(R.id.status);

        String str1 = mArrSchoolData.get(position);

        String[] strings = str1.split(",");
        textCustName.setText(strings[0]);
        textBill.setText(strings[1]);
        textDiscount.setText(strings[2]);
        textDate.setText(strings[3]);
        textStatus.setText(strings[4]);


        return v1;
    }
}
