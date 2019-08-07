package cycu.nclab.moocs2018.pref;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.util.Log;

import cycu.nclab.moocs2018.R;

//偏好設定UI
public class PrefsActivity extends Activity {
	final String TAG = this.getClass().getSimpleName();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Display the fragment as the main content.
		getFragmentManager().beginTransaction()
				.replace(android.R.id.content, new PrefsFragment()).commit();
	}

	public static class PrefsFragment extends PreferenceFragment implements
			OnSharedPreferenceChangeListener, Preference.OnPreferenceClickListener {
		final String TAG = this.getClass().getSimpleName();

		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			// Load the preferences from an XML resource
			addPreferencesFromResource(R.xml.preference);
		}

		// For proper lifecycle management in the activity, register and
		// unregister your SharedPreferences.OnSharedPreferenceChangeListener
		// during the onResume() and onPause() callbacks, respectively:
		@Override
		public void onResume() {
			super.onResume();
			Resources res = getResources();
			SharedPreferences preferences = getPreferenceScreen()
					.getSharedPreferences();

			// 註冊所有的ListPreference
			preferences.registerOnSharedPreferenceChangeListener(this);

			// ListPreference
			String key = res.getString(R.string.Pref_keyForCuteDogSetting);
			String[] entries = getResources().getStringArray(
					R.array.entries_ofCuteDog);
			findPreference(key).setOnPreferenceClickListener(this);
			findPreference(key).setSummary(
					getString(R.string.Pref_summaryCurrentlySetting)
							+ " "
							+ entries[Integer.valueOf(preferences.getString(key,
							res.getString(R.string.default_three))) -1]
							+ " " + getString(R.string.Pref_round));
		}

		@Override
		public void onPause() {
			super.onPause();
			Log.d(TAG, "onPause()");
			// 解除ListPreference的註冊
			getPreferenceScreen().getSharedPreferences()
					.unregisterOnSharedPreferenceChangeListener(this);

			// PreferenceScreen的註冊解除不會用
		}

		// ListPreference的點擊事件處理，有數值改變才會觸發
		public void onSharedPreferenceChanged(
				SharedPreferences preferences, String key) {
			Log.d(TAG, "enter onSharedPreferenceChanged");
			// 這支副程式主要處理偏好設定的細項說明部分，the part of summary
			Resources res = getResources();
			if (key.equals(res.getString(R.string.Pref_keyForCuteDogSetting))) {
				// 記帳起始日
				String[] entries = getResources().getStringArray(
						R.array.entries_ofCuteDog);
				findPreference(key).setSummary(
						getString(R.string.Pref_summaryCurrentlySetting)
								+ " "
								+ entries[Integer.valueOf(preferences.getString(key,
								res.getString(R.string.default_three))) -1]
								+ " " + getString(R.string.Pref_round));
			}
		}

		@Override
		public boolean onPreferenceClick(Preference preference) {
			return false;
		}
	}
}
