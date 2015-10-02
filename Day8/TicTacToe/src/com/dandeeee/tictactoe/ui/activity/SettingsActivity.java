package com.dandeeee.tictactoe.ui.activity;

import com.dandeeee.tictactoe.Constants;
import com.dandeeee.tictactoe.R;
import com.dandeeee.tictactoe.ui.component.ColorPickerDialog;
import com.dandeeee.tictactoe.ui.component.ColorPickerView;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.Preference.OnPreferenceClickListener;

public class SettingsActivity extends PreferenceActivity implements
        ColorPickerView.OnColorChangedListener {

    @SuppressWarnings("deprecation")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        final ListPreference goesFirstPref = (ListPreference) findPreference(Constants.GOES_FIRST_PREFERENCE_KEY);
        String goesFirst = prefs.getString(Constants.GOES_FIRST_PREFERENCE_KEY, "Alternate");
        goesFirstPref.setSummary((CharSequence) goesFirst);
        goesFirstPref.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                goesFirstPref.setSummary((CharSequence) newValue);

                // Since we are handling the pref, we must save it
                SharedPreferences.Editor ed = prefs.edit();
                ed.putString(Constants.GOES_FIRST_PREFERENCE_KEY, newValue.toString());
                ed.commit();

                return true;
            }
        });

        final ListPreference difficultyLevelPref = (ListPreference)
                findPreference(Constants.DIFFICULTY_PREFERENCE_KEY);
        String difficulty = prefs.getString(Constants.DIFFICULTY_PREFERENCE_KEY,
                getResources().getString(R.string.difficulty_expert));
        difficultyLevelPref.setSummary((CharSequence) difficulty);
        difficultyLevelPref.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                difficultyLevelPref.setSummary((CharSequence) newValue);

                // Since we are handling the pref, we must save it
                SharedPreferences.Editor ed = prefs.edit();
                ed.putString(Constants.DIFFICULTY_PREFERENCE_KEY, newValue.toString());
                ed.commit();

                return true;
            }
        });

        final EditTextPreference victoryMessagePref = (EditTextPreference)
                findPreference(Constants.VICTORY_MESSAGE_PREFERENCE_KEY);
        String victoryMessage = prefs.getString(Constants.VICTORY_MESSAGE_PREFERENCE_KEY, getResources().getString(R.string.result_human_wins));
        victoryMessagePref.setSummary("\"" + victoryMessage + "\"");
        victoryMessagePref.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                victoryMessagePref.setSummary((CharSequence) newValue);

                SharedPreferences.Editor ed = prefs.edit();
                ed.putString(Constants.VICTORY_MESSAGE_PREFERENCE_KEY, newValue.toString());
                ed.commit();

                return true;
            }
        });


		final Preference boardColorPref = (Preference) findPreference(Constants.BOARD_COLOR_PREFERENCE_KEY);
        boardColorPref.setOnPreferenceClickListener(new OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                int color = prefs.getInt(Constants.BOARD_COLOR_PREFERENCE_KEY, Color.GRAY);
                new ColorPickerDialog(SettingsActivity.this, SettingsActivity.this,
                        color).show();
                return true;
            }
        });

    }

    @Override
    protected Dialog onCreateDialog(int id) {
        Dialog dialog = null; // = new ColorPickerDialog();
        return dialog;
    }

    @Override
    public void colorChanged(int color) {
        PreferenceManager.getDefaultSharedPreferences(this).edit().putInt(
        		Constants.BOARD_COLOR_PREFERENCE_KEY, color).commit();
    }
}
