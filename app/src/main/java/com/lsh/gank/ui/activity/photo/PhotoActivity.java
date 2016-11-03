package com.lsh.gank.ui.activity.photo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lsh.gank.R;
import com.lsh.gank.ui.BasePresenter;
import com.lsh.gank.util.SnackBarUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;


public class PhotoActivity extends Activity implements PhotoContract.PhotoView {

    @BindView(R.id.pv_photo)
    PhotoView mPvPhoto;
    @BindView(R.id.tv_download)
    TextView mTvDownload;
    @BindView(R.id.tv_back)
    TextView mTvBack;
    @BindView(R.id.rl_bottom_layout)
    RelativeLayout mRlBottomLayout;
    private PhotoPresenter mPhotoPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        initData();
        initListener();
    }

    private void initListener() {
        // 退出事件
        mTvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        // 保存事件
        mTvDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPhotoPresenter.savePhoto(mPvPhoto);
            }
        });
        // 给mRlBottomLayout打上标记标识开关
        mRlBottomLayout.setTag(true);
        mPvPhoto.setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener() {
            @Override
            public void onViewTap(View view, float x, float y) {
                // 控制动画渐渐检出的动画，这里没有做动画中的判断，因为photoView会收集用户快速点击的事件
                boolean open = (boolean) mRlBottomLayout.getTag();
                if (open) {
                    mRlBottomLayout.animate().translationY(300f).start();
                    mRlBottomLayout.setTag(false);
                } else {
                    mRlBottomLayout.animate().translationY(0).start();
                }
                mRlBottomLayout.setTag(!open);
            }
        });
    }

    private void initData() {
        mPhotoPresenter = new PhotoPresenter(this);

        mPhotoPresenter.setPhoto(getIntent(), mPvPhoto);

    }

    private void initView() {
        setContentView(R.layout.layout_photo_activity);
        ButterKnife.bind(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    public void showPhoto() {
        // empty
    }

    @Override
    public void setPresenter(BasePresenter presenter) {
        mPhotoPresenter = (PhotoPresenter) presenter;
    }

    @Override
    public void showTips(String message) {
        SnackBarUtil.show(mPvPhoto, message);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPhotoPresenter.destroy();
    }
}
