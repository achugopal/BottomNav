package com.example.asvenugo.bottomtabs.fragment;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursorDriver;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQuery;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.asvenugo.bottomtabs.MySQLiteOpenHelper;
import com.example.asvenugo.bottomtabs.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class MailFragment extends Fragment {
    private Cursor c;

    @BindView(R.id.email_button)
    Button sendbtn;
    @BindView(R.id.edit_email)
    EditText editText;

    private EmailPickedListener mListener1;


    private OnFragmentInteractionListener mListener;

    public MailFragment() {
        // Required empty public constructor
    }

    public static MailFragment newInstance() {

        Bundle args = new Bundle();
        MailFragment fragment = new MailFragment();
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
        View view = inflater.inflate(R.layout.fragment_mail, container, false);
        ButterKnife.bind(this, view);
        return view;

    }

    @OnClick(R.id.email_button)
    void doneClicked() {
        String to = editText.getText().toString();
        String subject = "Test Email";

        MySQLiteOpenHelper db = new MySQLiteOpenHelper(getActivity());
        String datesel = db.getDate();

        Intent email = new Intent(Intent.ACTION_SEND);
        email.putExtra(Intent.EXTRA_EMAIL, new String[]{ to});
        email.putExtra(Intent.EXTRA_SUBJECT, subject);
        email.putExtra(Intent.EXTRA_TEXT, datesel);

        email.setType("message/rfc822");
        startActivity(Intent.createChooser(email, "Choose a client"));

        if (mListener1 != null) {
            mListener1.emailClicked(editText.getText().toString());
        }

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public interface EmailPickedListener {
        void emailClicked(String email);
    }
}

