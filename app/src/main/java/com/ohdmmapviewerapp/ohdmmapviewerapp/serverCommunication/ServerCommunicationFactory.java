package com.ohdmmapviewerapp.ohdmmapviewerapp.serverCommunication;

import android.content.Context;

public class ServerCommunicationFactory {

    public static ServerCommunication produceServerCommunication(Context ctx) {
        return ServerCommunicationImpl.getServerCommunicationInstance(ctx);
    }

}
