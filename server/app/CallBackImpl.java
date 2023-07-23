package server.app;

import client.app.Get15ClientSceneControler;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class CallBackImpl extends UnicastRemoteObject implements CallBack {

    private Get15ClientSceneControler thisClient;

    @Override
    public boolean getTurnStatus() throws RemoteException
    {
        return thisClient.isMyTurn();
    }

    public CallBackImpl(Object client) throws RemoteException {
        thisClient = (Get15ClientSceneControler)client;
    }
    @Override
    public void takeTurn(boolean turn) throws RemoteException {
        thisClient.setMyTurn(turn);
    }

    @Override
    public void notify(String message) throws RemoteException {
        thisClient.setMessage(message);
    }

    @Override
    public void choose(int number, String otherChosen) throws RemoteException {
        thisClient.choose(number, otherChosen);
    }
}
