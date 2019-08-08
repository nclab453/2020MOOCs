package cycu.nclab.moocs2018.pref;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;

import cycu.nclab.moocs2018.R;

//所有關於偏好設定的設置以及畫面轉跳的功用程式集放在這裡
public class Prefs {
	//private final String TAG = this.getClass().getSimpleName();
	private final String prefsTag = "cycu.nclab.moocs2018_preferences";

	private static Prefs PrefObject;
	private static SharedPreferences prefs;
	private static Resources res;
	
	final Context context;

	private Prefs(Context context) {
		prefs = context.getSharedPreferences(prefsTag, Context.MODE_PRIVATE);
		res = context.getResources();
		this.context = context;		
	}

	// 單例模式，Singleton
	public static Prefs getInstance(Context c) {
		if (PrefObject == null) {
			synchronized(Prefs.class) {
				if (PrefObject == null)
					PrefObject = new Prefs(c);
			}
		}

		return PrefObject;
	}

	/***************** 設定在PrefsActivity裡面完成，所以只要get就好*************************/
	public static int getCuteDogCircles(Context c) {
		if (PrefObject == null)
			getInstance(c);
		String key = res.getString(R.string.Pref_keyForCuteDogSetting);
		return Integer.valueOf(prefs.getString(key, res.getString(R.string.default_three)));
	}

	public static boolean isRun(Context c) {
		if (PrefObject == null)
			getInstance(c);
		String key = res.getString(R.string.Pref_keyForRun);
		return prefs.getBoolean(key, true);
	}
}
