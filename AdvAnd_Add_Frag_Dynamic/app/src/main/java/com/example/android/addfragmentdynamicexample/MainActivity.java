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

package com.example.android.addfragmentdynamicexample;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button mButton;
    private boolean isFragmentDisplayed = false;
    /*
    To save the boolean value representing the Fragment display state
    define a key for the Fragment state to use in the savedInstanceState
    bundle
     */
    static final String STATE_FRAGMENT ="state_of_fragment";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButton = findViewById(R.id.open_button);
        if(savedInstanceState != null){
            isFragmentDisplayed =
                    savedInstanceState.getBoolean(STATE_FRAGMENT);
            if(isFragmentDisplayed) {
                //If the Frag is displayed ,change button to close
                mButton.setText(R.string.close);
            }
        }
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isFragmentDisplayed){
                    displayFragment();
                } else{
                  closeFragment();
                }
            }
        });

    }

    public void displayFragment(){
        SimpleFragment simpleFragment = SimpleFragment.newInstance();

        //Get the FragmentManager and start a transaction
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        //Add the simple Fragment
        //First argument passed to add is the layout resource
        //(fragment_container) for the ViewGroup in which the
        //Fragment should be placed.
        //The second param is the Fragment (simpleFragment) to add

        /*
        In addition to the add() transaction , the code calls
        addToBackStack(null) in order to add the transaction to a
        backstack of Fragment transactions. This BackStack is managed
        by the Activity. It allows the user to return to the previous
        Fragment state by pressing back button.
         */
        fragmentTransaction.add(R.id.fragment_container,
                simpleFragment).addToBackStack(null).commit();

        //Update the Button Text
        mButton.setText(R.string.close);
        //Set boolean flag to indicate fragment is open
        isFragmentDisplayed = true;
    }

    public void closeFragment(){
        //Get the FragmentManager
        FragmentManager fragmentManager = getSupportFragmentManager();
        //check to see if the Fragment is already showing.
        SimpleFragment simpleFragment = (SimpleFragment) fragmentManager.
                findFragmentById(R.id.fragment_container);
        /*
        However b4 creating this transaction, the code checks to see
        if the Fragment is displayed (not null)
        If the Frag is not displayed there is nothing to remove.
         */
        if(simpleFragment != null )
        {
            //Create and commit the Transaction to remove the fragment
            FragmentTransaction fragmentTransaction =
                    fragmentManager.beginTransaction();
            fragmentTransaction.remove(simpleFragment).commit();
        }
        //Update the Button Text
        mButton.setText(R.string.open);

        //Set boolean flag to indicate fragment is closed
        isFragmentDisplayed = false;

    }

    // To save the state of the Fragment is the configuration changes
    //
    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        //Save the state of the Fragment (true = open , false = closed)
        savedInstanceState.putBoolean(STATE_FRAGMENT ,isFragmentDisplayed);
        super.onSaveInstanceState(savedInstanceState);
    }
}
