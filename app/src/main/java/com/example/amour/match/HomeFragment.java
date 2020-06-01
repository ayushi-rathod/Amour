package com.example.amour.match;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.StackView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.amour.R;

import java.util.ArrayList;
import java.util.Collections;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    public HomeFragment() {
        // Required empty public constructor
    }

    StackView stackView;
    private static final String TAG = "MainActivity";

    int windowwidth;
    int screenCenter;
    int x_cord, y_cord, x, y;
    int Likes = 0;
    public RelativeLayout parentView;
    float alphaValue = 0;
    private Context context;

    ArrayList<ItemModel> userDataModelArrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //getActivity().requestWindowFeature(Window.FEATURE_NO_TITLE);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = getActivity();

        parentView = (RelativeLayout) view.findViewById(R.id.activity_frame);

        windowwidth = getActivity().getWindowManager().getDefaultDisplay().getWidth();

        screenCenter = windowwidth / 2;
        userDataModelArrayList = new ArrayList<>();

        getArrayData();


        for (int i = 0; i < userDataModelArrayList.size(); i++) {

            LayoutInflater inflate =
                    (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            final View containerView = inflate.inflate(R.layout.custom_cardview, null);

            ImageView userIMG = (ImageView) containerView.findViewById(R.id.userIMG);
            RelativeLayout relativeLayoutContainer = (RelativeLayout) containerView.findViewById(R.id.relative_container);


            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            containerView.setLayoutParams(layoutParams);

            containerView.setTag(i);
            userIMG.setBackgroundResource(userDataModelArrayList.get(i).getImage());

//            m_view.setRotation(i);
//            containerView.setPadding(0, i, 0, 0);

            LinearLayout.LayoutParams layoutTvParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);


            // ADD dynamically like TextView on image.
            final TextView tvLike = new TextView(context);
            tvLike.setLayoutParams(layoutTvParams);
            tvLike.setPadding(10, 10, 10, 10);
            tvLike.setBackgroundDrawable(getResources().getDrawable(R.drawable.btnlikeback));
            tvLike.setText("LIKE");
            tvLike.setGravity(Gravity.CENTER);
            tvLike.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            tvLike.setTextSize(25);
            tvLike.setTextColor(ContextCompat.getColor(context, R.color.colorRed));
            tvLike.setX(20);
            tvLike.setY(100);
            tvLike.setRotation(-50);
            tvLike.setAlpha(alphaValue);
            relativeLayoutContainer.addView(tvLike);


//            ADD dynamically dislike TextView on image.
            final TextView tvUnLike = new TextView(context);
            tvUnLike.setLayoutParams(layoutTvParams);
            tvUnLike.setPadding(10, 10, 10, 10);
            tvUnLike.setBackgroundDrawable(getResources().getDrawable(R.drawable.btnlikeback));
            tvUnLike.setText("UNLIKE");
            tvUnLike.setGravity(Gravity.CENTER);
            tvUnLike.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            tvUnLike.setTextSize(25);
            tvUnLike.setTextColor(ContextCompat.getColor(context, R.color.colorRed));
            tvUnLike.setX(500);
            tvUnLike.setY(150);
            tvUnLike.setRotation(50);
            tvUnLike.setAlpha(alphaValue);
            relativeLayoutContainer.addView(tvUnLike);


            TextView tvName = (TextView) containerView.findViewById(R.id.tvName);
            TextView tvTotLikes = (TextView) containerView.findViewById(R.id.tvTotalLikes);


            tvName.setText(userDataModelArrayList.get(i).getName());
            tvTotLikes.setText(userDataModelArrayList.get(i).getTotalLikes());

            // Touch listener on the image layout to swipe image right or left.
            relativeLayoutContainer.setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    x_cord = (int) event.getRawX();
                    y_cord = (int) event.getRawY();

                    containerView.setX(0);
                    containerView.setY(0);

                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:

                            x = (int) event.getX();
                            y = (int) event.getY();


                            Log.v("On touch", x + " " + y);
                            break;
                        case MotionEvent.ACTION_MOVE:

                            x_cord = (int) event.getRawX();
                            // smoother animation.
                            y_cord = (int) event.getRawY();

                            containerView.setX(x_cord - x);
                            containerView.setY(y_cord - y);


                            if (x_cord >= screenCenter) {
                                containerView.setRotation((float) ((x_cord - screenCenter) * (Math.PI / 32)));
                                if (x_cord > (screenCenter + (screenCenter / 2))) {
                                    tvLike.setAlpha(1);
                                    if (x_cord > (windowwidth - (screenCenter / 4))) {
                                        Likes = 2;
                                    } else {
                                        Likes = 0;
                                    }
                                } else {
                                    Likes = 0;
                                    tvLike.setAlpha(0);
                                }
                                tvUnLike.setAlpha(0);
                            } else {
                                // rotate image while moving
                                containerView.setRotation((float) ((x_cord - screenCenter) * (Math.PI / 32)));
                                if (x_cord < (screenCenter / 2)) {
                                    tvUnLike.setAlpha(1);
                                    if (x_cord < screenCenter / 4) {
                                        Likes = 1;
                                    } else {
                                        Likes = 0;
                                    }
                                } else {
                                    Likes = 0;
                                    tvUnLike.setAlpha(0);
                                }
                                tvLike.setAlpha(0);
                            }

                            break;
                        case MotionEvent.ACTION_UP:

                            x_cord = (int) event.getRawX();
                            y_cord = (int) event.getRawY();

                            Log.e("X Point", "" + x_cord + " , Y " + y_cord);
                            tvUnLike.setAlpha(0);
                            tvLike.setAlpha(0);

                            if (Likes == 0) {
                                Toast.makeText(context, "NOTHING", Toast.LENGTH_SHORT).show();
                                Log.e("Event_Status :-> ", "Nothing");
                                containerView.setX(0);
                                containerView.setY(0);
                                containerView.setRotation(0);
                            } else if (Likes == 1) {
                                Toast.makeText(context, "UNLIKE", Toast.LENGTH_SHORT).show();
                                Log.e("Event_Status :-> ", "UNLIKE");
                                parentView.removeView(containerView);
                            } else if (Likes == 2) {
                                Toast.makeText(context, "LIKED", Toast.LENGTH_SHORT).show();
                                Log.e("Event_Status :-> ", "Liked");
                                parentView.removeView(containerView);
                            }
                            break;
                        default:
                            break;
                    }
                    return true;
                }
            });

            parentView.addView(containerView);

        }
    }

    private void getArrayData() {

        userDataModelArrayList.add(new ItemModel(R.drawable.sample1, "Markonah", "24", "Jember"));
        userDataModelArrayList.add(new ItemModel(R.drawable.sample2, "Marpuah", "20", "Malang"));
        userDataModelArrayList.add(new ItemModel(R.drawable.sample3, "Sukijah", "27", "Jonggol"));
        userDataModelArrayList.add(new ItemModel(R.drawable.sample4, "Markobar", "19", "Bandung"));
        userDataModelArrayList.add(new ItemModel(R.drawable.sample5, "Marmut", "25", "Hutan"));

        userDataModelArrayList.add(new ItemModel(R.drawable.sample1, "Markonah", "24", "Jember"));
        userDataModelArrayList.add(new ItemModel(R.drawable.sample2, "Marpuah", "20", "Malang"));
        userDataModelArrayList.add(new ItemModel(R.drawable.sample3, "Sukijah", "27", "Jonggol"));
        userDataModelArrayList.add(new ItemModel(R.drawable.sample4, "Markobar", "19", "Bandung"));
        userDataModelArrayList.add(new ItemModel(R.drawable.sample5, "Marmut", "25", "Hutan"));

        Collections.reverse(userDataModelArrayList);

    }
}


