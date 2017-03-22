package com.example.dandeeee.dynamicfragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

public class MainActivity extends Activity {

    Fragment1 frag1;
    Fragment2 frag2;
    FragmentTransaction fTrans;
    CheckBox chbStack;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        frag1 = new Fragment1();
        frag2 = new Fragment2();

        chbStack = (CheckBox)findViewById(R.id.chbStack);
    }

    public void onClick(View v) {
        fTrans = getFragmentManager().beginTransaction();
        switch (v.getId()) {
            case R.id.btnAdd:
                fTrans.add(R.id.frgmCont, frag1);
                break;

            case R.id.btnRemove:
                fTrans.remove(frag1);
                break;

            case R.id.btnReplace:
                fTrans.replace(R.id.frgmCont, frag2);
                break;

            case R.id.btnUpd:
                try{
                    ((TextView) frag1.getView().findViewById(R.id.frag1Text)).setText("Access to Fragment 1 from Activity");

                    Fragment fr2 = getFragmentManager().findFragmentById(R.id.frgmCont); //FIXME: check how to conect to fragment
                    ((TextView) fr2.getView().findViewById(R.id.frag2Text)).setText("Access to Fragment 2 from Activity");
                } catch (Exception ex) {
                    // ok
                }
                break;

            default:
                break;
        }
        if(chbStack.isChecked())
            fTrans.addToBackStack(null);
        fTrans.commit();
    }
}