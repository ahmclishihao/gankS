package com.lsh.gank.ui.fragment.translate;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.text.TextUtils;

import com.lsh.gank.bean.WordData;
import com.lsh.gank.net.TranslateHelper;

import rx.Observable;
import rx.Subscriber;

public class TranslatePresenter implements TranslateContract.Presenter {

    private final TranslateContract.TranslateView translateView;

    public TranslatePresenter(TranslateContract.TranslateView translateView) {
        this.translateView = translateView;
    }

    @Override
    public void translate(String source) {
        Observable<WordData> translateApi = TranslateHelper.getTranslateApi(source);
        translateApi.subscribe(new Subscriber<WordData>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                // 因为bean的原因，可能抛出异常到此步，导致结果无法返回。解决：将bean中可能出现的nullpoint的地方进行实例化
                translateView.showTips("暂无结果");
            }

            @Override
            public void onNext(WordData wordData) {
                translateView.fillData(wordData);
            }
        });
    }

    @Override
    public void clear() {
        translateView.clear();
    }

    @Override
    public void copy(CharSequence str, ClipboardManager cm) {
        if (!TextUtils.isEmpty(str)) {
            cm.setPrimaryClip(ClipData.newPlainText("translate", str));
            translateView.showTips("已经复制到剪切板");
        } else
            translateView.showTips("还没有翻译内容");


    }
}
