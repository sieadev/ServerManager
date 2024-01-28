package manager;

import server.DiscordBot;
import server.MinecraftServer;
import server.MinecraftServerType;

import java.util.ArrayList;
import java.util.List;

public class Manager {
    private static List<MinecraftServer> minecraftServer;
    private static List<DiscordBot> discordBots;
    private static Manager manager;

    public Manager(){
        manager = this;
        init();
    }

    private void init() {
        minecraftServer = getMinecraftServerFromConfig();
        discordBots = getDiscordBotFromConfig();
    }

    private static List<MinecraftServer> getMinecraftServerFromConfig(){
        MinecraftServer minecraftServer1 = new MinecraftServer(MinecraftServerType.BUNGEECORD, "Vitacraft");
        List<MinecraftServer> minecraftServers = new ArrayList<>();
        minecraftServers.add(minecraftServer1);
        return minecraftServers;
    }

    private static List<DiscordBot> getDiscordBotFromConfig(){
        DiscordBot DiscordBot = new DiscordBot("Barista");
        List<DiscordBot> discordBots = new ArrayList<>();
        discordBots.add(DiscordBot);
        return discordBots;
    }

    public static List<MinecraftServer> getMinecraftServers(){
        return minecraftServer;
    }

    public static List<DiscordBot> getDiscordBots(){
        return discordBots;
    }

    public static Manager getInstance(){
        return manager;
    }
}
