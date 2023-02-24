package server.app;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface CallBack extends Remote {

    public void takeTurn(boolean turn) throws RemoteException;

    public void notify(String message) throws RemoteException;

    public void choose(int number, String otherChosen) throws RemoteException;
}
