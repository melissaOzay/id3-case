package com.id3.event_app.ui.event_detail;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.id3.event_app.R;
import com.id3.event_app.ui.event_detail.tabs.comments.CommentsFragment;
import com.id3.event_app.ui.event_detail.tabs.live_status.LiveStatusFragment;
import com.id3.event_app.ui.event_detail.tabs.participants.ParticipantsFragment;

public class EventDetailPagerAdapter extends FragmentStateAdapter {

    public static final int[] TAB_TITLES = {
            R.string.tab_participants,
            R.string.tab_comments,
            R.string.tab_live_status
    };

    private static final int TAB_PARTICIPANTS = 0;
    private static final int TAB_COMMENTS = 1;
    private static final int TAB_LIVE_STATUS = 2;

    private final String eventId;

    public EventDetailPagerAdapter(@NonNull Fragment fragment, String eventId) {
        super(fragment);
        this.eventId = eventId;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case TAB_PARTICIPANTS:
                return ParticipantsFragment.newInstance(eventId);
            case TAB_COMMENTS:
                return CommentsFragment.newInstance(eventId);
            case TAB_LIVE_STATUS:
                return LiveStatusFragment.newInstance(eventId);
            default:
                return ParticipantsFragment.newInstance(eventId);
        }
    }

    @Override
    public int getItemCount() {
        return TAB_TITLES.length;
    }
}
