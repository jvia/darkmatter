package com.giantcow.darkmatter;

import com.giantcow.darkmatter.level.MusicPlayer;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * @author Jeremiah M Via. <jxv911@cs.bham.ac.uk>
 * @author Yukun Wang <yxw999@cs.bham.ac.uk>
 * @version 2011.0312
 */
public class MainWindow extends JFrame {

    public static void main(String[] args) {
        MainWindow m = new MainWindow();
        m.repaint(20);
    }

    // The panels
    /** The root panel of the CardLayout */
    private JPanel rootPanel;
    /** The game panel. */
    private DarkMatterNetworked darkMatter;
    Background bg;

    // Game images
    /** The title image; says "DarkMatter". */
    ImageIcon title;
    /** Single player menu icon. */
    ImageIcon singlePlayerIcon;
    /** Single player menu rollover icon. */
    ImageIcon singlePlayerIconRollover;
    /** Multiplayer menu icon. */
    ImageIcon multiplayerIcon;
    /** Multiplayer menu rollover icon. */
    ImageIcon multiplayerIconRollover;
    /** Instructions icon. */
    ImageIcon howToPlayIcon;
    /** Instructions rollover icon. */
    ImageIcon howToPlayIconRollover;
    /** About icon. */
    ImageIcon aboutIcon;
    /** About rollover icon. */
    ImageIcon aboutIconRollover;
    /** Back icon. */
    ImageIcon backIcon;
    /** Back rollover icon. */
    ImageIcon backIconRollover;
    /** Quit icon. */
    ImageIcon quitIcon;
    /** Quit rollover icon. */
    ImageIcon quitIconRollover;
    /** Host icon. */
    ImageIcon hostIcon;
    /** Port icon. */
    ImageIcon portIcon;
    /** Play icon. */
    ImageIcon playIcon;
    /** Play rollover icon. */
    ImageIcon playIconRollover;

    /**
     * Creates the MainWindow. This class run the whole interaction with the game. Users can look at
     * instructions on how to play the game, select different modes of play and even learn about the
     * development team.
     */
    public MainWindow() {
        // Prepare GUI for full screen
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setUndecorated(true);
        setResizable(false);
        GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().setFullScreenWindow(this);

        // Load back icon since it will be used everywhere
        backIcon = new ImageIcon(getClass().getClassLoader().getResource("menu/back.png"));
        backIconRollover =
                new ImageIcon(getClass().getClassLoader().getResource("menu/back_o.png"));


        // Make our root panel and give it the card layout
        rootPanel = new JPanel(new CardLayout());
        rootPanel.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
        rootPanel.setOpaque(false);

        // Add our background image
        bg = new Background();
        bg.add(rootPanel);
        add(bg);

        // Make the main menu and make it see through
        MainMenu menu = new MainMenu();
        menu.setOpaque(false);

        // Make the dark matter panel
        JPanel panel = new JPanel();
        panel.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
        panel.setOpaque(false);
        darkMatter = new DarkMatterNetworked(rootPanel);
        darkMatter.setOpaque(false);
        panel.add(darkMatter, BorderLayout.CENTER);

        // Create instructions panel
        InstructionsPanel instructions = new InstructionsPanel();
        instructions.setOpaque(false);

        // Create about panel
        AboutPanel about = new AboutPanel();
        about.setOpaque(false);

        // Create multiplayer panel
        MultiPlayerMenu multiPlayerMenu = new MultiPlayerMenu();


        // Add panels to root panel and give them a handle so we can switch between them
        rootPanel.add(menu, "menu");
        rootPanel.add(panel, "darkmatter");
        rootPanel.add(instructions, "instructions");
        rootPanel.add(about, "about");
        rootPanel.add(multiPlayerMenu, "multiPlayerMenu");

//        // Set selector
//        try {
//            // Get image & dimensions
//            Image cursorImage =
//                    ImageIO.read(getClass().getClassLoader().getResource("menu/Selector.png"));
//            Dimension d = Toolkit.getDefaultToolkit().getBestCursorSize(cursorImage.getWidth(null),
//                                                                        cursorImage.getHeight(null));
//            int width = d.width;
//
//            // Create cursor
//            cursorImage = cursorImage.getScaledInstance(width, width, Image.SCALE_SMOOTH);
//            Point cursorPoint = new Point(width / 2, width / 2);
//            Cursor cursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImage,
//                                                                           cursorPoint,
//                                                                           "myCursor");
//
//            // Make it out cursor
//            this.setCursor(cursor);
//        } catch (IOException ex) {
//            System.out.println(ex.getMessage());
//        }

        // Make snug and visible
        pack();
        setVisible(true);

        // Start the game music
        new MusicPlayer().start();
    }

