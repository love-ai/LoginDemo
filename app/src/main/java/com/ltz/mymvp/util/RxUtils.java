package com.ltz.mymvp.util;

import com.ltz.mymvp.base.baseinterface.LoadingListener;
import com.ltz.mymvp.network.HttpResponse;
import com.ltz.mymvp.network.ResponseCode;
import com.ltz.mymvp.network.RxError;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.FragmentEvent;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by xiaowei on 2018/5/22
 */
public class RxUtils {

    private static ObservableTransformer TRANSFORMER;

    public static <T> ObservableTransformer<T, T> default_thread() {
        if (TRANSFORMER == null) {
            TRANSFORMER = new ObservableTransformer() {
                @Override
                public ObservableSource apply(Observable upstream) {
                    return upstream.subscribeOn(Schedulers.io())
                            .unsubscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread());
                }
            };
        }
        return TRANSFORMER;
    }

    public static <T> ObservableTransformer<HttpResponse<T>, T> rx
            (final LifecycleProvider<FragmentEvent> provider, LoadingListener loadingListener) {
        return rx(provider, FragmentEvent.DESTROY_VIEW, loadingListener);
    }


    public static <T> ObservableTransformer<HttpResponse<T>, T> rx
            (final LifecycleProvider<FragmentEvent> provider, final FragmentEvent fragmentEvent, final LoadingListener loadingListener) {
        return new ObservableTransformer<HttpResponse<T>, T>() {
            @Override
            public ObservableSource<T> apply(Observable<HttpResponse<T>> upstream) {
                return upstream
                        .compose(provider.<HttpResponse<T>>bindUntilEvent(fragmentEvent))
                        .compose(RxUtils.<HttpResponse<T>>default_thread())
                        .flatMap(new Function<HttpResponse<T>, ObservableSource<? extends T>>() {
                            @Override
                            public ObservableSource<? extends T> apply(HttpResponse<T> t) throws Exception {
                                if (t == null) {
                                    return Observable.error(new NullPointerException());
                                } else {
                                    if (t.getCode() == ResponseCode.SUCCESS) {
                                        if (t.getData() != null) {
                                            return Observable.just(t.getData());
                                        } else {
                                            return Observable.empty();
                                        }
                                    }
                                    return Observable.error(new RxError(t.getMessage(), t.getCode()));

                                }
                            }
                        })
                        .compose(new ObservableTransformer<T, T>() {
                            @Override
                            public ObservableSource<T> apply(Observable<T> upstream) {
                                return upstream
                                        .doOnSubscribe(new Consumer<Disposable>() {
                                            @Override
                                            public void accept(Disposable disposable) throws Exception {
                                                if (loadingListener != null) {
                                                    loadingListener.showLoading();
                                                }
                                            }
                                        })
                                        .doOnComplete(new Action() {
                                            @Override
                                            public void run() throws Exception {
                                                if (loadingListener != null) {
                                                    loadingListener.hideLoading();
                                                }
                                            }
                                        })
                                        .doOnError(new Consumer<Throwable>() {
                                            @Override
                                            public void accept(Throwable throwable) throws Exception {
                                                if (loadingListener != null) {
                                                    loadingListener.hideLoading();
                                                }
                                            }
                                        });
                            }
                        });
            }
        };
    }


}
