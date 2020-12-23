/*
 * Copyright (C) 2017 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.fragmentdynamicandfragcommu;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass to display
 * radio buttons.
 */
public class SimpleFragment extends Fragment {

    // The radio button choice has 3 states: 0 = yes, 1 = no,
    // 2 = default (no choice). Using only 0 and 1.
    private static final int YES = 0;
    private static final int NO = 1;
    private static final int NONE = 2;

    public int mRadioButtonChoice = NONE;

    OnFragmentInteractionListener mListener;

    private static final String CHOICE = "choice";

    public SimpleFragment() {
        // Required empty public constructor
    }

    public static SimpleFragment newInstance(int choice){
        SimpleFragment fragment = new SimpleFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(CHOICE,choice);
        fragment.setArguments(arguments);
        return fragment;
    }


    /**
     * Creates the view for the fragment.
     *
     * @param inflater           LayoutInflater
     * @param container          ViewGroup
     * @param savedInstanceState Bundle
     * @return View
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment.
        final View rootView = inflater.inflate(R.layout.fragment_simple,
                container, false);
        final RadioGroup radioGroup = rootView.findViewById(R.id.radio_group);

        /*
        This choice is passed from Activity
        while instantiating the SimpleFragment. Set in callback in MainActivity
        and originally set in the Fragment itself in checkchangelistener(this
        calles the callback from MainActivity)
        Now that the bundle of arguments is available in the SimpleFragment,
        you can add code to the onCreateView() method in SimpleFragment to get
        the choice from the Bundle.Right before the statement that sets
        the radioGroup onCheckChangedListener, so added following code
        to retrieve the radio button choice (if a choice was made),
        and pre-select the radioButton.
         */

        if(getArguments().containsKey(CHOICE)){
            //A choice was made so get the choice.
            mRadioButtonChoice = getArguments().getInt(CHOICE);
            //check the radiobutton choice
            if(mRadioButtonChoice != NONE){
                radioGroup.check(
                        radioGroup.getChildAt(mRadioButtonChoice).getId()
                );
            }

        }

        // Set the radioGroup onCheckedChanged listener.
        radioGroup.setOnCheckedChangeListener(
                new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        View radioButton = radioGroup.findViewById(checkedId);
                        int index = radioGroup.indexOfChild(radioButton);
                        TextView textView =
                                rootView.findViewById(R.id.fragment_header);
                        switch (index) {
                            case YES: // User chose "Yes".
                                textView.setText(R.string.yes_message);
                                mRadioButtonChoice = YES;
                                mListener.onRadioButtonChoice(YES);
                                break;
                            case NO: // User chose "No".
                                textView.setText(R.string.no_message);
                                mRadioButtonChoice = NO;
                                mListener.onRadioButtonChoice(NO);
                                break;
                            default: // No choice made.
                                // Do nothing.
                                mRadioButtonChoice = NONE;
                                mListener.onRadioButtonChoice(NONE);
                                break;
                        }
                    }
                });

        // Return the View for the fragment's UI.
        return rootView;
    }

    interface OnFragmentInteractionListener{
        void onRadioButtonChoice(int choice);
    }

    /*
    onAttach() method is called as soon as the Frag is associated with
    the Activity. The code makes sure that the host Activity has implemented
    the callback interface. If not, it throws exception.
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof OnFragmentInteractionListener){
            mListener = (OnFragmentInteractionListener) context;
        }else{
            throw new ClassCastException(context.toString()
                    + getResources().getString(R.string.exception_message));
        }
    }
}
