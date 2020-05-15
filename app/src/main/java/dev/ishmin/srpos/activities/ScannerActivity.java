package dev.ishmin.srpos.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.zxing.Result;

import dev.ishmin.srpos.fragments.billing.BillingFragment;
import dev.ishmin.srpos.fragments.purchase.PurchaseFragment;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScannerActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    ZXingScannerView scannerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scannerView = new ZXingScannerView(this);
        setContentView(scannerView);
        Bundle bundle = getIntent().getExtras();
        getSupportActionBar().setTitle("POS Scanner");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void handleResult(Result rawResult) {
        try {
            if (BillingFragment.flag1 == 1)
            {
                BillingFragment.sku = rawResult.getText().toString();
                BillingFragment.entry();
                Log.i("result",rawResult.getText());
            } else
                {
                PurchaseFragment.scannerresult = rawResult.getText().toString();
                PurchaseFragment.scanner();
                    Log.i("result",rawResult.getText());
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
        scannerView.setResultHandler(this);
        scannerView.startCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
        scannerView.stopCamera();
    }
}
