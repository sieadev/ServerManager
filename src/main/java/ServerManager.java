import gui.GUI;
import manager.Manager;

public class ServerManager {
    private static ServerManager serverManager;
    public ServerManager(){
        new Manager();
        new GUI();
    }
    public static void main(String[] args){
        serverManager = new ServerManager();
    }
}
