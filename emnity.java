import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class emnity extends GameEngine {

    public static void main(String args[]) {
        createGame(new emnity(), 60);
    }

    boolean jump, left, right, down;
    int jumpCount;

    /* --- EDIT THESE VALUES AS NEEDED TO CHANGE MOVEMENT --- */
    final static int FPS = 60; // frames per second;
    final double GROUND = 450.0; // y-coordinate of ground.
    final double GRAVITY = 40.0;
    final double HORIZONTAL_ACCELERATION = 50.0;
    final double HORIZONTAL_DECELERATION = 20.0;
    final double VERTICAL_ACCELERATION = 10.0;
    final double VERTICAL_DECELERATION = 50.0;
    final double MAX_VERTICAL_VELOCITY = -600;
    final double MAX_HORIZONTAL_VELOCITY = 300;

    /* --- PLAYER --- */
    double playerX, playerY; // position of player.
    double playerVX, playerVY; // velocity of player.

    public void initPlayer() {
        playerX = width()/2;
        playerY = GROUND;
        playerVX = 0;
        playerVY = 0;
    }

    public void updatePlayer(double dt) {
        playerX += playerVX*dt;
        playerY += playerVY*dt;

        if (left) { if (playerVX > -MAX_HORIZONTAL_VELOCITY) { playerVX-= HORIZONTAL_ACCELERATION; } } 
        if (right) { if (playerVX < MAX_HORIZONTAL_VELOCITY) { playerVX+= HORIZONTAL_ACCELERATION; } }
        if (!left && !right) { 
            if (playerVX < 0 && playerVX != 0) { playerVX+= HORIZONTAL_DECELERATION; } 
            if (playerVX > 0 && playerVX != 0) { playerVX-= HORIZONTAL_DECELERATION; }
        }
        if (down && playerY < GROUND) { playerVY+= VERTICAL_DECELERATION; }

        if (jump) {
            if (playerY > GROUND) {
                playerY = GROUND;
                playerVY = 0;
                jump = false;
                jumpCount = 0;
            } else { playerVY+= GRAVITY; }
        }
    }

    public void drawPlayer() {
        changeColor(white);
        drawSolidCircle(playerX, playerY, 10);
    }

    @Override
    public void init() {
        jump = false;
        left = false;
        right = false;
        down = false;
        jumpCount = 0;
        initPlayer();

        System.out.println(width() + " " + height());
    }

    @Override
    public void update(double dt) {
       updatePlayer(dt);
    }

    @Override
    public void paintComponent() {
        changeBackgroundColor(black);
        clearBackground(width(), height());
        drawPlayer();
        
        // DEBUG.
        changeColor(white);
        if (jump) { drawBoldText(5, 25, "jump " + jumpCount); }
        if (left) { drawBoldText(5, 60, "left"); }
        if (right) { drawBoldText(5, 90, "right"); }
        if (down) { drawBoldText(5, 120, "down"); }
    }
    
    @Override
    public void keyPressed(KeyEvent e) 
    {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) { left = true; }  
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) { right = true; }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) { down = true; }
        if (e.getKeyCode() == KeyEvent.VK_SPACE) { 
            if (!jump || jumpCount < 2) {
                jump = true; 
                playerVY = MAX_VERTICAL_VELOCITY;
                jumpCount++;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) { left = false; }  
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) { right = false; }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) { down = false; }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mouseDragged(MouseEvent e) {}

    @Override
    public void mouseMoved(MouseEvent e) {}
}
