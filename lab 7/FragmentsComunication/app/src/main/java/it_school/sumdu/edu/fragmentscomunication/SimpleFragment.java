package it_school.sumdu.edu.fragmentscomunication;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;


public class SimpleFragment extends Fragment {


    private static final int Yes = 0;
    private static final int No = 1;
    private static final int Nothing = 2;

    private int mRadioButtonChoice = Nothing;

    private static final String UserPick = "up";

    OnFragmentInteractionListener mListener;

    public SimpleFragment() {
    }

    interface OnFragmentInteractionListener {
        void onRadioButtonChoice(int choice);
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new ClassCastException(context
                    + getResources().getString(R.string.exception_message));
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_simple,
                container, false);
        final RadioGroup radioGroup = rootView.findViewById(R.id.radio_group);

        assert getArguments() != null;
        if (getArguments().containsKey(UserPick)) {
            mRadioButtonChoice = getArguments().getInt(UserPick);
            if (mRadioButtonChoice != Nothing) {
                radioGroup.check
                        (radioGroup.getChildAt(mRadioButtonChoice).getId());
            }
        }

        radioGroup.setOnCheckedChangeListener(
                (group, checkedId) -> {
                    View radioButton = radioGroup.findViewById(checkedId);
                    int index = radioGroup.indexOfChild(radioButton);
                    TextView textView =
                            rootView.findViewById(R.id.fragment_header);
                    switch (index) {
                        case Yes:
                            textView.setText(R.string.yes_message);
                            mRadioButtonChoice = Yes;
                            mListener.onRadioButtonChoice(Yes);
                            break;
                        case No:
                            textView.setText(R.string.no_message);
                            mRadioButtonChoice = No;
                            mListener.onRadioButtonChoice(No);
                            break;
                        default:
                            mRadioButtonChoice = Nothing;
                            mListener.onRadioButtonChoice(Nothing);
                            break;
                    }
                });

        return rootView;
    }

      public static SimpleFragment newInstance(int choice) {
        SimpleFragment fragment = new SimpleFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(UserPick, choice);
        fragment.setArguments(arguments);
        return fragment;
    }

}