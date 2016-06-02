package com.monkeybusiness.jaaar.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.monkeybusiness.jaaar.Activity.BaseActivity;
import com.monkeybusiness.jaaar.R;
import com.monkeybusiness.jaaar.objectClasses.DemoDataFlipImageViewer;
import com.monkeybusiness.jaaar.objectClasses.Friend;
import com.monkeybusiness.jaaar.utils.Log;
import com.monkeybusiness.jaaar.utils.Utils;
import com.rey.material.widget.Button;
import com.yalantis.flipviewpager.adapter.BaseFlipAdapter;
import com.yalantis.flipviewpager.utils.FlipSettings;

import java.util.List;

/**
 * @author Yalantis
 */
public class FriendsActivity extends BaseActivity {

    public final String TAG = "FriendsActivity";
    RelativeLayout relativeLayoutMenu;
    TextView textViewActionTitle;
    TextDrawable drawableAbsent;

    //    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return rootView;
//    }
    TextDrawable drawablePresent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);

        toggleLayouts(linearlayoutMyclass, textViewMyclass);
        Utils.classFlag = 2;
        final ListView friends = (ListView) findViewById(R.id.friends);

        relativeLayoutMenu = (RelativeLayout) findViewById(R.id.relativeLayoutMenu);
        textViewActionTitle = (TextView) findViewById(R.id.textViewActionTitle);

        relativeLayoutMenu.setOnClickListener(this);
        textViewActionTitle.setOnClickListener(this);

        textViewActionTitle.setText("My Class");

        FlipSettings settings = new FlipSettings.Builder().defaultPage(1).build();
        friends.setAdapter(new FriendsAdapter(this, DemoDataFlipImageViewer.friends, settings));
        friends.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Friend f = (Friend) friends.getAdapter().getItem(position);
                Log.d(TAG, "onItemClick : " + f.getNickname());
            }
        });
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.relativeLayoutMenu:
                toggle();
                break;
        }
    }

    class FriendsAdapter extends BaseFlipAdapter<Friend> {

        private final int PAGES = 3;
//        private int[] IDS_INTEREST = {R.id.interest_1, R.id.interest_2, R.id.interest_3, R.id.interest_4, R.id.interest_5,R.id.interest_6};

        public FriendsAdapter(Context context, List<Friend> items, FlipSettings settings) {
            super(context, items, settings);
            drawableAbsent = TextDrawable.builder()
                    .beginConfig()
                    .withBorder(4) /* thickness in px */
                    .endConfig()
                    .buildRound("A", getResources().getColor(R.color.absent_button));

            drawablePresent = TextDrawable.builder()
                    .beginConfig()
                    .withBorder(4) /* thickness in px */
                    .endConfig()
                    .buildRound("P", getResources().getColor(R.color.normal_present_button));
        }

        @Override
        public View getPage(int position, View convertView, ViewGroup parent, Friend friend1, Friend friend2) {
            final FriendsHolder holder;

            if (convertView == null) {
                holder = new FriendsHolder();
                convertView = getLayoutInflater().inflate(R.layout.friends_merge_page, parent, false);
                holder.leftAvatar = (ImageView) convertView.findViewById(R.id.first);
                holder.rightAvatar = (ImageView) convertView.findViewById(R.id.second);
                holder.infoPage = getLayoutInflater().inflate(R.layout.friends_info, parent, false);
                holder.nickName = (TextView) holder.infoPage.findViewById(R.id.nickname);

//                for (int id : IDS_INTEREST)
//                    holder.interests.add((ImageView) holder.infoPage.findViewById(id));

                holder.buttonViewRemarks = (Button) holder.infoPage.findViewById(R.id.buttonViewRemarks);
                holder.buttonViewProfile = (Button) holder.infoPage.findViewById(R.id.buttonViewProfile);

                convertView.setTag(holder);
            } else {
                holder = (FriendsHolder) convertView.getTag();
            }

            switch (position) {
                // Merged page with 2 friends
                case 1:
                    holder.leftAvatar.setImageResource(friend1.getAvatar());
                    if (friend2 != null)
                        holder.rightAvatar.setImageResource(friend2.getAvatar());
                    break;
                default:
                    fillHolder(holder, position == 0 ? friend1 : friend2);
                    holder.infoPage.setTag(holder);
                    return holder.infoPage;
            }
            return convertView;
        }

        @Override
        public int getPagesCount() {
            return PAGES;
        }

        private void fillHolder(FriendsHolder holder, Friend friend) {
            if (friend == null)
                return;
//            Iterator<ImageView> iViews = holder.interests.iterator();
//            Iterator<String> iInterests = friend.getInterests().iterator();
//            while (iViews.hasNext() && iInterests.hasNext())
//                if (iInterests.next().equalsIgnoreCase("A"))
//                {
//                    iViews.next().setImageDrawable(drawableAbsent);
//                }else {
//                    iViews.next().setImageDrawable(drawablePresent);
//                }
            holder.infoPage.setBackgroundColor(getResources().getColor(friend.getBackground()));
            holder.nickName.setText(friend.getNickname());
            holder.buttonViewProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "View Profile : " + friend.getNickname());
                }
            });

            holder.buttonViewRemarks.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "View Remarks : " + friend.getNickname());
                }
            });
        }

        class FriendsHolder {
            ImageView leftAvatar;
            ImageView rightAvatar;
            View infoPage;

            //            List<ImageView> interests = new ArrayList<>();
            TextView nickName;

            Button buttonViewRemarks;
            Button buttonViewProfile;
        }
    }

}
