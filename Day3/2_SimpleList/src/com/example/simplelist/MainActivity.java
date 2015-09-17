package com.example.simplelist;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

    String[] name = { "Иван", "Марья", "Петр", "Антон", "Даша", "Борис","Костя", "Игорь" };

    String[] position = { "Программер", "Бухгалтер", "Программер", "Программер", "Бухгалтер", "Директор", "Программер", "Охранник" };

    int salary[] = { 13000, 10000, 13000, 13000, 10000, 15000, 13000, 8000 };

    int[] colors = new int[2];

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        colors[0] = Color.parseColor("#559966CC");
        colors[1] = Color.parseColor("#55336699");

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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
