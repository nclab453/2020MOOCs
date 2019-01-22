/*
  @author hhliu, 2018/12/12
  @exception RuntimeException()
  Usage:
      // 使用現在時間當作初始時間
  1.  DialogFragment timeFragment = TimePickerFragment.newInstance();
      timeFragment.show(getSupportFragmentManager(), "timePicker");
      // 輸入參數作為初始時間
  2.  DialogFragment timeFragment = TimePickerFragment.newInstance(Calendar c);
      timeFragment.show(getSupportFragmentManager(), "timePicker");

   Callbacks:
   implements OnTimePickerFragmentListener { onTimeSet(Calendar c) }
 */

package cycu.nclab.moocs2018;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.TimePicker;

import java.util.Calendar;

public class TimePickerFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {

    final String TAG = this.getClass().getSimpleName();

    private OnTimePickerFragmentListener mListener;
    private static final String ARG_PARAM1 = "param1";

    Calendar c;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        c = Calendar.getInstance();
        Bundle args = getArguments();
        if (args != null) {
            c.setTimeInMillis(args.getLong(ARG_PARAM1));
        }

        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity())) {
//            @Override
//            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
//                super.onTimeChanged(view, hourOfDay, minute);
//                onTimeSet(view, hourOfDay, minute);
//                dismiss();
//            }

        };
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        // Do something with the time chosen by the user
        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, minute);

        if (mListener != null) {
            mListener.onTimeSet(c);
        }
    }


    public static TimePickerFragment newInstance(Calendar c) {
        TimePickerFragment fragment = new TimePickerFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_PARAM1, c.getTimeInMillis());
        fragment.setArguments(args);
        return fragment;
    }

    public static TimePickerFragment newInstance() {
        TimePickerFragment fragment = new TimePickerFragment();
        return fragment;
    }


    public interface OnTimePickerFragmentListener {
        // TODO: Update argument type and name
        void onTimeSet(Calendar c);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnTimePickerFragmentListener) {
            mListener = (OnTimePickerFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnTimePickerFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

}
