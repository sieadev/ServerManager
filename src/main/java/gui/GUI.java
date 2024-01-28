package gui;

import com.formdev.flatlaf.FlatDarkLaf;
import manager.Manager;
import server.DiscordBot;
import server.MinecraftServer;
import server.Status;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.List;

public class GUI {
    public static GUI gui;
    private final JFrame frame;
    private final JTabbedPane tabbedPane;
    private final JPanel homePanel;
    private final JPanel createPanel;
    private final JPanel settingsPanel;
    private final JPanel aboutPanel;

    public GUI() {
        gui = this;

        try {
            UIManager.setLookAndFeel(new FlatDarkLaf()); // You can choose other themes as well
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        frame = new JFrame("Server Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setMinimumSize(new Dimension(447, 400));

        tabbedPane = new JTabbedPane();

        homePanel = createHomePanel();
        createPanel = new JPanel();  // Implement creation of servers/bots here
        settingsPanel = new JPanel();  // Implement settings here
        aboutPanel = new JPanel();  // Implement about information here

        tabbedPane.addTab("Home", homePanel);
        tabbedPane.addTab("Create", createPanel);
        tabbedPane.addTab("Settings", settingsPanel);
        tabbedPane.addTab("About", aboutPanel);

        frame.getContentPane().add(tabbedPane);
        frame.setVisible(true);
    }

    private JPanel createHomePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));  // Adjust layout manager

        List<MinecraftServer> minecraftServers = getMinecraftServers();
        List<DiscordBot> discordBots = getDiscordBots();

        for (MinecraftServer server : minecraftServers) {
            panel.add(createServerSquare(server));
        }

        for (DiscordBot bot : discordBots) {
            panel.add(createBotSquare(bot));
        }

