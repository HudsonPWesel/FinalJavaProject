import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.sound.sampled.Clip;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable {
    final int originalTileSize = 16;
    final int scale = 3;

    public final int tileSize = originalTileSize * scale;
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;
    public final int FPS = 60;
    public boolean isPlayingBossTheme = false;
    public boolean isPlayingSoundEffect = false;

    public Thread gameThread;
    public KeyHandler keyHandler = new KeyHandler();
    public Player player = new Player(this, keyHandler, "player-spritesheet.png");
    public TileManager tileManager;
    public CollisionChecker collisionChecker = new CollisionChecker(this);
    public InteractableObject amongUs;

    // World Settings
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;

    public boolean isBattlingBoss = false;
    public Clip amongUsClip;
    public Clip bossMusic;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
        this.tileManager = new TileManager(this);

        amongUs = new InteractableObject(Sprite.initSprite("amongus"), "amongus", tileSize * 37,
                tileSize * 8);
    }

    /**
     * Starts the game clock and sound
     */
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();

        // SoundManager.playSound(PathFinder.getFilePathForFile("background.wav").toFile());

    }

    /**
     * Core delta game loop of game
     */
    @Override
    public void run() {
        double drawInterval = 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null) {

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            // Delta has reached draw interval
            if (delta >= 1) {
                // Update information of game
                update();

                // Draw that reflects new information of game
                repaint();
                delta--;
            }
        }

    }

    public void update() {
        player.update();

    }

    /**
     * Draws map and player
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        tileManager.draw(g2d, PathFinder.getFilePathForFile("map.txt").toAbsolutePath().toString());
        player.draw(g2d);

        InteractableObject chest = new InteractableObject(
                new Sprite("chests", new int[] { 120, 130 }, new String[] { "Chest-Opening" }, new int[] { 2 }),
                "Chest", tileSize * 21,
                tileSize * 23);
        chest.draw((Graphics2D) g, this, "Chest-Opening");

        amongUs.draw((Graphics2D) g, this);

        drawHearts(g2d);
        g2d.dispose();

    }

    public int checkForDamage() {
        initBossBattle();
        return -1;

    }

    private void initBossBattle() {
        if (Math.abs(amongUs.worldX - player.worldX) < 300 && Math.abs(amongUs.worldY - player.worldY) < 280) {
            isBattlingBoss = true;
            if (!isPlayingBossTheme && !isPlayingSoundEffect) {
                amongUsClip = SoundManager.playSound(PathFinder.getFilePathForFile("amongusEffect.wav").toFile());
                bossMusic = SoundManager.playSound(PathFinder.getFilePathForFile("bosstheme.wav").toFile());

                amongUsClip.start();
                bossMusic.start();

                isPlayingBossTheme = true;
                isPlayingSoundEffect = true;

            } else if (Math.abs(amongUs.worldX - player.worldX) < 80
                    && Math.abs(amongUs.worldY - player.worldY) < 100 && player.keyHandler.attackPressed) {
                System.out.println("AMONGUS");
                // Check if player is damaging boss

            }

        } else {
            System.out.println(Math.abs(amongUs.worldX - player.worldX));
            System.out.println(Math.abs(amongUs.worldY - player.worldY));
            isPlayingBossTheme = false;
            isPlayingSoundEffect = false;
            if (bossMusic != null)
                bossMusic.stop();
        }

    }

    private void drawHearts(Graphics2D g2d) {
        int[] healthIndicies = new int[3];
        int numFullHearts = player.health / 2;
        int remainder = player.health % 2;

        // Init num of full hearts
        for (int i = 0; i < (numFullHearts); i++)
            healthIndicies[i] = 2;

        for (int i = numFullHearts; i < numFullHearts + remainder; i++)
            healthIndicies[i] = 1;

        for (int i = 0; i < player.hearts.size(); i++) {
            g2d.drawImage(player.hearts.get(healthIndicies[i]), 15 + (i * 35), 15, tileSize / 2, tileSize / 2, null);

        }
    }
}
