package com.sunilpaulmathew.debloater.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/*
 * Created by sunilpaulmathew <sunil.kde@gmail.com> on December 28, 2020
 */

public class LicenceFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        WebView webView = new WebView(getActivity());
        webView.loadUrl("file:///android_asset/gpl.html");

        return webView;
    }

}