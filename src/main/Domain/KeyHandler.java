package main.Domain;

import main.Domain.Game;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    // Combined key states
    public boolean upPressed, downPressed, leftPressed, rightPressed;
    public boolean pPressed, rPressed, bPressed, aPressed, wPressed, sPressed, dPressed, cPressed;
    public Game game;

    public KeyHandler(Game game) {
        this.game = game;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // No action needed for keyTyped in this implementation
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (game.gameState == 1) {
            // Movement keys
            if (e.getKeyCode() == KeyEvent.VK_UP) {
                upPressed = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                downPressed = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                leftPressed = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                rightPressed = true;
            }

            // Enchantment and special keys
            if (e.getKeyCode() == KeyEvent.VK_P) {
                pPressed = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_R) {
                rPressed = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_B) {
                bPressed = true;
                new java.util.Timer().schedule(
                        new java.util.TimerTask() {
                            @Override
                            public void run() {
                                //reduce to normal
                                game.bpressed = false;
                            }
                        }, 2000
                );
            }
            if (e.getKeyCode() == KeyEvent.VK_A) {
                aPressed = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_W) {
                wPressed = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_S) {
                sPressed = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_D) {
                dPressed = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_C) {
                cPressed = true;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Movement keys
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            upPressed = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            downPressed = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            leftPressed = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            rightPressed = false;
        }

        // Enchantment and special keys
        if (e.getKeyCode() == KeyEvent.VK_P) {
            pPressed = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_R) {
            rPressed = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_B) {
            bPressed = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_A) {
            aPressed = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_W) {
            wPressed = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_S) {
            sPressed = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_D) {
            dPressed = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_C) {
            cPressed = false;
        }
    }
}
