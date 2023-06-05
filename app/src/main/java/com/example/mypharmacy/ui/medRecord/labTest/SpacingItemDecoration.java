package com.example.mypharmacy.ui.medRecord.labTest;

import android.graphics.Rect;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;

public class SpacingItemDecoration extends RecyclerView.ItemDecoration {
    private int spacing;

    public SpacingItemDecoration(int spacing) {
        this.spacing = spacing;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        // Apply spacing to all sides (left, top, right, bottom)
        outRect.left = spacing;
        outRect.right = spacing;
        outRect.top = spacing;
        outRect.bottom = spacing;
    }
}
