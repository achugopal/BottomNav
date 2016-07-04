package com.example.asvenugo.bottomtabs.fragment;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursorDriver;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQuery;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asvenugo.bottomtabs.MySQLiteOpenHelper;
import com.example.asvenugo.bottomtabs.R;

import java.text.SimpleDateFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class DateFragment extends Fragment {


    @BindView(R.id.selected)
    TextView selectedTxt;
    @BindView(R.id.doneBtn)
    Button doneBtn;
    @BindView(R.id.datePicker)
    DatePicker datePicker;
    private DatePickedListener mListener;
    private Unbinder unbinder;

    public DateFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment DateFragment.
     */
    public static DateFragment newInstance() {
        DateFragment fragment = new DateFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_date, container, false);
        unbinder = ButterKnife.bind(this, view);
        // ButterKnife.bind(this, view);
        return view;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof DatePickedListener) {
            mListener = (DatePickedListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @OnClick(R.id.doneBtn)
    void doneClicked() {

        MySQLiteOpenHelper db = new MySQLiteOpenHelper(getActivity());

        String datesel = db.getDate();

        if (datesel != null) {
            selectedTxt.setText(datesel);
            db.removeDate();
        }



        int date = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year = datePicker.getYear();

       // selectedTxt.setText(date+"/"+month+"/"+year);

        db.addDate(date, month, year);

        String datesel2 = db.getDate();
        selectedTxt.setText(datesel2);

        //selectedTxt.setText(datePicker.getDayOfMonth()+"/"+datePicker.getMonth()+"/"+datePicker.getYear());


        if (mListener != null) {
            mListener.dateClicked(datePicker.getYear(),
                    datePicker.getMonth(),
                    datePicker.getDayOfMonth());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        // ButterKnife.unbind(this);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface DatePickedListener {
        void dateClicked(int year, int month, int day);
    }
}
