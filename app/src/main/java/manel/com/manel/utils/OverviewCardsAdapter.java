package manel.com.manel.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import manel.com.manel.R;
import manel.com.manel.activities.AccelerometerActivity;
import manel.com.manel.activities.CommunicationLogActivity;
import manel.com.manel.activities.LabyrinthActivity;
import manel.com.manel.activities.MainMenuActivity;
import manel.com.manel.activities.RemoteControlActivity;
import manel.com.manel.activities.supports.Labyrinth;
import manel.com.manel.activities.supports.LabyrinthMother;

import java.util.ArrayList;

public class OverviewCardsAdapter extends
        RecyclerView.Adapter<OverviewCardsAdapter.ViewHolder> {

    private ArrayList<Integer> mDataset;
    public static final int REMOTE_CONTROL_CASE = 1;
    public static final int LABYRINTH_CASE = 2;
    public static final int ACCELEROMETER_CASE = 3;
    public static final int LOG_CASE = 4;
    private static final String TAG = OverviewCardsAdapter.class
            .getSimpleName() + ".java";
    private static final CharSequence ACCELEROMETER = "ACC";
    private static final CharSequence DRIVE = "DRIVE";
    private static final CharSequence LABYRINTH = "LABYRINTH";
    private static final CharSequence LOG = "LOGGER";

    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder implements OnClickListener {

        private Context mContext;
        public View view;
        private ImageView mImageView;
        private TextView mTextView1, mTextView2;
        private Button mButton;

        /**
         * Constructor.
         * @param view View
         */
        public ViewHolder(View view) {
            super(view);
            this.view = view;
            mContext = view.getContext();
            mImageView = (ImageView) view.findViewById(R.id.card_icon);
            mTextView1 = (TextView) view.findViewById(R.id.card_text_1);
            mTextView2 = (TextView) view.findViewById(R.id.card_text_2);
            mButton = (Button) view.findViewById(R.id.card_button);
        }

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            // Anar a fer el que toqui
        }
    }

    public OverviewCardsAdapter(Context context, ArrayList<Integer> myDataset) {
        mDataset = myDataset;
        this.context = context;
    }

    @Override
    public OverviewCardsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                              int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.card_layout, parent, false);
        return new ViewHolder(v);
    }

    @SuppressLint("NewApi")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Integer card_type = mDataset.get(position);

        switch (card_type) {
            case REMOTE_CONTROL_CASE:
                holder.mImageView.setBackgroundResource(R.mipmap.remote_control);
                holder.mTextView1.setText("REMOTE");
                holder.mTextView2.setText("CONTROL");
                holder.mButton.setText(DRIVE);
                holder.mButton.setBackground(
                        new ColorDrawable(holder.mContext
                        .getResources().getColor(R.color.remote_control_blue)));
                holder.mButton.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        context.startActivity(new Intent(context, RemoteControlActivity.class));
                    }
                });
                break;
            case LABYRINTH_CASE:
                holder.mImageView.setBackgroundResource(R.mipmap.labyrinth);
                holder.mTextView1.setText("GET");
                holder.mTextView2.setText("LOST!");
                holder.mButton.setText(LABYRINTH);
                holder.mButton.setBackground(
                        new ColorDrawable(holder.mContext
                        .getResources().getColor(R.color.labyrinth_red)));
                holder.mButton.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, LabyrinthActivity.class);
                        Labyrinth labyrinth = LabyrinthMother.getLabyrinth(1);
                        intent.putExtra("labyrinth", labyrinth);
                        context.startActivity(intent);
                    }
                });
                break;
            case ACCELEROMETER_CASE:
                holder.mImageView.setBackgroundResource(R.mipmap.accelerometer);
                holder.mTextView1.setText("FEELING");
                holder.mTextView2.setText("DIZZY?");
                holder.mButton.setText(ACCELEROMETER);
                holder.mButton.setBackground(
                        new ColorDrawable(holder.mContext
                        .getResources().getColor(R.color.accelerometer_yellow)));
                holder.mButton.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        context.startActivity(new Intent(context, AccelerometerActivity.class));
                    }
                });
                break;
            case LOG_CASE:
                holder.mImageView.setBackgroundResource(R.mipmap.logger);
                holder.mTextView1.setText("HAVING");
                holder.mTextView2.setText("TROUBLE?");
                holder.mButton.setText(LOG);
                holder.mButton.setBackground(
                        new ColorDrawable(holder.mContext
                                .getResources().getColor(R.color.log_green)));
                holder.mButton.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        context.startActivity(new Intent(context, CommunicationLogActivity.class));
                    }
                });
                break;
            default:
                Log.d(TAG, "Please set the type of Card");
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
