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

package com.example.android.songdetailstart;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.songdetailstart.content.SongUtils;

import java.util.List;

/**
 * An activity representing a list of song titles (items). When one is
 * touched, an intent starts {@link SongDetailActivity} representing
 * song details.
 */
public class MainActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */

    private boolean mTwoPane = false;



    /**
     * Sets up a song list as a RecyclerView.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_list);

        // Set the toolbar as the app bar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        // Get the song list as a RecyclerView.
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.song_list);
        recyclerView.setAdapter
                (new SimpleItemRecyclerViewAdapter(SongUtils.SONG_ITEMS));

        /*
        The following code checks for the screen size and orientation.
        The song_detail_container view for MainActivity will be present
        only if the screen's width is 900dp or larger, because it is defined
        only in the song_list.xml(w900dp) layout , not in the default song_list.xml
        layout for small screen sizes. If this view is present, then the activity
        should be in two-pane mode.

        if a tablet is set to portrait orientation , its width will most likely to be
        lower than 900dp, and so it will not show a two-pane layout. If the tablet
        is set to horizontal orientation and its width is 900dp or larger , it will
        show a two-pane layout.
        activity_song_list is layout in it including song_list only 900 one has
        song_detail_container and it's loading at song list itself. If not
        screen size 900 it won't be there default song_list will be there
        so it will go to SongDetailActivity it's layout and it's own container
        with same name as following but activity's container
         */
        if(findViewById(R.id.song_detail_container) != null){
            mTwoPane = true;
        }
    }

    /**
     * The ReyclerView for the song list.
     */
    class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter
            <SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final List<SongUtils.Song> mValues;

        SimpleItemRecyclerViewAdapter(List<SongUtils.Song> items) {
            mValues = items;
        }

        /**
         * This method inflates the layout for the song list.
         * @param parent ViewGroup into which the new view will be added.
         * @param viewType The view type of the new View.
         * @return
         */
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.song_list_content, parent, false);
            return new ViewHolder(view);
        }

        /**
         * This method implements a listener with setOnClickListener().
         * When the user taps a song title, the code checks if mTwoPane
         * is true, and if so uses a fragment to show the song detail.
         * If mTwoPane is not true, it starts SongDetailActivity
         * using an intent with extra data about which song title was selected.
         *
         * @param holder   ViewHolder
         * @param position Position of the song in the array.
         */
        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mItem = mValues.get(position);
            holder.mIdView.setText(String.valueOf(position + 1));
            holder.mContentView.setText(mValues.get(position).song_title);
            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*
                    following extra data should be sent to the Fragment
                    instead of activity. Removing code for Activity.
                     */
                    if(mTwoPane){
                        int selectedSong = holder.getAdapterPosition();
                        SongDetailFragment fragment = SongDetailFragment.newInstance(selectedSong);
                        /*
                        replace transaction to show a new version of the fragment.
                        By replacing the fragment u can refresh with new data a fragment is already
                        running. This is from song_list layout rt?
                        Here we r replacing the fragment.
                         */
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.song_detail_container , fragment)
                                .addToBackStack(null)
                                .commit();
                    }else{
                        Context context = v.getContext();
                        Intent intent = new Intent(context , SongDetailActivity.class);
                        intent.putExtra(SongUtils.SONG_ID_KEY ,
                                holder.getAdapterPosition());
                        context.startActivity(intent);
                    }
                }
            });
        }

        /**
         * Get the count of song list items.
         * @return
         */
        @Override
        public int getItemCount() {
            return mValues.size();
        }

        /**
         * ViewHolder describes an item view and metadata about its place
         * within the RecyclerView.
         */
        class ViewHolder extends RecyclerView.ViewHolder {
            final View mView;
            final TextView mIdView;
            final TextView mContentView;
            SongUtils.Song mItem;

            ViewHolder(View view) {
                super(view);
                mView = view;
                mIdView = (TextView) view.findViewById(R.id.id);
                mContentView = (TextView) view.findViewById(R.id.content);
            }
        }
    }
}
