/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author User
 */
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    GamePanel gp;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;  // Assign GamePanel instance to the KeyHandler
    }

    public boolean upPressed, downPressed, leftPressed, rightPressed;
    public boolean enterPressed, escPressed, spacePressed;  // Added spacePressed

    public KeyHandler() {
        // Initialize all key states as false
        upPressed = false;
        downPressed = false;
        leftPressed = false;
        rightPressed = false;
        enterPressed = false;
        escPressed = false;
        spacePressed = false;  // Initialize spacePressed
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Not used
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) {
            upPressed = true;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = true;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = true;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = true;
        }

        if (code == KeyEvent.VK_ENTER) {
            enterPressed = true;

            // Check if gp and gp.ui are non-null before accessing them
            if (gp != null && gp.ui != null && gp.ui.gameFinished) {
                gp.restartGame();  // Restart the game
            }
        }

        if (code == KeyEvent.VK_ESCAPE) {
            escPressed = true;
        }
        if (code == KeyEvent.VK_SPACE) {  // Detect spacebar press
            spacePressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) {
            upPressed = false;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = false;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = false;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = false;
        }
        if (code == KeyEvent.VK_ENTER) {
            enterPressed = false;
        }
        if (code == KeyEvent.VK_ESCAPE) {
            escPressed = false;
        }
        if (code == KeyEvent.VK_SPACE) {  // Detect spacebar release
            spacePressed = false;
        }
    }
}
