package com.example.dandeeee.dynamicfragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class Fragment2 extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment2, null);
        v.setTag("FR2");

        Button button = (Button) v.findViewById(R.id.btnFrag2);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ((TextView) getActivity().findViewById(R.id.txtAct)).setText("Access from Fragment2");
            }
        });

        return v;
    }
}