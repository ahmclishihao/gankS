package com.lsh.gank.ui.fragment.translate;

import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.lsh.gank.R;
import com.lsh.gank.bean.WordData;
import com.lsh.gank.ui.BasePresenter;
import com.lsh.gank.util.SnackBarUtil;
import com.lsh.gank.util.UIUtils;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 翻译界面
 */

public class TranslateFragment extends Fragment implements View.OnClickListener, TranslateContract.TranslateView {
    @BindView(R.id.et_source)
    EditText mEtSource;
    @BindView(R.id.tv_translate)
    TextView mTvTranslate;
    @BindView(R.id.btn_start)
    Button mBtnStart;
    @BindView(R.id.btn_clear)
    Button mBtnClear;
    @BindView(R.id.btn_copy)
    Button mBtnCopy;
    private TranslatePresenter mTranslatePresenter;
    private View mRootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = UIUtils.inflateView(getContext(), R.layout.layout_transalte_query, null);
            ButterKnife.bind(this, mRootView);
            if (mTranslatePresenter == null)
                mTranslatePresenter = new TranslatePresenter(this);
        }
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setListener();
    }

    private void setListener() {
        mBtnStart.setOnClickListener(this);
        mBtnClear.setOnClickListener(this);
        mBtnCopy.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_clear:
                mTranslatePresenter.clear();
                break;
            case R.id.btn_start:
                hindeSIM();
                String source = mEtSource.getText().toString();
                if (TextUtils.isEmpty(source)) {
                    SnackBarUtil.show(mTvTranslate, "请输入翻译内容");
                    return;
                }
                mTranslatePresenter.translate(source);
                break;
            case R.id.btn_copy:
                // 文字copy到剪切板
                mTranslatePresenter.copy(mTvTranslate.getText(), (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE));
                break;
        }
    }

    // 隐藏软键盘
    private void hindeSIM() {
        InputMethodManager simS = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        simS.hideSoftInputFromWindow(mEtSource.getWindowToken(), 0);
    }

    private void parseData(WordData wordData) {
        if (wordData.errorCode == 0) {
            mTvTranslate.setText("");
            mTvTranslate.setVisibility(View.VISIBLE);
            if (wordData.basic != null && wordData.basic.explains.size() > 0) {
                if (!TextUtils.isEmpty(wordData.basic.phonetic))
                    mTvTranslate.append("发音：[" + wordData.basic.phonetic + "]");

                for (String explain : wordData.basic.explains) {
                    mTvTranslate.append("\n" + explain);
                }
                mTvTranslate.append("\n");
            }

            if (!(wordData.basic.explains.size() > 0) && wordData.translation.size() > 0) {
                mTvTranslate.append(wordData.translation.get(0));
            }

            if (wordData.web != null && wordData.web.size() > 0) {

                WordData.WebBean bean = null;
                for (int i = 0, size = wordData.web.size(); i < size; i++) {
                    bean = wordData.web.get(i);
                    mTvTranslate.append("\n" + bean.key + "：");
                    for (String s : bean.value) {
                        mTvTranslate.append("\n\t" + s);
                    }
                }
            }
        } else {
            showTips("暂无结果");
        }
    }

    @Override
    public void setPresenter(BasePresenter presenter) {
        mTranslatePresenter = (TranslatePresenter) presenter;
    }

    @Override
    public void showTips(String message) {
        SnackBarUtil.show(mRootView, message);
    }

    @Override
    public void fillData(WordData wordData) {
        parseData(wordData);
    }

    @Override
    public void clear() {
        mEtSource.setText("");
    }
}
