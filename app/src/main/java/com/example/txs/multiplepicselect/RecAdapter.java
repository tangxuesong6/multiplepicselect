package com.example.txs.multiplepicselect;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import com.facebook.drawee.view.SimpleDraweeView;
import java.util.ArrayList;
import java.util.List;

/**
 * @author txs
 * @date 2018/01/14
 */

public class RecAdapter extends RecyclerView.Adapter<RecAdapter.MyViewHolder> {
    private Context context;
    /**
     * 控制是否显示Checkbox
     */
    private boolean showCheckBox;
    /**
     * 屏幕宽度 我们要动态设置每个item大小为屏幕宽度的1/3
     */
    private int screenWidth;
    /**
     * 设置每个item 的params(大小)
     */
    private GridLayoutManager.LayoutParams params;
    /**
     * frasco 使用
     */
    private Uri uri;

    public RecAdapter(Context context, List<String> list, int screenWidth) {
        this.context = context;
        this.list = list;
        this.screenWidth = screenWidth;
        //frasco 使用
        uri = Uri.parse("res:///" + R.mipmap.imgv_fitness_girl);
    }
    public boolean isShowCheckBox() {
        return showCheckBox;
    }

    public void setShowCheckBox(boolean showCheckBox) {
        this.showCheckBox = showCheckBox;
    }

    /**
     * 这就是适配器要传过来的数据集合了
     */
    private List<String> list = new ArrayList<>();
    /**
     * 防止Checkbox错乱 做setTag  getTag操作
     */
    private SparseBooleanArray mCheckStates = new SparseBooleanArray();

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_pic, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        //防止复用导致的checkbox显示错乱
        holder.mCbItem.setTag(position);
        //设置item宽高为屏幕宽度的1/3
        params = (GridLayoutManager.LayoutParams) holder.mRlItem.getLayoutParams();
        params.width = screenWidth / 3;
        params.height = screenWidth / 3;
        //判断当前checkbox的状态
        if (showCheckBox) {
            holder.mCbItem.setVisibility(View.VISIBLE);
            //防止显示错乱
            holder.mCbItem.setChecked(mCheckStates.get(position, false));
        } else {
            holder.mCbItem.setVisibility(View.GONE);
            //取消掉Checkbox后不再保存当前选择的状态
            holder.mCbItem.setChecked(false);
            mCheckStates.clear();
        }
        //点击监听
        holder.mRlItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (showCheckBox) {
                    holder.mCbItem.setChecked(!holder.mCbItem.isChecked());
                }
                onItemClickListener.onClick(view, position);
            }
        });
        //长按监听
        holder.mRlItem.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return onItemClickListener.onLongClick(view, position);
            }
        });
        //对checkbox的监听 保存选择状态 防止checkbox显示错乱
        holder.mCbItem.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                int pos = (int) compoundButton.getTag();
                if (b) {
                    mCheckStates.put(pos, true);
                } else {
                    mCheckStates.delete(pos);
                }

            }
        });
        //frasco 使用
        holder.mImgvItem.setImageURI(uri);
//        Glide.with(context).load(R.mipmap.imgv_fitness_gril).into(holder.mImgvItem);
    }

    @Override
    public int getItemCount() {
        //暂时做60个模拟数据
        return 60;
    }

    /**
     * 自己写接口，实现点击和长按监听
     */
    public interface onItemClickListener {
        void onClick(View view, int pos);

        boolean onLongClick(View view, int pos);
    }

    private onItemClickListener onItemClickListener;

    public void setOnItemClickListener(RecAdapter.onItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout mRlItem;
        private SimpleDraweeView mImgvItem;
        private CheckBox mCbItem;

        public MyViewHolder(View itemView) {
            super(itemView);
            mRlItem = (RelativeLayout) itemView.findViewById(R.id.rl_item);
            mImgvItem = (SimpleDraweeView) itemView.findViewById(R.id.imgv_item);
            mCbItem = (CheckBox) itemView.findViewById(R.id.cb_item);
        }
    }
}
