package com.ohdmmapviewerapp.ohdmmapviewerapp.serverCommunication;

import static org.mockito.Mockito.mock;

import android.content.Context;
import android.content.Intent;

import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Test;

public class HttpServiceTest {

    @Test
    public void sendFileRequest() throws InterruptedException {
        Context ctx = mock(Context.class);
        Intent intent = new Intent(ctx, HttpService.class );
        intent.putExtra("fileName", "123456");
        intent.putExtra("date", "2012-01-20");
        intent.putExtra("wkt", "POYLGON");
        intent.putExtra("token", "348234f2nf329f");
        ctx.startService(intent);
        Thread.sleep(5000);
    }
}