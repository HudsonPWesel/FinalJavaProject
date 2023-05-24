import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    public boolean upPressed, downPressed, leftPressed, rightPressed, attackPressed;

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        handleKeyPressInput(keyCode);

    }

    private void handleKeyPressInput(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_W:
                upPressed = true;
                break;
            case KeyEvent.VK_D:
                rightPressed = true;
                break;
            case KeyEvent.VK_A:
                leftPressed = true;
                break;
            case KeyEvent.VK_S:
                downPressed = true;
                break;
            case KeyEvent.VK_J:
                attackPressed = true;
                break;

            default:
                break;
        }
    }

    private void handleKeyReleaseInput(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_W:
                upPressed = false;

                break;
            case KeyEvent.VK_D:
                rightPressed = false;
                break;
            case KeyEvent.VK_A:
                leftPressed = false;

                break;
            case KeyEvent.VK_S:
                downPressed = false;
                break;
            case KeyEvent.VK_J:
                attackPressed = false;
                break;

            default:
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
        int keyCode = e.getKeyCode();
        handleKeyReleaseInput(keyCode);

    }

}
