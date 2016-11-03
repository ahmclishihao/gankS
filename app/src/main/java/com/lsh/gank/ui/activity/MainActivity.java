package com.lsh.gank.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TabHost;

import com.lsh.gank.R;
import com.lsh.gank.enums.KeyValue;
import com.lsh.gank.enums.TabMenus;
import com.lsh.gank.global.App;
import com.lsh.gank.util.SPUtil;
import com.lsh.gank.util.SnackBarUtil;
import com.lsh.gank.util.UIUtils;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    // 主题的id
    public static int themeId = R.style.AppTheme;
    public static String extra_change_theme = "change_theme";
    public int tabIndex = 0;
    FragmentTabHost mTabhost;

    // 保存更新通知的小圆点
    private List<ImageView> newsNotifyViews = new ArrayList<>();
    private View mRootView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initTheme();
        initView();
        initTabHost();
    }

    private void initView() {
        mRootView = View.inflate(this, R.layout.layout_main_content, null);
        setContentView(mRootView);
    }

    private void initTheme() {
        //选择主题
        Intent intent = getIntent();
        tabIndex = intent.getIntExtra(extra_change_theme, 0);
        setTheme(getThemeId());
    }

    /**
     * 暴露出给外界获取themeId
     *
     * @return
     */
    public static int getThemeId() {
        String string = SPUtil.getString(App.sContext, "theme", "blue");
        switch (string) {
            case "red":
                themeId = R.style.AppTheme_red;
                break;
            case "blue":
                themeId = R.style.AppTheme;
                break;
            case "green":
                themeId = R.style.AppTheme_green;
                break;
            case "purple":
                themeId = R.style.AppTheme_purple;
                break;
            case "black":
                themeId = R.style.AppTheme_black;
                break;
        }
        return themeId;
    }

    private void initTabHost() {
        mTabhost = (FragmentTabHost) findViewById(android.R.id.tabhost);

        mTabhost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);
        // 去掉间隔
        mTabhost.getTabWidget().setDividerDrawable(android.R.color.transparent);

        TabMenus[] tabMenuses = TabMenus.values();

        for (int i = 0; i < tabMenuses.length; i++) {
            TabMenus tabMenuse = tabMenuses[i];

            TabHost.TabSpec tabSpec = mTabhost.newTabSpec(tabMenuse.getTag());

            tabSpec.setIndicator(getIndicator(tabMenuse));

            // 传入需要的参数
            KeyValue[] arguments = tabMenuse.getArguments();

            Bundle bundle = null;

            if (arguments != null && arguments.length > 0) {
                bundle = new Bundle();
                for (KeyValue argument : arguments) {
                    bundle.putString(argument.key, argument.value);
                }
            }
            mTabhost.addTab(tabSpec, tabMenuse.getClazz(), bundle);
        }
        mTabhost.setCurrentTab(tabIndex);
    }


    /**
     * 获取指示器
     *
     * @param tabMenus
     * @return
     */
    private View getIndicator(TabMenus tabMenus) {
        RelativeLayout indicatorView = (RelativeLayout) UIUtils.inflateView(this, R.layout.layout_bottom_indicator, null);

        indicatorView.setGravity(Gravity.CENTER);

        ImageView icon = (ImageView) indicatorView.findViewById(R.id.iv_icon);
        ImageView notify = (ImageView) indicatorView.findViewById(R.id.iv_new_notify);
        notify.setVisibility(ImageView.INVISIBLE);
        newsNotifyViews.add(notify);
        icon.setImageResource(tabMenus.getIconId());
        return indicatorView;
    }

    public void changeTheme(int id) {
        themeId = id;
//        recreate();
        overridePendingTransition(R.animator.activity_enter, R.animator.activity_out);
        finish();
        // intent中标记着是通过更换主题的方式进入的，并告知更换的主题后的选择页
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(extra_change_theme, 3);
        overridePendingTransition(R.animator.activity_enter, R.animator.activity_out);
        startActivity(intent);
    }


    long oldTime = 0;

    public void onBackPressed() {
        long newTime = System.currentTimeMillis();

        if (newTime - oldTime < 1000) {
            super.onBackPressed();
        } else {
            SnackBarUtil.show(mRootView, "再按一次退出");
            oldTime = newTime;
        }

    }
}
