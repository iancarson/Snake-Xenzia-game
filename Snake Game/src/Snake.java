/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Nikhil
 */
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.JFrame;
import javax.swing.Timer;
import java.util.ArrayList;
import java.util.Random;
import sun.audio.*;

/**
 * This class controls the user interface
 * and renders the snake accordingly.
 */
public class Snake implements ActionListener, KeyListener {

    /**
     * The constant snake.
     */
    public static Snake snake;
    /**
     * The Jframe.
     */
    public JFrame jframe;
    /**
     * The Render panel.
     */
    public RenderPanel renderPanel;
    /**
     * The Timer.
     */
    public Timer timer = new Timer(20, this);
    /**
     * The Snake parts.
     */
    public ArrayList<Point> snakeParts = new ArrayList<Point>();
    /**
     * The constant UP.
     */
    public static final int UP = 0, /**
     * The Down.
     */
    DOWN = 1, /**
     * The Left.
     */
    LEFT = 2, /**
     * The Right.
     */
    RIGHT = 3, /**
     * The Scale.
     */
    SCALE = 10;
    /**
     * The Ticks.
     */
    public int ticks = 0, /**
     * The Direction.
     */
    direction = DOWN, /**
     * The Score.
     */
    score, /**
     * The Tail length.
     */
    tailLength = 10, /**
     * The Time.
     */
    time;
    /**
     * The Head.
     */
    public Point head, /**
     * The Cherry.
     */
    cherry;
    /**
     * The Random.
     */
    public Random random;
    /**
     * The Over.
     */
    public boolean over = false, /**
     * The Paused.
     */
    paused;
    /**
     * The Dim.
     */
    public Dimension dim;

    /**
     * Instantiates a new Snake.
     */
    Snake() {
        dim = Toolkit.getDefaultToolkit().getScreenSize();
        jframe = new JFrame("Snake");
        jframe.setVisible(true);
        jframe.setSize(805, 700);
        jframe.setResizable(false);

        jframe.setLocation(dim.width / 2 - jframe.getWidth() / 2, dim.height / 2 - jframe.getHeight() / 2);
        jframe.add(renderPanel = new RenderPanel());
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.addKeyListener(this);
        startGame();

    }

    /**
     * Start game.
     */
    public void startGame() {
        paused = false;
        over = false;
        time = 0;
        ticks = 0;
        score = 0;
        tailLength = 14;
        snakeParts.clear();
        direction = DOWN;
        random = new Random();
        head = new Point(0, -1);
        cherry = new Point(random.nextInt(79), random.nextInt(66));
        timer.start();
    }

    public void actionPerformed(ActionEvent e) {
        renderPanel.repaint();
        ticks++;

        if (ticks % 2 == 0 && head != null && !over && !paused) {
            time++;

            snakeParts.add(new Point(head.x, head.y));

            if (direction == UP) {
                if (head.y - 1 >= 0 && noTailAt(head.x, head.y - 1)) {
                    head = new Point(head.x, head.y - 1);
                }
                else if(head.y-1<0 &&  noTailAt(head.x, head.y - 1))
                {
                    head = new Point(head.x, 67-head.y - 1);
                }
                else {
                    over = true;

                }
            }

            if (direction == DOWN) {
                if (head.y + 1 < 67 && noTailAt(head.x, head.y + 1)) {
                    head = new Point(head.x, head.y + 1);
                }
                else if (head.y + 1 >= 67 && noTailAt(head.x, head.y + 1))
                {
                    head = new Point(head.x, 67-head.y);
                }
                else {
                    over = true;
                }
            }

            if (direction == LEFT) {
                if (head.x - 1 >= 0 && noTailAt(head.x - 1, head.y)) {
                    head = new Point(head.x - 1, head.y);
                }
                else if(head.x - 1 < 0 && noTailAt(head.x - 1, head.y))
                {
                     head = new Point(80+head.x - 1, head.y);
                    
                }
                else {
                    over = true;
                }
            }

            if (direction == RIGHT) {
                if (head.x + 1 <= 80 && noTailAt(head.x + 1, head.y)) {
                    head = new Point(head.x + 1, head.y);
                }
                else if(head.x + 1 > 80 && noTailAt(head.x + 1, head.y))
                {
                   head = new Point(80-head.x, head.y); 
                }
                else {
                    over = true;
                }
            }

            if (snakeParts.size() > tailLength) {
                snakeParts.remove(0);

            }

            if (cherry != null) {
                if (head.equals(cherry)) {
                    score += 10;
                    tailLength++;
                    cherry.setLocation(random.nextInt(79), random.nextInt(66));
                }
            }
        }
    }

    /**
     * No tail at boolean.
     *
     * @param x the x
     * @param y the y
     *
     * @return the boolean
     */
    public boolean noTailAt(int x, int y) {
        for (Point point : snakeParts) {
            if (point.equals(new Point(x, y))) {
                return false;
            }
        }
        return true;
    }


    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        snake = new Snake();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int i = e.getKeyCode();

        if ((i == KeyEvent.VK_A || i == KeyEvent.VK_LEFT) && direction != RIGHT) {
            direction = LEFT;
        }

        if ((i == KeyEvent.VK_D || i == KeyEvent.VK_RIGHT) && direction != LEFT) {
            direction = RIGHT;
        }

        if ((i == KeyEvent.VK_W || i == KeyEvent.VK_UP) && direction != DOWN) {
            direction = UP;
        }

        if ((i == KeyEvent.VK_S || i == KeyEvent.VK_DOWN) && direction != UP) {
            direction = DOWN;
        }

        if (i == KeyEvent.VK_SPACE) {
            if (over) {
                startGame();
            } else {
                paused = !paused;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

}
