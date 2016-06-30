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

import com.example.asvenugo.bottomtabs.R;

import java.text.SimpleDateFormat;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class DateFragment extends Fragment {

    private SQLiteDatabase db;
    private Cursor c;

    @Bind(R.id.selected)
    TextView selectedTxt;
    @Bind(R.id.doneBtn)
    Button doneBtn;
    @Bind(R.id.datePicker)
    DatePicker datePicker;
    private DatePickedListener mListener;

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
        ButterKnife.bind(this, view);
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

        SQLiteDatabase.CursorFactory cursorFactory = new SQLiteDatabase.CursorFactory() {
            @Override
            public Cursor newCursor(SQLiteDatabase db, SQLiteCursorDriver masterQuery, String editTable, SQLiteQuery query) {
                return null;
            }
        };


        db = SQLiteDatabase.openOrCreateDatabase("Date", cursorFactory, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS datesel(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, date INTEGER, month INTEGER, year INTEGER);");

        c = db.rawQuery("SELECT * FROM datesel;", null);
        if (c!=null) {
            String sql = "DELETE FROM datesel;";
            db.execSQL(sql);
        }


        int date = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year = datePicker.getYear();

        String query = "INSERT INTO datesel (date,month, year) VALUES('"+date+"', '"+month+"', '"+year+"');";
        db.execSQL(query);
     //   Toast.makeText(context,"Saved Successfully", Toast.LENGTH_LONG).show();

        selectedTxt.setText(date+"/"+month+"/"+year);

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
        ButterKnife.unbind(this);
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
