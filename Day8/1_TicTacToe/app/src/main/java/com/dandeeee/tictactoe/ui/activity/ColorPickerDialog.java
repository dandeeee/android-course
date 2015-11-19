package com.dandeeee.tictactoe.ui.activity;

import com.dandeeee.tictactoe.ui.component.ColorPickerView;
import com.dandeeee.tictactoe.ui.component.ColorPickerView.OnColorChangedListener;

import android.os.Bundle;
import android.app.Dialog;
import android.content.Context;

public class ColorPickerDialog extends Dialog {

    private OnColorChangedListener mListener;
    private int mInitialColor;

    public ColorPickerDialog(Context context, OnColorChangedListener listener, int initialColor) {
        super(context);

        mListener = listener;
        mInitialColor = initialColor;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OnColorChangedListener l = new OnColorChangedListener() {
            public void colorChanged(int color) {
                mListener.colorChanged(color);
                dismiss();
            }
        };

        setContentView(new ColorPickerView(getContext(), l, mInitialColor));
        setTitle("Pick a Color");
    }
}