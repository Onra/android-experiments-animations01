package com.thesuitedowl.animations1;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.transition.ChangeBounds;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.GridLayout;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment implements View.OnClickListener {

        private ViewGroup mLayout1;
        private ViewGroup mLayout2;

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);

            mLayout1 = (ViewGroup) rootView.findViewById(R.id.layout_1);
            mLayout1.findViewById(R.id.item_1a).setOnClickListener(this);
            mLayout1.findViewById(R.id.item_1b).setOnClickListener(this);

            mLayout2 = (ViewGroup) rootView.findViewById(R.id.layout_2);
            mLayout2.findViewById(R.id.item_2a).setOnClickListener(this);
            mLayout2.findViewById(R.id.item_2b).setOnClickListener(this);
            mLayout2.findViewById(R.id.item_2c).setOnClickListener(this);

            return rootView;
        }

        @Override
        public void onClick(View v) {

            if (v.getId() == R.id.item_1a || v.getId() == R.id.item_1b) {
                int selected = mLayout1.indexOfChild(v);
                TransitionManager.beginDelayedTransition(mLayout1, new ChangeBounds());
                mLayout1.removeView(v);
                mLayout1.addView(v, selected == 0 ? mLayout1.getChildCount() : 0);
            } else {
                int selected = mLayout2.indexOfChild(v);
                if (selected > 0) {
                    View cur = mLayout2.getChildAt(0);
                    int currentId = cur.getId();
                    int selectedId = v.getId();
                    TransitionManager.beginDelayedTransition(mLayout2, new ChangeBounds());
                    mLayout2.removeView(v);
                    mLayout2.addView(v, 0);
                    GridLayout.LayoutParams params = (GridLayout.LayoutParams)cur.getLayoutParams();
                    cur.setLayoutParams(v.getLayoutParams());
                    v.setLayoutParams(params);
                    if(currentId == R.id.item_2c ||
                            (currentId == R.id.item_2b &&
                                    selectedId == R.id.item_2c)) {
                        mLayout2.removeView(cur);
                        mLayout2.addView(cur);
                    }
                }
            }
        }
    }

}