//ImageView imageView = (ImageView) getView().findViewById(R.id.foo);
// CardStackView cardStackView = (CardStackView) getView().findViewById(R.id.card_stack_view);
// manager = new CardStackLayoutManager(this, new CardStackListener() {
         /*   @Override
            public void onCardDragging(Direction direction, float ratio) {
                Log.d(TAG, "onCardDragging: d=" + direction.name() + " ratio=" + ratio);
            }
*/
          /*  @Override
            public void onCardSwiped(Direction direction) {
                Log.d(TAG, "onCardSwiped: p=" + manager.getTopPosition() + " d=" + direction);
               if (direction == Direction.Right){
                    Toast.makeText(getApplicationContext(), "Right Swipe", Toast.LENGTH_SHORT).show();
                }

                if (direction == Direction.Left) {
                    Toast.makeText(getApplicationContext(), " Left Swipe", Toast.LENGTH_SHORT).show();
                }


                // Paginating
                if (manager.getTopPosition() == adapter.getItemCount() - 5){
                    paginate();
                }

            }

            private void getApplicationContext() {
            }

            @Override
            public void onCardRewound() {
                Log.d(TAG, "onCardRewound: " + manager.getTopPosition());
            }

            @Override
            public void onCardCanceled() {
                Log.d(TAG, "onCardRewound: " + manager.getTopPosition());
            }

            @Override
            public void onCardAppeared(View view, int position) {
                TextView tv = view.findViewById(R.id.item_name);
                Log.d(TAG, "onCardAppeared: " + position + ", nama: " + tv.getText());
            }

            @Override
            public void onCardDisappeared(View view, int position) {
                TextView tv = view.findViewById(R.id.item_name);
                Log.d(TAG, "onCardAppeared: " + position + ", nama: " + tv.getText());
            }
        });
        manager.setStackFrom(StackFrom.None);
        manager.setVisibleCount(3);
        manager.setTranslationInterval(8.0f);
        manager.setScaleInterval(0.95f);
        manager.setSwipeThreshold(0.3f);
        manager.setMaxDegree(20.0f);
        manager.setDirections(Direction.FREEDOM);
        manager.setCanScrollHorizontal(true);
        manager.setSwipeableMethod(SwipeableMethod.Manual);
        manager.setOverlayInterpolator(new LinearInterpolator());
        adapter = new CardStackAdapter(addList());
        cardStackView.setLayoutManager(manager);
        cardStackView.setAdapter(adapter);
        cardStackView.setItemAnimator(new DefaultItemAnimator());

        }


   private void paginate() {
        List<ItemModel> old = adapter.getItems();
        List<ItemModel> baru = new ArrayList<>(addList());
        CardStackCallback callback = new CardStackCallback(old, baru);
        DiffUtil.DiffResult hasil = DiffUtil.calculateDiff(callback);
        adapter.setItems(baru);
        hasil.dispatchUpdatesTo(adapter);
    }


    private List<ItemModel> addList() {
        List<ItemModel> items = new ArrayList<>();
        items.add(new ItemModel(R.drawable.sample1, "Markonah", "24", "Jember"));
        items.add(new ItemModel(R.drawable.sample2, "Marpuah", "20", "Malang"));
        items.add(new ItemModel(R.drawable.sample3, "Sukijah", "27", "Jonggol"));
        items.add(new ItemModel(R.drawable.sample4, "Markobar", "19", "Bandung"));
        items.add(new ItemModel(R.drawable.sample5, "Marmut", "25", "Hutan"));

        items.add(new ItemModel(R.drawable.sample1, "Markonah", "24", "Jember"));
        items.add(new ItemModel(R.drawable.sample2, "Marpuah", "20", "Malang"));
        items.add(new ItemModel(R.drawable.sample3, "Sukijah", "27", "Jonggol"));
        items.add(new ItemModel(R.drawable.sample4, "Markobar", "19", "Bandung"));
        items.add(new ItemModel(R.drawable.sample5, "Marmut", "25", "Hutan"));
        return items;
    }*/