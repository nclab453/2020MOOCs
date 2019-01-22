/*  這個類別實作DatePickerDialog，用來讓使用者設定時間
* @author hhliu, 2018/12/12
* @exception RuntimeException()
*
* 呼叫方式：
* 1. 可以輸入預設日期
* DialogFragment dateFragment = DatePickerFragment.newInstance(Calendar c);
* dateFragment.show(getSupportFragmentManager(), "datePicker");
* 2. 以目前時間為預設日期
* DialogFragment dateFragment = DatePickerFragment.newInstance();
* dateFragment.show(getSupportFragmentManager(), "datePicker");
*
* 回傳方式：
* 實作 OnDatePickerFragmentListener 介面，實作 onDateSet(Calendar c)
 */


package cycu.nclab.moocs2018;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    private OnDatePickerFragmentListener mListener;
    private static final String ARG_PARAM1 = "param1";
    static Calendar c;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        c = Calendar.getInstance();
        Bundle args = getArguments();
        if (args != null) {
            c.setTimeInMillis(args.getLong(ARG_PARAM1));
        }
        c.setTimeInMillis(getArguments().getLong(ARG_PARAM1));
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day) {
            @Override
            public void onDateChanged(@android.support.annotation.NonNull DatePicker view, int year, int month, int dayOfMonth) {
                super.onDateChanged(view, year, month, dayOfMonth);
                onDateSet(view, year, month, dayOfMonth);
                this.dismiss();
            }
        };
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Do something with the date chosen by the user
        c.set(year, month, day);

        if (mListener != null) {
            mListener.onDateSet(c);
        }
    }

    public static DatePickerFragment newInstance(Calendar c) {

        DatePickerFragment fragment = new DatePickerFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_PARAM1, c.getTimeInMillis());

        fragment.setArguments(args);
        return fragment;
    }

    public static DatePickerFragment newInstance() {
        DatePickerFragment fragment = new DatePickerFragment();
        return fragment;
    }

    public interface OnDatePickerFragmentListener {
        // TODO: Update argument type and name
        void onDateSet(Calendar c);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnDatePickerFragmentListener) {
            mListener = (OnDatePickerFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnDatePickerFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}

//
///**
// * A simple {@link Fragment} subclass.
// * Activities that contain this fragment must implement the
// * {@link DatePickerFragment.OnFragmentInteractionListener} interface
// * to handle interaction events.
// * Use the {@link DatePickerFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
//public class DatePickerFragment extends Fragment {
//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;
//
//    private OnFragmentInteractionListener mListener;
//
//    public DatePickerFragment() {
//        // Required empty public constructor
//    }
//
//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment DatePickerFragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static DatePickerFragment newInstance(String param1, String param2) {
//        DatePickerFragment fragment = new DatePickerFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        TextView textView = new TextView(getActivity());
//        textView.setText(R.string.hello_blank_fragment);
//        return textView;
//    }
//
//    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }
//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }
//
//    /**
//     * This interface must be implemented by activities that contain this
//     * fragment to allow an interaction in this fragment to be communicated
//     * to the activity and potentially other fragments contained in that
//     * activity.
//     * <p>
//     * See the Android Training lesson <a href=
//     * "http://developer.android.com/training/basics/fragments/communicating.html"
//     * >Communicating with Other Fragments</a> for more information.
//     */
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(Uri uri);
//    }
//}




//，用來讓使用者設定時間
//        * @hhliu, 2018/12/12
//        * @exception RuntimeException()
//        *
//        * 呼叫方法：
//        * 1. DialogFragment dateFragment = DatePickerFragment.newInstance(Calendar c);  輸入預設日期
//        *    dateFragment.show(getSupportFragmentManager(), "datePicker");
//        * 2. DialogFragment dateFragment = DatePickerFragment.newInstance();  以目前時間作為預設日期
//        *    dateFragment.show(getSupportFragmentManager(), "datePicker");
//        *
//        * 回傳方法：
//        * 實作 OnDatePickerFragmentListener 介面的 onDateSet(Calendar c)
//        */