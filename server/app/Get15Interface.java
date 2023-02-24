package server.app;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Get15Interface extends Remote {
    public String connect(CallBack client) throws RemoteException;

    public void myChoice(int number, String player) throws RemoteException;
}
