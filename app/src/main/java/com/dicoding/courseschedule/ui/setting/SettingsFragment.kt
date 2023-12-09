package com.dicoding.courseschedule.ui.setting

import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.notification.DailyReminder

class SettingsFragment : PreferenceFragmentCompat() {
    private  lateinit var  DARK_MODE: String
    private  lateinit var  NOTIFICATION: String

    private  lateinit var themePreference: ListPreference
    private  lateinit var  notificationPreference: SwitchPreference

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        //TODO 10 : Update theme based on value in ListPreference
        //TODO 11 : Schedule and cancel notification in DailyReminder based on SwitchPreference
        DARK_MODE = resources.getString(R.string.pref_key_dark)
        NOTIFICATION = resources.getString(R.string.pref_key_notify)

        themePreference = findPreference<ListPreference>(DARK_MODE) as ListPreference
        notificationPreference = findPreference<SwitchPreference>(NOTIFICATION) as SwitchPreference

        notificationPreference.setOnPreferenceChangeListener{_,new ->
            val reminder = DailyReminder()
            if (new == true){
                reminder.setDailyReminder(requireContext())
            }else if (new == false){
                reminder.cancelAlarm(requireContext())
            }
            true
        }
        themePreference.setOnPreferenceChangeListener{_, new ->
            when(new.toString()){
                "on" -> {
                    updateTheme(MODE_NIGHT_YES)
                }
                "off" -> {
                    updateTheme(MODE_NIGHT_NO)
                }
                "auto" -> {
                    updateTheme(MODE_NIGHT_FOLLOW_SYSTEM)
                }

            }
            true
        }
    }

    private fun updateTheme(nightMode: Int): Boolean {
        AppCompatDelegate.setDefaultNightMode(nightMode)
        requireActivity().recreate()
        return true
    }
}