    /** Displays the main menu. */
    class MainMenu extends JPanel implements ActionListener {

        JButton singlePlayer;
        JButton multiPlayer;
        JButton howToPlay;
        JButton about;
        JButton quit;

        public static final String SINGLE_PLAYER = "Single Player";
        public static final String MULTI_PLAYER = "Multi player";
        public static final String HOW_TO_PLAY = "How to Play";
        public static final String ABOUT = "About";

        public MainMenu() {
            // Load images
            ClassLoader l = getClass().getClassLoader();
            title = new ImageIcon(l.getResource("menu/title.png"));
            singlePlayerIcon = new ImageIcon(l.getResource("menu/single.png"));
            singlePlayerIconRollover = new ImageIcon(l.getResource("menu/single_o.png"));
            multiplayerIcon = new ImageIcon(l.getResource("menu/multi.png"));
            multiplayerIconRollover = new ImageIcon(l.getResource("menu/multi_o.png"));
            howToPlayIcon = new ImageIcon(l.getResource("menu/howto.png"));
            howToPlayIconRollover = new ImageIcon(l.getResource("menu/howto_o.png"));
            aboutIcon = new ImageIcon(l.getResource("menu/about.png"));
            aboutIconRollover = new ImageIcon(l.getResource("menu/about_o.png"));
            quitIcon = new ImageIcon(l.getResource("menu/quit.png"));
            quitIconRollover = new ImageIcon(l.getResource("menu/quit_o.png"));


            setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
            JLabel label = new JLabel(title);
            JButton invisible = new JButton();
            invisible.setBorderPainted(false);
            invisible.setBackground(new Color(0, 0, 0, 0));
            add(invisible);
            add(Box.createVerticalGlue());
            add(label);
            add(Box.createVerticalGlue());

            // Set up single player button
            singlePlayer = new JButton(singlePlayerIcon);
            //singlePlayer.setRolloverIcon(singlePlayerIconRollover);
            singlePlayer.setActionCommand(SINGLE_PLAYER);
            singlePlayer.setBackground(new Color(0, 0, 0, 0));
            singlePlayer.setBorderPainted(false);
            singlePlayer.addActionListener(this);
            add(singlePlayer);
            add(Box.createVerticalGlue());

            // Set up multiplayer button
            multiPlayer = new JButton(multiplayerIcon);
            //multiPlayer.setRolloverIcon(multiplayerIconRollover);
            multiPlayer.setActionCommand(MULTI_PLAYER);
            multiPlayer.setBackground(new Color(0, 0, 0, 0));
            multiPlayer.setBorderPainted(false);
            multiPlayer.addActionListener(this);
            add(multiPlayer);
            add(Box.createVerticalGlue());

            // Set up how to player button
            howToPlay = new JButton(howToPlayIcon);
            //howToPlay.setRolloverIcon(howToPlayIconRollover);
            howToPlay.setActionCommand(HOW_TO_PLAY);
            howToPlay.setBackground(new Color(0, 0, 0, 0));
            howToPlay.setBorderPainted(false);
            howToPlay.addActionListener(this);
            add(howToPlay);
            add(Box.createVerticalGlue());

            // Set up about button
            about = new JButton(aboutIcon);
            //about.setRolloverIcon(aboutIconRollover);
            about.setActionCommand(ABOUT);
            about.addActionListener(this);
            about.setBackground(new Color(0, 0, 0, 0));
            about.setBorderPainted(false);
            add(about);
            add(Box.createVerticalGlue());

            // Set up quit button
            quit = new JButton(quitIcon);
            //quit.setRolloverIcon(quitIconRollover);
            quit.setBackground(new Color(0, 0, 0, 0));
            quit.setBorderPainted(false);
            quit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.exit(0);
                }
            });
            add(quit);
            add(Box.createVerticalGlue());
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String action = e.getActionCommand();
            CardLayout cl = (CardLayout) rootPanel.getLayout();

            if (action.equals(SINGLE_PLAYER)) {
                darkMatter.setSinglePlayer(true);
                pack();
                cl.show(rootPanel, "darkmatter");
                new Thread(darkMatter).start();
//                bg.setBackground(Toolkit.getDefaultToolkit().createImage(getClass().getClassLoader().getResource("nebbg_occluded.png")));
            } else if (action.equals(MULTI_PLAYER)) {
                darkMatter.setSinglePlayer(false);
                pack();
                cl.show(rootPanel, "multiPlayerMenu");
            } else if (action.equals(HOW_TO_PLAY)) {
                cl.show(rootPanel, "instructions");
            } else if (action.equals(ABOUT)) {
                cl.show(rootPanel, "about");
            }
        }
    }

    /** Displays the multiplayer menu. */
    class MultiPlayerMenu extends JPanel {

        public MultiPlayerMenu() {
            Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
            // Load images
            ClassLoader l = getClass().getClassLoader();
            hostIcon = new ImageIcon(l.getResource("menu/host.png"));
            portIcon = new ImageIcon(l.getResource("menu/port.png"));
            playIcon = new ImageIcon(l.getResource("menu/play.png"));
            playIconRollover = new ImageIcon("menu/play_o.png");
            setOpaque(false);

            // Set up host info
            JLabel host = new JLabel(hostIcon);
            final JTextField hostField = new JTextField("localhost");
            hostField.setColumns(30);
            hostField.setPreferredSize(new Dimension(60, 30));
            JPanel hostPanel = new JPanel();
            hostPanel.setPreferredSize(new Dimension(screen.width, 100));
            hostPanel.setOpaque(false);
            hostPanel.add(host, BorderLayout.LINE_START);
            hostPanel.add(hostField, BorderLayout.LINE_END);
            add(hostPanel, BorderLayout.PAGE_START);

            // Se up port info
            JLabel port = new JLabel(portIcon);
            final JTextField portField = new JTextField("1234");
            portField.setColumns(30);
            portField.setPreferredSize(new Dimension(60, 30));
            JPanel portPanel = new JPanel();
            portPanel.setPreferredSize(new Dimension(screen.width, 100));
            portPanel.setOpaque(false);
            portPanel.add(port, BorderLayout.LINE_START);
            portPanel.add(portField, BorderLayout.LINE_END);
            add(portPanel, BorderLayout.CENTER);


            JButton goBack = new JButton(backIcon);
            //goBack.setRolloverIcon(backIconRollover);
            goBack.setAlignmentX(Component.CENTER_ALIGNMENT);
            goBack.setBackground(new Color(0, 0, 0, 0));
            goBack.setBorderPainted(false);
            goBack.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    CardLayout cl = (CardLayout) rootPanel.getLayout();
                    cl.show(rootPanel, "menu");
                }
            });

            JButton play = new JButton(playIcon);
            System.out.println(play.getHeight() + " " + play.getWidth());
            //play.setRolloverIcon(playIconRollover);
            play.setBackground(new Color(0, 0, 0, 0));
            play.setAlignmentX(Component.CENTER_ALIGNMENT);
            play.setBorderPainted(false);
            play.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    CardLayout cl = (CardLayout) rootPanel.getLayout();
                    cl.show(rootPanel, "darkmatter");
                    darkMatter.setHost(hostField.getText());
                    darkMatter.setPort(Integer.parseInt(portField.getText()));
                    new Thread(darkMatter).start();
                }
            });

            JPanel buttons = new JPanel();
            buttons.setOpaque(false);
            buttons.add(goBack, BorderLayout.LINE_START);
            buttons.add(play, BorderLayout.LINE_END);
            add(buttons, BorderLayout.PAGE_END);
        }
    }

    /** Defines the panel that contains the instructions. */
    class InstructionsPanel extends JPanel {

        public InstructionsPanel() {
            setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
            setPreferredSize(new Dimension(500, 900));

            JTextArea instructions = new JTextArea();
            instructions.setEditable(false);
            instructions.setAlignmentX(Component.CENTER_ALIGNMENT);
            instructions.setLineWrap(true);
            instructions.setWrapStyleWord(true);
            instructions.setOpaque(false);
            instructions.setMaximumSize(new Dimension(500, 700));
            instructions.setFont(Utils.smallFont);
            instructions.setForeground(Color.white);
            instructions.setText(Utils.instructions);
            instructions.setEnabled(false);

            JButton goBack = new JButton(backIcon);
            goBack.setRolloverIcon(backIconRollover);
            goBack.setAlignmentX(Component.CENTER_ALIGNMENT);
            goBack.setBackground(new Color(0, 0, 0, 0));
            goBack.setBorderPainted(false);
            goBack.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    CardLayout cl = (CardLayout) rootPanel.getLayout();
                    cl.show(rootPanel, "menu");
                }
            });

            add(instructions, BorderLayout.PAGE_START);
            add(goBack, BorderLayout.PAGE_END);
        }
    }

    class AboutPanel extends JPanel {

        public AboutPanel() {
            JPanel panel = new JPanel();
            panel.setLayout(new BorderLayout());
            panel.setOpaque(false);

            ImageIcon giantCow =
                    new ImageIcon(getClass().getClassLoader().getResource("menu/logo.png"));
            JLabel label = new JLabel(giantCow);
            label.setSize(360, 540);
            panel.add(label, BorderLayout.NORTH);
            //panel.setPreferredSize(new Dimension(giantCow.getIconWidth() + 200, 900));

            JTextArea aboutText = new JTextArea(Utils.about);
            aboutText.setOpaque(false);
            aboutText.setLineWrap(true);
            aboutText.setWrapStyleWord(true);
            aboutText.setEditable(false);
            aboutText.setEnabled(false);
            aboutText.setFont(Utils.smallFont);
            aboutText.setForeground(Color.white);
            panel.add(aboutText, BorderLayout.CENTER);


            JButton goBack = new JButton(backIcon);
            goBack.setRolloverIcon(backIconRollover);
            goBack.setAlignmentX(Component.CENTER_ALIGNMENT);
            goBack.setBackground(new Color(0, 0, 0, 0));
            goBack.setBorderPainted(false);
            goBack.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    CardLayout cl = (CardLayout) rootPanel.getLayout();
                    cl.show(rootPanel, "menu");
                }
            });

            panel.add(goBack, BorderLayout.SOUTH);
            add(panel);
        }
    }

    class Background extends JPanel {

        Image img;

        public Background() {
            super();
            img =
                    Toolkit.getDefaultToolkit().createImage(getClass().getClassLoader().getResource("nebbg_occluded.png"));
        }

        public void setBackground(Image img) {
            this.img = img;
            repaint();
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                RenderingHints.VALUE_ANTIALIAS_ON);
            g2.drawImage(img, 0, 0, this);
        }
    }
}
