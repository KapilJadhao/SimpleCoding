package com.mtp.simplecoding.FirebaseDataBase;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mtp.simplecoding.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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


        messageViewHolder.tv_owner_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mListener != null) {
                    mListener.onItemClick(model);
                }
            }
        });

    }

    private  void strikeThroughText(TextView price){
        price.setPaintFlags(price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder {


        TextView tv_owner_name;



        private MessageViewHolder(View view) {
            super(view);

            tv_owner_name=  view.findViewById(R.id.tv_owner_name);


        }
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.payment_list_adapter, parent, false);
        return new MessageViewHolder(itemView);
    }

    public interface ItemListener {
        void onItemClick(paymentPojo item);
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
}