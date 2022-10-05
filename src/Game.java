import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import static java.util.Collections.max;

public class Game extends JPanel implements KeyListener {

    final ImageIcon icon = new ImageIcon(getClass().getResource("images/cat.gif")); //картинка для результата
    public static final int WIDTH = 20; // private static final int SNAKEWIDTH = 30; //из-за разных размеров фрукт не кушался
    private boolean running;
    private int direction;

    public int SPEED=400;
    ArrayList<Integer> records = new ArrayList<>();
    private ArrayList<Point2D> snake;
    private ArrayList<Integer> keys;
    private Point2D food;
    private Point2D bonus;
    public Game(JFrame frame) {
        super();
        frame.setIconImage(new ImageIcon(getClass().getResource("images/aa.png")).getImage());
        this.setKeys(new ArrayList<>());
        JOptionPane.showMessageDialog(null, "Для выхода жмите esc, это покажет рекорд", "ВАЖНО", JOptionPane.WARNING_MESSAGE, icon);
        this.setSize(frame.getWidth() - frame.getInsets().left - frame.getInsets().right, frame.getHeight() - frame.getInsets().top - frame.getInsets().bottom);
        this.setFocusable(true); //фокус на окошке со змейкой
        this.addKeyListener(this);

        this.resetGame();
        this.setRunning(true);

        new Thread(new Update(Game.this)).start(); //запуск потока
        new Thread(new Input(Game.this)).start(); //запуск потока
        new Thread(new Repaint(Game.this)).start(); //запуск потока
        bonusTime = new Thread(new BonusTime(null));
    }

    public void paint(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(new Color(34,139,34)); //фон
        g2d.fillRect(0,0,getWidth(),getHeight());

        g2d.setColor(new Color(0,255,127)); //змейка
        for (Point2D point : snake) {
            g2d.fillRect((int) point.getX() * WIDTH + 1, (int) point.getY() * WIDTH + 1,
                    WIDTH - 1, WIDTH - 1);
        }

        g2d.setColor(new Color(255, 20, 147)); //фрукт
        g2d.fillOval((int) food.getX() * WIDTH + 1, (int) food.getY() * WIDTH + 1, WIDTH - 1, WIDTH - 1); //фрукт круглый

        g2d.setColor(new Color(255, 20, 50));
        g2d.fillRoundRect((int) bonus.getX() * WIDTH , (int) bonus.getY() * WIDTH,WIDTH - 5, WIDTH - 5,20, 20);       //тупой бонус

    }

    void resetGame() {
        setDirection(-1); //чтобы в начале игры не бежала без команды

        setSnake(new ArrayList<>());
        getSnake().add(new Point((int) (Math.random() * getWidth() / WIDTH), (int) (Math.random() * getHeight() / WIDTH))); //ставит змейку в рандомное место в начале игры

        setFood(new Point());
        setBonus(new Point());
        createFood();
        createBonus();
    }

    void createBonus() {

        int x;
        int y;

        boolean flag;
        do {
            flag = false;
            x = (int) (Math.random() * getWidth() / WIDTH);
            y = (int) (Math.random() * getHeight() / WIDTH);

            for (Point2D point : snake) {
                if (point.distance(x, y) == 0) { //бонус кушается и генерируется снова
                    flag = true;
                }
            }
        } while (flag);
        this.getBonus().setLocation(x, y);
    }
    void createFood() {
        int x;
        int y;
        boolean flag;
        points++;
        do {
            flag = false;
            x = (int) (Math.random() * getWidth() / WIDTH);
            y = (int) (Math.random() * getHeight() / WIDTH);

            for (Point2D point : snake) {
                if (point.distance(x, y) == 0) { //фрукт кушается и генерируется снова
                    flag = true;
                }
            }System.out.println(points+" "+SPEED);

        } while (flag);
//первая часть выполняется каждый раз когда кушается фрукт
        this.getFood().setLocation(x, y);
    }

    public void keyPressed(KeyEvent e) { //выполняем при зажатии

        if (!keys.contains(e.getKeyCode()))
            keys.add(e.getKeyCode());
    }

    public void keyReleased(KeyEvent e) {
        keys.remove((Integer) e.getKeyCode()); // Выполняем при отпускании кнопки
    }

    public void keyTyped(KeyEvent e) { // Выполняем при нажатии кнопки
        if (keys.contains(KeyEvent.VK_ESCAPE)){//чтобы выход совершался на esc
            if(!records.isEmpty()){
                JOptionPane.showMessageDialog (null,"Ваш рекорд: "+max(records), "Ну круто", JOptionPane.WARNING_MESSAGE, icon);}
                System.exit(0); }
    }
    public ArrayList<Point2D> getSnake() {
        return snake;
    }
    public void setSnake(ArrayList<Point2D> snake) {
        this.snake = snake;
    }

    public Point2D getFood() {
        return food;
    }

    public Point2D getBonus(){
        return bonus;
    }

    public void setFood(Point2D food) {
        this.food = food;
    }

    public void setBonus(Point2D bonus){
        this.bonus=bonus;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public ArrayList<Integer> getKeys() {
        return keys;
    }

    public void setKeys(ArrayList<Integer> keys) {
        this.keys = keys;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }
    Thread bonusTime;
    int points = -1;
}