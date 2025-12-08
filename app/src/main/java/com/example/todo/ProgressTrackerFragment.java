package com.example.todo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

public class ProgressTrackerFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.progress_tracker, container, false);

        ProgressBar progressTracker = view.findViewById(R.id.progressBar);
        TextView progressPercent = view.findViewById(R.id.textView_progress_percent);

        // Progress 100%
        final int STATIC_PROGRESS = 100;

        if (progressTracker != null) {
            progressTracker.setProgress(STATIC_PROGRESS);
        }

        if (progressPercent != null) {
            progressPercent.setText(STATIC_PROGRESS + "%");
        }

        return view;
    }
}