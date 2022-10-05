import javax.swing.*;
import java.awt.*;

class Update extends Thread {
    private final Game game;
    Update(Game game){
        this.game=game;
    }
    public static int SPEED = 400;

    public void run() { //запуск задачи
        while (game.isRunning()) {

            int oldX = (int) game.getSnake().get(0).getX();
            int oldY = (int) game.getSnake().get(0).getY();

            if (game.getDirection() == 0) //w
                game.getSnake().get(0).setLocation(oldX, oldY - 1);
            else if (game.getDirection() == 1) //s
                game.getSnake().get(0).setLocation(oldX, oldY + 1);
            else if (game.getDirection() == 2) //a
                game.getSnake().get(0).setLocation(oldX - 1, oldY);
            else if (game.getDirection() == 3) //d
                game.getSnake().get(0).setLocation(oldX + 1, oldY);

            for (int i = 1; i < game.getSnake().size(); i++) {
                int tempX = (int) game.getSnake().get(i).getX();
                int tempY = (int) game.getSnake().get(i).getY();

                game.getSnake().get(i).setLocation(oldX, oldY);

                oldX = tempX;
                oldY = tempY;
            }

            if (game.getSnake().get(0).getX() < 0 || game.getSnake().get(0).getY() < 0 || game.getSnake().get(0).getX() >= game.getWidth() / game.WIDTH || game.getSnake().get(0).getY() >= game.getHeight() / game.WIDTH){

                JOptionPane.showMessageDialog(null, "Всего очков: "+game.points, "Ура", JOptionPane.WARNING_MESSAGE, game.icon);
                game.records.add(game.points);
                game.resetGame();
                game.points=0;}

            for (int i = 1; i < game.getSnake().size(); i++) {
                if (game.getSnake().get(i).distance(game.getSnake().get(0)) == 0) {
                    JOptionPane.showMessageDialog(null, "Всего очков: "+game.points, "Ура", JOptionPane.WARNING_MESSAGE, game.icon);
                    game.records.add(game.points);
                    game.resetGame();
                    break;
                }
            }
            if (game.getSnake().get(0).distance(game.getFood()) == 0) {
                game.getSnake().add(new Point(oldX, oldY));
                game.createFood();
            }

            if (game.getSnake().get(0).distance(game.getBonus()) == 0) {
                if(game.bonusTime.getState() == State.WAITING)
                    game.bonusTime.interrupt();
                game.bonusTime = new Thread(new BonusTime(Update.this));
                game.bonusTime.start(); //запуск потока
                game.createBonus();
            }

            try {
                sleep(SPEED);
            } catch (InterruptedException e) {
                currentThread().interrupt(); // e.printStackTrace();
            }
        }
    }
}