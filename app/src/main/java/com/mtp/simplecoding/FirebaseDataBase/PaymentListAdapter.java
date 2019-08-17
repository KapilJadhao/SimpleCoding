package com.mtp.simplecoding.FirebaseDataBase;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.mtp.simplecoding.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.mtp.simplecoding.RefranceName.BacgroundImage;

public class PaymentListAdapter extends RecyclerView.Adapter<PaymentListAdapter.MessageViewHolder> {
    private List<paymentPojo> beanClassForCtagorysArrayList = new ArrayList<>();
    private ArrayList<paymentPojo> imageModelArrayList = new ArrayList<>();
    private Context context;
    int quantitycount;
    float total;
    protected ItemListener mListener;

    public PaymentListAdapter(ArrayList<paymentPojo> horizontalList, Context context, ItemListener mListener) {
        this.beanClassForCtagorysArrayList = horizontalList;
        this.imageModelArrayList = new ArrayList<paymentPojo>();
        this.imageModelArrayList.addAll(horizontalList);
        this.context = context;
        this.mListener=mListener;
    }

    @Override
    public int getItemCount() {
        return beanClassForCtagorysArrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return  super.getItemViewType(position);
    }
    @Override
    public void onBindViewHolder(final MessageViewHolder holder, final int position) {
        final paymentPojo model = beanClassForCtagorysArrayList.get(position);
        final MessageViewHolder messageViewHolder = (MessageViewHolder) holder;

        messageViewHolder.tv_owner_name.setText(model.getNameOfOwner());
        messageViewHolder.tv_description.setText(model.getDescription());
        messageViewHolder.tv_duration.setText(model.getFromDate()+" to "+model.getToDate());
        messageViewHolder.tv_status.setText(model.getStatus());
        messageViewHolder.tv_amount.setText(model.getAmount());
        messageViewHolder.iv_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onItemClick(model,position);
                }
            }
        });
        Glide.with(context)
                .asBitmap()
                .load(BacgroundImage)
                .into(new CustomTarget<Bitmap>(){
                    @Override
                    public void onResourceReady( Bitmap resource,  Transition<? super Bitmap> transition) {
                        messageViewHolder.iv_background.setImageBitmap(resource);
                    }

                    @Override
                    public void onLoadCleared( Drawable placeholder) {

                    }
                });

    }

    private  void strikeThroughText(TextView price){
        price.setPaintFlags(price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder {


        TextView tv_owner_name,tv_description,tv_duration,tv_status,tv_amount;
        ImageView iv_remove,iv_background;



        private MessageViewHolder(View view) {
            super(view);

            tv_owner_name=  view.findViewById(R.id.tv_owner_name);
            tv_description=  view.findViewById(R.id.tv_description);
            tv_duration=  view.findViewById(R.id.tv_duration);
            tv_status=  view.findViewById(R.id.tv_status);
            tv_amount=view.findViewById(R.id.tv_amount);
            iv_remove=view.findViewById(R.id.iv_remove);
            iv_background=view.findViewById(R.id.iv_background);


        }
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.payment_list_adapter, parent, false);
        return new MessageViewHolder(itemView);
    }

    public interface ItemListener {
        void onItemClick(paymentPojo item, int position);
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        beanClassForCtagorysArrayList.clear();
        ArrayList<paymentPojo> filters=new ArrayList<paymentPojo>();
        if (charText.length() == 0) {
            beanClassForCtagorysArrayList.addAll(imageModelArrayList);
        } else {
            for (paymentPojo wp : imageModelArrayList) {
               /* if ((wp.get+wp.getApplicantNumber()).toLowerCase(Locale.getDefault())
                        .contains(charText)) {
                    beanClassForCtagorysArrayList.add(wp);
                }*/
            }


        }
        notifyDataSetChanged();
    }

    public void removeAt(int position) {
        /*mDataset.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mDataSet.size());*/
    }
}