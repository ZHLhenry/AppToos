package com.henry.apptools.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.henry.apptools.R;
import com.henry.apptools.entity.SignModel;

import java.util.List;

/**
 * Author:henry
 * Time:2019-06-04 17:26:26
 * ClassName:This is CustomDialogAdapter
 * Remark:不要冲动,杀人犯法
 * Description:description
 */
public class CustomDialogAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<SignModel> mSignModels;

    public CustomDialogAdapter(Context context, List<SignModel> signModels) {
        this.mContext = context;
        this.mSignModels = signModels;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new CustomDialogViewHilder(LayoutInflater.from(mContext).inflate(R.layout.adapter_custom, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        CustomDialogViewHilder customDialogViewHilder = (CustomDialogViewHilder) viewHolder;
        customDialogViewHilder.getCustom_name_tv().setText(String.format("%s:", mSignModels.get(i).getName()));
        customDialogViewHilder.getCustom_val_tv().setText(mSignModels.get(i).getValue());
        customDialogViewHilder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (itemClicklistener != null) itemClicklistener.onItemClicklistener(i, view);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mSignModels == null ? 0 : mSignModels.size();
    }

    class CustomDialogViewHilder extends RecyclerView.ViewHolder {
        private TextView custom_name_tv, custom_val_tv;

        public CustomDialogViewHilder(@NonNull View itemView) {
            super(itemView);
            custom_name_tv = itemView.findViewById(R.id.custom_name_tv);
            custom_val_tv = itemView.findViewById(R.id.custom_val_tv);
        }

        public TextView getCustom_name_tv() {
            return custom_name_tv;
        }

        public TextView getCustom_val_tv() {
            return custom_val_tv;
        }
    }

    //region    点击事件
    private ItemClicklistener itemClicklistener;

    public void setItemClicklistener(ItemClicklistener itemClicklistener) {
        this.itemClicklistener = itemClicklistener;
    }

    public interface ItemClicklistener {
        void onItemClicklistener(int i, View view);
    }

    //endregion

}
