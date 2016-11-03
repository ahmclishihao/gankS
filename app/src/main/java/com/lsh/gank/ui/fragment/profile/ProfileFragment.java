package com.lsh.gank.ui.fragment.profile;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.lsh.gank.R;
import com.lsh.gank.ui.activity.AboutActivity;
import com.lsh.gank.ui.activity.MainActivity;
import com.lsh.gank.ui.activity.favorite.FavoriteActivity;
import com.lsh.gank.util.SPUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * "更多选项"的fragment
 */

public class ProfileFragment extends Fragment {

    @BindView(R.id.rg_theme)
    RadioGroup mRgTheme;
    @BindView(R.id.tv_version_name)
    TextView mTvVersionName;
    @BindView(R.id.cd_about)
    CardView mCdAbout;
    @BindView(R.id.cd_favorite)
    CardView mCdFavorite;

    private View mRootView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = inflater.inflate(R.layout.layout_main_content_profile, null);
            ButterKnife.bind(this, mRootView);
            initMenu();
        }
        return mRootView;
    }

    private void initMenu() {
        initTheme();
        initAbout();
        initFavoriate();
    }

    private void initFavoriate() {
        mCdFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), FavoriteActivity.class));
            }
        });
    }

    private void initAbout() {
        try {
            PackageManager pM = getActivity().getPackageManager();
            PackageInfo packageInfo = pM.getPackageInfo(getActivity().getPackageName(), PackageManager.GET_CONFIGURATIONS);
            String versionName = packageInfo.versionName;
            mTvVersionName.setText("当前版本：" + versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        mCdAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AboutActivity.class);
                getActivity().startActivity(intent);
            }
        });
    }

    private void initTheme() {
        mRgTheme.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int id = MainActivity.themeId;
                switch (checkedId) {
                    case R.id.rb_green:
                        id = R.style.AppTheme_green;
                        SPUtil.setString(getContext(), "theme", "green");
                        break;
                    case R.id.rb_red:
                        id = R.style.AppTheme_red;
                        SPUtil.setString(getContext(), "theme", "red");
                        break;
                    case R.id.rb_blue:
                        id = R.style.AppTheme;
                        SPUtil.setString(getContext(), "theme", "blue");
                        break;
                    case R.id.rb_purple:
                        id = R.style.AppTheme_purple;
                        SPUtil.setString(getContext(), "theme", "purple");
                        break;
                    case R.id.rb_black:
                        id = R.style.AppTheme_black;
                        SPUtil.setString(getContext(), "theme", "black");
                        break;
                }
                if (id != MainActivity.themeId) {
                    MainActivity activity = (MainActivity) getActivity();
                    activity.changeTheme(id);
                }
            }
        });
    }

}
