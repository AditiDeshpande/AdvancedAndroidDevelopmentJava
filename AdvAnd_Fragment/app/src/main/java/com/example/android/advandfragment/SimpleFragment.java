package com.example.android.advandfragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SimpleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SimpleFragment extends Fragment {

    private static final int YES = 0;
    private static final int NO = 1;

    public SimpleFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final View rootView =
                inflater.inflate(R.layout.fragment_simple,
                container, false);
        final RadioGroup radioGroup = rootView.findViewById(R.id.radio_group);
        final RatingBar ratingBar = rootView.findViewById(R.id.ratingBar);

        radioGroup.
                setOnCheckedChangeListener(
                        new RadioGroup.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(RadioGroup group, int checkedId) {
                                View radioButton = group.findViewById(checkedId);
                                int index = group.indexOfChild(radioButton);

                                TextView textView =
                                        rootView.findViewById(R.id.fragment_header);
                                switch(index){
                                    case YES:
                                        textView.setText(R.string.yes_message);
                                        break;

                                    case NO:
                                        textView.setText(R.string.no_message);
                                        break;

                                    default:
                                        //No choice made
                                        //Do nothing
                                        break;

                                }

                            }
                        });


        ratingBar.setOnRatingBarChangeListener(
                new RatingBar.OnRatingBarChangeListener() {
                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                    //Get rating and show Toast with Rating
                    String myRating = (getString(R.string.my_rating) +
                            String.valueOf(ratingBar.getRating()));
                        Toast.makeText(getContext(), myRating, Toast.LENGTH_SHORT).show();
                    }
                }
        );

        return rootView;

    }
}