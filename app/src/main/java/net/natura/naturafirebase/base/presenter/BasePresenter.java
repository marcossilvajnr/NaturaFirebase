package net.natura.naturafirebase.base.presenter;

/**
 * Created by marcos on 03/06/17.
 */

public abstract class BasePresenter<V> {
    protected V view;
    public void attachView(V view) {
        this.view = view;
    }
    public void detachView() {
        this.view = null;
    }
}