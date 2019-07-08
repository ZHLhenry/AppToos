package com.henry.apptools.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.henry.apptools.R;

/**
 * Author:henry
 * Time:2019-06-04 15:28:28
 * ClassName:This is LockPackageViewHolder
 * Remark:不要冲动,杀人犯法
 * Description:description
 */
public class LockPackageViewHolder extends RecyclerView.ViewHolder {
    private ImageView item_img;
    private TextView item_name_tv, item_package_name_tv;

    public LockPackageViewHolder(@NonNull View itemView) {
        super(itemView);
        item_img = itemView.findViewById(R.id.item_img);
        item_name_tv = itemView.findViewById(R.id.item_name_tv);
        item_package_name_tv = itemView.findViewById(R.id.item_package_name_tv);
    }

    public ImageView getItem_img() {
        return item_img;
    }

    public TextView getItem_name_tv() {
        return item_name_tv;
    }

    public TextView getItem_package_name_tv() {
        return item_package_name_tv;
    }
}
