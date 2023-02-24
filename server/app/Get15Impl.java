package server.app;

import javafx.application.Platform;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class Get15Impl extends UnicastRemoteObject implements Get15Interface {

    private CallBack player1 = null;

    private CallBack player2 = null;

    private ArrayList<Integer> firstPlayerNumbers = new ArrayList<Integer>(5);

    private ArrayList<Integer> secondPlayerNumbers = new ArrayList<Integer>(5);

    private String firstPlayerChosen = "";

    private String secondPlayerChosen = "";

    private ArrayList<Integer> allChosen = new ArrayList<Integer>(9);

    public Get15Impl() throws RemoteException
    {
        super();
    }

    public Get15Impl(int port) throws RemoteException
    {
        super(port);
    }

    public String connect(CallBack client) throws RemoteException
    {
        if(player1 == null)
        {
            player1 = client;
            player1.notify(String.format("Wait for the second player to join  "));
            return "First Player";
        }
        else if(player2 == null)
        {
            player2 = client;
            player2.notify(String.format("Wait for the first player to choose  "));
            player2.takeTurn(false);

            player1.notify("It is my turn  ");

            player1.takeTurn(true);
            return "Second Player";
        }
        else
        {
            client.notify("Both players are already in the game.  ");
            return " ";
        }
    }

    public void myChoice(int number, String player) throws RemoteException
    {
        if(player.equals("First Player"))
        {
            if(!allChosen.contains(number) && (number >=1 && number <=9))
            {
                player1.choose(number, secondPlayerChosen);
                player2.notify(String.format("First player choose number: %d  ", number));
                firstPlayerChosen+=String.valueOf(number) + ", ";
                firstPlayerNumbers.add(number);
                allChosen.add(number);
            }
        }
        else
        {
            if(!allChosen.contains(number) && (number >=1 && number <=9))
            {
                player2.choose(number, firstPlayerChosen);
                player1.notify(String.format("Second player choose number: %d  ", number));
                secondPlayerChosen+=String.valueOf(number) + ", ";
                secondPlayerNumbers.add(number);
                allChosen.add(number);
            }
        }

        if(isWonByFirst()) {
            player1.notify("I won!");
            player2.notify("I lost!");
            Platform.exit();
        }
        else if(isWonBySecond())
        {
            player2.notify("I won!");
            player1.notify("I lost!");
            Platform.exit();
        }
        else if(allChosen.size() == 9)
        {
            player1.notify("I didn't win!");
            player2.notify("I didn't win!");
            Platform.exit();
        }
    }

    public boolean isWonByFirst()
    {
        if(firstPlayerNumbers.size() < 3)
            return false;

        boolean flag = false;

        for(int i=0;i<firstPlayerNumbers.size();i++)
        {
            for(int j=i+1;j<firstPlayerNumbers.size();j++)
            {
                for(int k=j+1;k<firstPlayerNumbers.size();k++)
                {
                    if(firstPlayerNumbers.get(i) + firstPlayerNumbers.get(j)
                    + firstPlayerNumbers.get(k) == 15)
                        flag = true;
                }
            }
        }

        return flag;
    }

    public boolean isWonBySecond()
    {
        if(secondPlayerNumbers.size() < 3)
            return false;

        boolean flag = false;

        for(int i=0;i<secondPlayerNumbers.size();i++)
        {
            for(int j=i+1;j<secondPlayerNumbers.size();j++)
            {
                for(int k=j+1;k<secondPlayerNumbers.size();k++)
                {
                    if(secondPlayerNumbers.get(i) + secondPlayerNumbers.get(j)
                            + secondPlayerNumbers.get(k) == 15)
                        flag = true;
                }
            }
        }

        return flag;
    }

    public static void main(String[] args) {
        try
        {
            Get15Interface obj = new Get15Impl();
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("Get15Impl", obj);
            System.out.println("Server" + obj + "registered");
            System.out.println("Server waiting...");
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
