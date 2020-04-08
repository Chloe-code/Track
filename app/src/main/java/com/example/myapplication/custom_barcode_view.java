package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class custom_barcode_view extends Fragment {
    private ZXingScannerView mScannerView;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        View view = inflater.inflate(R.layout.custom_barcode_view, container, false);
        return view;
    }

}
