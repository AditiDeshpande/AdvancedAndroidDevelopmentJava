package com.example.android.songdetailstart;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.songdetailstart.content.SongUtils;

/*
MainActivity in this app provides the data to a second activity (SongDetailActivity)
to display the song details on a separate Activity display. To change the app
to provide data for the Fragment , you will change the code that displays
the song detail.
1. If the display is wide enough for a two-pane layout, MainActivity will host
the Fragment , and send the position of the selected song in the list directly
to the Fragment
2. if the screen is not wide enough for a two-pane layout , MainActivity
will use an intent with extra data- the position of the selected song-to start
SongDetailActivity. SongDetailActivity will then host the Fragment , and send
the position of the selected song to the Fragment.

In other words, the Fragment will take over the job of displaying the song
detail. Therefore ur code needs to host the Fragment in MainActivity if the
screen is wide enough for a two-pane display , or in SongDetailActivity if the
screen is not wide enough.
 */

public class SongDetailFragment extends Fragment {

    // SongItem includes the song title and detail.
    public SongUtils.Song mSong;



    public static SongDetailFragment newInstance(int selectedSong){
        SongDetailFragment fragment = new SongDetailFragment();
        //Set the bundle arguments for the fragment
        Bundle arguments = new Bundle();
        arguments.putInt(SongUtils.SONG_ID_KEY, selectedSong);
        fragment.setArguments(arguments);
        return fragment;
    }

    public SongDetailFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static SongDetailFragment newInstance(String param1, String param2) {
        SongDetailFragment fragment = new SongDetailFragment();
        Bundle args = new Bundle();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments().containsKey(SongUtils.SONG_ID_KEY)){
            //Load the content specififed by the fragment arguments
            mSong = SongUtils.SONG_ITEMS.get(getArguments().getInt(SongUtils.SONG_ID_KEY));
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.song_detail, container, false);

        // Show the detail information in a TextView.
        if (mSong != null) {
            ((TextView) rootView.findViewById(R.id.song_detail))
                    .setText(mSong.details);
        }
        return rootView;
    }
}