        return panel;
    }

    private JButton createServerSquare(MinecraftServer server) {
        JButton button = new JButton();
        button.setBorder(BorderFactory.createLineBorder(getStatusColor(server.getStatus())));
        button.setLayout(new BorderLayout()); // Use BorderLayout

        JPanel contentPanel = new JPanel(new GridLayout(4, 1));
        JLabel nameLabel = new JLabel(server.getName());
        JLabel iconLabel = new JLabel();
        URL iconPath = getClass().getClassLoader().getResource("icons/minecraft.png");
        if (iconPath != null){
            ImageIcon icon = new ImageIcon(iconPath);
            java.awt.Image resizedImage = icon.getImage().getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH);
            ImageIcon resizedIcon = new ImageIcon(resizedImage);
            iconLabel.setIcon(resizedIcon);
        } else{
            System.out.println("Null!");
        }
        JLabel type = new JLabel(server.getType().toString().replace("_", ""));
        JLabel filler = new JLabel("");
        JLabel statusLabel = new JLabel("Status: " + server.getStatus());

        // Set margin on the left
        nameLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        type.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        statusLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));

        contentPanel.add(nameLabel);
        contentPanel.add(type);
        contentPanel.add(filler);
        contentPanel.add(statusLabel);

        button.add(contentPanel, BorderLayout.CENTER);
        button.add(iconLabel, BorderLayout.LINE_START);

        // Set preferred size
        button.setPreferredSize(new Dimension(200, 100));

        // Disable automatic resizing
        button.setMaximumSize(new Dimension(200, 100));
        button.setMinimumSize(new Dimension(200, 100));

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle button click, for example, open a small window
                showDetailsWindow(server);
            }
        });

        return button;
    }

    private JButton createBotSquare(DiscordBot bot) {
        JButton button = new JButton();
        button.setBorder(BorderFactory.createLineBorder(getStatusColor(bot.getStatus())));
        button.setLayout(new BorderLayout()); // Use BorderLayout

        JPanel contentPanel = new JPanel(new GridLayout(4, 1));
        JLabel nameLabel = new JLabel(bot.getName());
        JLabel iconLabel = new JLabel();
        URL iconPath = getClass().getClassLoader().getResource("icons/discord.png");
        if (iconPath != null){
            ImageIcon icon = new ImageIcon(iconPath);
            java.awt.Image resizedImage = icon.getImage().getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH);
            ImageIcon resizedIcon = new ImageIcon(resizedImage);
            iconLabel.setIcon(resizedIcon);
        } else{
            System.out.println("Null!");
        }



        JLabel filler = new JLabel("");
        JLabel filler2 = new JLabel("");
        JLabel statusLabel = new JLabel("Status: " + bot.getStatus());

        // Set margin on the left
        nameLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        statusLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));

        contentPanel.add(nameLabel);
        contentPanel.add(filler);
        contentPanel.add(filler2);
        contentPanel.add(statusLabel);

        button.add(contentPanel, BorderLayout.CENTER);
        button.add(iconLabel, BorderLayout.LINE_START);

        // Set preferred size
        button.setPreferredSize(new Dimension(200, 100));

        // Disable automatic resizing
        button.setMaximumSize(new Dimension(200, 100));
        button.setMinimumSize(new Dimension(200, 100));

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle button click, for example, open a small window
                showDetailsWindow(bot);
            }
        });

        return button;
    }

    private Color getStatusColor(Status status){
        return switch (status) {
            case ONLINE -> (Color.GREEN);
            case OFFLINE, UNAVAILABLE -> (Color.RED);
            case STARTING, STOPPING -> (Color.ORANGE);
            default -> (Color.GRAY);
        };
    }


    private static List<MinecraftServer> getMinecraftServers() {
        return Manager.getMinecraftServers();
    }

    private static List<DiscordBot> getDiscordBots() {
        return Manager.getDiscordBots();
    }

    private void showDetailsWindow(Object data) {
        if (data instanceof MinecraftServer) {
            showMinecraftServerDetailsWindow((MinecraftServer) data);
        } else if (data instanceof DiscordBot) {
            showDiscordBotDetailsWindow((DiscordBot) data);
        }
    }

    private void showMinecraftServerDetailsWindow(MinecraftServer server) {
        JDialog dialog = new JDialog(frame, "Server Details: " + server.getName(), true);
        dialog.setSize(500, 600);
        dialog.setLayout(new BorderLayout());
        dialog.setLocationRelativeTo(frame);

        // Title at the top
        JLabel titleLabel = new JLabel(server.getName(), SwingConstants.CENTER);
        titleLabel.setFont(new Font(titleLabel.getFont().getName(), Font.BOLD, 16));
        dialog.add(titleLabel, BorderLayout.NORTH);

        // Buttons for start, stop, and restart
        JButton startButton = new JButton("Start");
        JButton stopButton = new JButton("Stop");
        JButton restartButton = new JButton("Restart");

        startButton.addActionListener(e -> {
            // Handle start button click
            if (server.getStatus() == Status.OFFLINE) {
            }
        });

        stopButton.addActionListener(e -> {
            // Handle stop button click
            if (server.getStatus() == Status.ONLINE) {
            }
        });

        restartButton.addActionListener(e -> {
            // Handle restart button click
            if (server.getStatus() == Status.ONLINE) {
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(startButton);
        buttonPanel.add(stopButton);
        buttonPanel.add(restartButton);
        dialog.add(buttonPanel, BorderLayout.CENTER);

        disableButtons(startButton, stopButton, restartButton);
        if (server.getStatus() == Status.OFFLINE) {
            startButton.setEnabled(true);
        } else if (server.getStatus() == Status.ONLINE) {
            stopButton.setEnabled(true);
            restartButton.setEnabled(true);
        }

        JLabel infoLabel = new JLabel("Minecraft Server Info:", SwingConstants.CENTER);
        JLabel statusLabel = new JLabel("Status: " + server.getStatus(), SwingConstants.CENTER);
        JLabel portLabel = new JLabel("Port: " + server.getPort());
        JLabel ipLabel = new JLabel("IP: " + server.getIp());

        JPanel infoPanel = new JPanel(new GridLayout(4, 1));
        infoPanel.add(infoLabel);
        infoPanel.add(statusLabel);
        infoPanel.add(portLabel);
        infoPanel.add(ipLabel);
        dialog.add(infoPanel, BorderLayout.EAST);

        dialog.setVisible(true);
    }

    private void showDiscordBotDetailsWindow(DiscordBot bot) {
        // Similar implementation for DiscordBot details
        // You can customize it based on the information you want to display
    }

    private void disableButtons(AbstractButton... buttons) {
        for (AbstractButton button : buttons) {
            button.setEnabled(false);
        }
    }
}
