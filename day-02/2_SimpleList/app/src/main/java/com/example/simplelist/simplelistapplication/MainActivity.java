package com.example.simplelist.simplelistapplication;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

    String[] name = { "Иван", "Марья", "Петр", "Антон", "Даша", "Борис","Костя", "Игорь" };
    String[] position = { "Программер", "Бухгалтер", "Программер", "Программер", "Бухгалтер", "Директор", "Программер", "Охранник" };
    int salary[] = { 13000, 10000, 13000, 13000, 10000, 15000, 13000, 8000 };

    int[] colors = {Color.parseColor("#559966CC"), Color.parseColor("#55336699") };

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        LinearLayout linLayout = (LinearLayout) findViewById(R.id.linLayout);
        LayoutInflater ltInflater = getLayoutInflater();

        for (int i = 0; i < name.length; i++) {
            Log.e("myLogs", "i = " + i);
            View newItem = ltInflater.inflate(R.layout.item, linLayout, false);

            TextView tvName = (TextView) newItem.findViewById(R.id.tvName);
            tvName.setText(name[i]);

            TextView tvPosition = (TextView) newItem.findViewById(R.id.tvPosition);
            tvPosition.setText("Должность: " + position[i]);

            TextView tvSalary = (TextView) newItem.findViewById(R.id.tvSalary);
            tvSalary.setText("Оклад: " + salary[i]);

            newItem.getLayoutParams().width = LayoutParams.MATCH_PARENT;
            newItem.setBackgroundColor(colors[i % 2]);

            linLayout.addView(newItem);
        }
    }

}
