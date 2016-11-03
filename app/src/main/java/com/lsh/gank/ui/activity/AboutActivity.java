package com.lsh.gank.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;

import com.lsh.gank.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 个人详情页，富文本
 */

public class AboutActivity extends Activity {

    @BindView(R.id.tv_author)
    TextView mTvAuthor;
    @BindView(R.id.tv_myblog)
    TextView mTvMyblog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(MainActivity.getThemeId());
        setContentView(R.layout.layout_my_info_activity);
        ButterKnife.bind(this);
        setAuthorRich();
        setBlogRich();
    }

    /**
     * 设置官网的点击
     */
    private void setAuthorRich() {
        SpannableStringBuilder ssb = new SpannableStringBuilder();

        ssb.append("官网：");

        setClickRich(mTvAuthor, ssb, getResources().getString(R.string.gankio_url));
    }

    /**
     * 设置blog的点击
     */
    private void setBlogRich() {
        SpannableStringBuilder ssb = new SpannableStringBuilder();

        ssb.append("我的博客：");
        setClickRich(mTvMyblog, ssb, getResources().getString(R.string.blog_url));

    }

    private void setClickRich(TextView tv, SpannableStringBuilder ssb, final String url) {
        ClickSpan clickSpan = new ClickSpan(new ClickSpan.OnClickListener() {
            @Override
            public void onClick(View widget) {
                Intent intent = new Intent(AboutActivity.this, ArticleWebView.class);
                intent.putExtra("url", url);
                startActivity(intent);
            }
        });

        int start = ssb.length();

        ssb.append(url);
        ssb.setSpan(clickSpan, start, ssb.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        tv.setText(ssb);
        tv.setMovementMethod(new LinkMovementMethod());
    }

    /**
     * 可点击的富文本
     */
    public static class ClickSpan extends ClickableSpan {
        OnClickListener cl;

        public ClickSpan(OnClickListener cl) {
            this.cl = cl;
        }

        @Override
        public void onClick(View widget) {
            if (cl != null) {
                cl.onClick(widget);
            }
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
        }

        public interface OnClickListener {
            void onClick(View widget);
        }

    }

}
