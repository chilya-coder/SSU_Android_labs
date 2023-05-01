package it_school.sumdu.edu.asynctaskandasynctaskloader;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.loader.content.AsyncTaskLoader;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


public class SimpleAsyncTaskLoader extends AsyncTaskLoader<String> {

    private final String mQueryString;
    private final String mTransferProtocol;
    @SuppressLint("StaticFieldLeak")
    Context mContext;

    public SimpleAsyncTaskLoader(@NonNull Context context, String queryString, String transferProtocol) {
        super(context);
        mContext = context;
        mQueryString = queryString;
        mTransferProtocol = transferProtocol;
    }

    @Nullable
    @Override
    public String loadInBackground() {
        return Link.getSourceCode(mContext, mQueryString, mTransferProtocol);
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }
}