/*
 * Copyright 2013 Philip Schiffer
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package main.java.de.psdev.licensesdialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;

import de.psdev.licensesdialog.R;
import main.java.de.psdev.licensesdialog.model.Notice;

public class SingleLicenseDialogFragment extends DialogFragment {

    private static final String ARGUMENT_NOTICE = "ARGUMENT_NOTICE";
    private static final String STATE_LICENSE_TEXT = "license_text";
    private static final String STATE_TITLE_TEXT = "title_text";
    private static final String STATE_CLOSE_TEXT = "close_text";

    //
    private String mTitleText;
    private String mCloseButtonText;
    private String mLicenseText;

    //
    private boolean mShowFullLicenseText;
    private DialogInterface.OnDismissListener mOnDismissListener;

    public SingleLicenseDialogFragment() {
    }

    public static SingleLicenseDialogFragment newInstance(final Notice notice) {
        final SingleLicenseDialogFragment fragment = new SingleLicenseDialogFragment();
        final Bundle args = new Bundle();
        args.putSerializable(ARGUMENT_NOTICE, notice);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Resources resources = getResources();

        if (savedInstanceState != null) {
            mTitleText = savedInstanceState.getString(STATE_TITLE_TEXT);
            mLicenseText = savedInstanceState.getString(STATE_LICENSE_TEXT);
            mCloseButtonText = savedInstanceState.getString(STATE_CLOSE_TEXT);
        } else {
            mTitleText = resources.getString(R.string.notices_title);
            mCloseButtonText = resources.getString(R.string.notices_close);

            try {
                final Notice notice = getNotice();
                mLicenseText = NoticesHtmlBuilder.create(getActivity()).setNotice(notice).build();
            } catch (final Exception e) {
                throw new IllegalStateException(e);
            }
        }
    }

    @Override
    public void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(STATE_TITLE_TEXT, mTitleText);
        outState.putString(STATE_LICENSE_TEXT, mLicenseText);
        outState.putString(STATE_CLOSE_TEXT, mCloseButtonText);
    }

    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        return new SingleLicenseDialog(getActivity(), mTitleText, mLicenseText, mCloseButtonText).create();
    }

    @Override
    public void onDismiss(final DialogInterface dialog) {
        super.onDismiss(dialog);
        if (mOnDismissListener != null) {
            mOnDismissListener.onDismiss(dialog);
        }
    }

    //

    public DialogInterface.OnDismissListener getOnDismissListener() {
        return mOnDismissListener;
    }

    public void setOnDismissListener(final DialogInterface.OnDismissListener onDismissListener) {
        mOnDismissListener = onDismissListener;
    }

    public boolean isShowFullLicenseText() {
        return mShowFullLicenseText;
    }

    public void setShowFullLicenseText(final boolean showFullLicenseText) {
        mShowFullLicenseText = showFullLicenseText;
    }

    //

    private Notice getNotice() {
        final Bundle arguments = getArguments();
        if (arguments != null && arguments.containsKey(ARGUMENT_NOTICE)) {
            return (Notice) arguments.getSerializable(ARGUMENT_NOTICE);
        }

        throw new IllegalStateException("no notice provided");
    }
}
