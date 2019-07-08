package com.henry.apptools.adapter;

import android.content.Context;
import android.content.pm.ResolveInfo;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.henry.apptools.R;

import java.util.List;

/**
 * Author:henry
 * Time:2019-06-04 15:28:28
 * ClassName:This is LockPackageAdapter
 * Remark:不要冲动,杀人犯法
 * Description:description
 */
public class LockPackageAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<ResolveInfo> resolveInfoList;

    public LockPackageAdapter(Context context, List<ResolveInfo> resolveInfoList) {
        this.context = context;
        this.resolveInfoList = resolveInfoList;
    }

    public List<ResolveInfo> getResolveInfoList() {
        return resolveInfoList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new LockPackageViewHolder(LayoutInflater.from(context).inflate(R.layout.adapter_info, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        LockPackageViewHolder lockPackageViewHolder = (LockPackageViewHolder) viewHolder;
        //APP名称
        lockPackageViewHolder.getItem_name_tv().setText(resolveInfoList.get(i).activityInfo.loadLabel(context.getPackageManager()));
        //APP包名
        lockPackageViewHolder.getItem_package_name_tv().setText(resolveInfoList.get(i).activityInfo.packageName);
        //APP图标
        lockPackageViewHolder.getItem_img().setImageDrawable(resolveInfoList.get(i).activityInfo.loadIcon(context.getPackageManager()));

        lockPackageViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(i, resolveInfoList);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return resolveInfoList == null ? 0 : resolveInfoList.size();
    }

    public OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int pos, List<ResolveInfo> resolveInfoList);
    }
}
