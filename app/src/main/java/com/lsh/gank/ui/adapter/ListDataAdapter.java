package com.lsh.gank.ui.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lsh.gank.R;
import com.lsh.gank.bean.ListData;
import com.lsh.gank.ui.activity.ArticleWebView;
import com.lsh.gank.ui.activity.photo.PhotoActivity;
import com.lsh.gank.util.GlideUtil;
import com.lsh.gank.util.UIUtils;

import java.util.List;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * 列表数据的adapter
 */
public class ListDataAdapter extends RecyclerView.Adapter {
    private List<ListData.ListBean> data = null;
    private Activity mActivity;

    private OnItemLongClickListener onItemLongClickListener;

    public ListDataAdapter(Activity activity, List<ListData.ListBean> data) {
        this.mActivity = activity;
        this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new InnerHolder(UIUtils.inflateView(parent.getContext(), R.layout.recycle_item_home, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof InnerHolder) {
            InnerHolder innerHolder = (InnerHolder) holder;
            final ListData.ListBean listBean = data.get(position);
            // 填充数据
            if (listBean.type.equals("福利")) {
                innerHolder.mImageView.setVisibility(View.VISIBLE);
                GlideUtil.loadStaticPic(innerHolder.mImageView, listBean.url);
            } else if (listBean.images == null || listBean.images.size() == 0) {
                innerHolder.mImageView.setVisibility(View.GONE);
            } else {
                innerHolder.mImageView.setVisibility(View.VISIBLE);
                GlideUtil.loadStaticPic(innerHolder.mImageView, listBean.images.get(0));
            }
            innerHolder.mTvTitle.setText(listBean.desc);
            innerHolder.mTvTime.setText(listBean.publishedAt.substring(0, 10));
            innerHolder.mTvAuther.setText(listBean.who);
            // 设置角标颜色
            int typeId = 0;
            switch (listBean.type) {
                case "福利":
                    typeId = R.color.yellow;
                    break;
                case "Android":
                    typeId = R.color.forestgreen;
                    break;
                case "iOS":
                    typeId = R.color.orangered;
                    break;
                case "休息视频":
                    typeId = R.color.purple;
                    break;
                case "拓展资源":
                    typeId = R.color.darkred;
                    break;
                case "前端":
                    typeId = R.color.mistyrose;
                    break;
                default:
                    typeId = R.color.colorPrimary;
                    break;
            }
            innerHolder.mDataType.setBackgroundResource(typeId);
            setListener(innerHolder, listBean, position);
        }
    }

    private void setListener(final InnerHolder holder, final ListData.ListBean listBean, final int position) {
        // 列表图片的点击事件
        holder.mImageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showImagePhoto((ImageView) v);
            }
        });
        // 列表图片的长按事件
        holder.mImageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (onItemLongClickListener != null) {
                    // 截断事件，防止滑动
                    v.getParent().requestDisallowInterceptTouchEvent(true);
                    onItemLongClickListener.onItemLongClick(v, listBean, position);
                }
                return true;
            }
        });
        // 列表项目的点击事件
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent;
                if (listBean.type.equals("福利")) {
                    intent = new Intent(mActivity, PhotoActivity.class);
                } else {
                    intent = new Intent(mActivity, ArticleWebView.class);
                }
                intent.putExtra("url", listBean.url);
                mActivity.startActivity(intent);
            }
        });
        // 列表项目长按点击事件
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View v) {
                if (onItemLongClickListener != null) {
                    // 截断事件，防止滑动
                    LinearLayout lv = (LinearLayout) v;
                    lv.requestDisallowInterceptTouchEvent(true);
                    onItemLongClickListener.onItemLongClick(v, listBean, position);
                }
                return true;
            }
        });
    }

    private PhotoView mPhotoView;

    // 显示photoView
    public void showImagePhoto(ImageView view) {
        Drawable drawable = view.getDrawable();
        if (mPhotoView == null) {
            mPhotoView = new PhotoView(view.getContext());
            mPhotoView.setZoomable(true);
            mPhotoView.setBackgroundColor(Color.BLACK);
            mPhotoView.setImageDrawable(drawable);

            mPhotoView.setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener() {
                @Override
                public void onViewTap(View view, float x, float y) {
                    mPhotoView.setVisibility(View.GONE);
                }
            });
            mPhotoView.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        mPhotoView.setVisibility(View.GONE);
                    }
                    return true;
                }
            });
            mActivity.getWindowManager().addView(mPhotoView, new WindowManager.LayoutParams());
        }

        if (mPhotoView.getVisibility() == View.GONE) {
            mPhotoView.setImageDrawable(drawable);
            mPhotoView.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 提供给调用者设置监听
     */
    public void setOnItemLongClickListener(OnItemLongClickListener icl) {
        this.onItemLongClickListener = icl;
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(View v, ListData.ListBean listBean, int position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class InnerHolder extends RecyclerView.ViewHolder {
        ImageView mImageView = null;
        TextView mTvTitle = null;
        TextView mTvAuther = null;
        TextView mTvTime = null;
        View rootView = null;
        View mDataType = null;

        public InnerHolder(View itemView) {
            super(itemView);
            rootView = itemView;
            mImageView = (ImageView) itemView.findViewById(R.id.iv_picture);
            mTvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            mTvAuther = (TextView) itemView.findViewById(R.id.tv_author);
            mTvTime = (TextView) itemView.findViewById(R.id.tv_time);
            mDataType = itemView.findViewById(R.id.v_data_type);
        }
    }

}
