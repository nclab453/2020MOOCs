package cycu.nclab.moocs2018;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.avito.android.krop.KropView;

import java.io.ByteArrayOutputStream;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentKrop.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentKrop#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentKrop extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private Bitmap mBitmap;
    Button mOK, mCancel;

    private OnFragmentInteractionListener mListener;

    KropView kropView;

    public FragmentKrop() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment FragmentKrop.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentKrop newInstance(Bitmap param1) {
        FragmentKrop fragment = new FragmentKrop();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        param1.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        Bundle args = new Bundle();
        args.putByteArray(ARG_PARAM1, byteArray);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            byte[] byteArray = getArguments().getByteArray(ARG_PARAM1);
            mBitmap = BitmapFactory.decodeByteArray(byteArray,0, byteArray.length);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_fragment_krop, container, false);

        kropView = v.findViewById(R.id.krop_view);
        kropView.setBitmap(mBitmap);

        mOK = v.findViewById(R.id.button3);
        mOK.setOnClickListener(this);
        mCancel = v.findViewById(R.id.button4);
        mCancel.setOnClickListener(this);
        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button3:
                if (mListener != null) {
                    mListener.onFragmentInteraction(kropView.getCroppedBitmap());
                }
                break;
            case R.id.button4:
                if (mListener != null) {
                    mListener.onFragmentInteraction(mBitmap);
                }
                break;
        }
        getFragmentManager().beginTransaction().remove(FragmentKrop.this).commit();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Bitmap bitmap);
    }
}
