package com.crisolutions.commonlib.view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.lang.ref.WeakReference;
import java.util.Stack;

import timber.log.Timber;

/**
 * This class finds all {@link EditText}s in a view hierarchy using DFS and sets ime option of the
 * {@link EditText}s to {@link EditorInfo#IME_ACTION_NEXT} except the last one. The last {@link EditText} is set to
 * {@link EditorInfo#IME_ACTION_DONE}. If implemented, {@link OnSubmitListener#onSubmit(View)} method will be called.
 */
public class FormAutoSubmitHandler implements ViewTreeObserver.OnGlobalLayoutListener {

    private WeakReference<View> viewRef;
    private OnSubmitListener listener;

    public void init(View view) {
        init(view, null);
    }

    public void init(View view, OnSubmitListener listener) {
        init(view, listener, false);
    }

    public void init(View view, OnSubmitListener listener, boolean listenForLayoutChanges) {
        if (view == null) {
            return;
        }

        this.viewRef = new WeakReference<>(view);
        this.listener = listener;

        if (listenForLayoutChanges) {
            view.getViewTreeObserver().addOnGlobalLayoutListener(this);
        }

        updateImeOptions(view, listener);
    }

    @Override
    public void onGlobalLayout() {
        Timber.d("OnGlobalLayout called. Refreshing ime options");
        updateImeOptions(viewRef.get(), listener);
    }

    private void updateImeOptions(View view, OnSubmitListener listener) {
        EditText last = findLastEditText(view);
        if (last != null) {
            setImeAction(last, EditorInfo.IME_ACTION_DONE);
            last.setOnEditorActionListener((v, actionId, event) -> {
                if (actionId == EditorInfo.IME_ACTION_DONE && listener != null) {
                    hideKeyboard(view.getContext(), view);
                    listener.onSubmit(v);
                    return true;
                }

                return false;
            });
        }
    }

    private EditText findLastEditText(View root) {
        EditText last = null;

        if (root == null) {
            return null;
        }

        Stack<View> stack = new Stack<>();
        stack.push(root);

        while (!stack.isEmpty()) {
            View v = stack.pop();

            if (isVisible(v)) {
                if (isEditText(v)) {
                    if (last == null) {
                        last = (EditText) v;
                    } else {
                        setImeAction((EditText) v, EditorInfo.IME_ACTION_NEXT);
                    }
                } else if (isViewGroup(v)) {
                    pushChildrenToStack(v, stack);
                }
            }
        }

        return last;
    }

    private void setImeAction(EditText v, int action) {
        v.setImeOptions(action);
    }

    private void pushChildrenToStack(View v, Stack<View> stack) {
        for (int i = 0; i < ((ViewGroup) v).getChildCount(); i++) {
            View child = ((ViewGroup) v).getChildAt(i);
            if (isViewGroup(child) || isEditText(child)) {
                stack.push(child);
            }
        }
    }

    private boolean isVisible(View v) {
        return View.VISIBLE == v.getVisibility();
    }

    private boolean isEditText(View v) {
        return v instanceof EditText;
    }

    private boolean isViewGroup(View v) {
        return v instanceof ViewGroup;
    }

    private void hideKeyboard(Context context, View view) {
        final InputMethodManager inputMethodManager =
                (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);

        if (inputMethodManager != null && view != null) {
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void clear() {
        if (viewRef == null) {
            return;
        }

        View v = viewRef.get();

        if (v != null) {
            v.getViewTreeObserver().removeOnGlobalLayoutListener(this);
        }

        listener = null;
    }

    public interface OnSubmitListener {
        void onSubmit(View view);
    }
}