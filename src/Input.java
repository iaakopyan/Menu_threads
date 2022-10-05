import java.awt.event.KeyEvent;
class Input extends Thread {
    private Game game;
    Input(Game game) {
        this.game = game;
    }
    public void run() { //запуск задачи
        while (game.isRunning()) {
            if (!game.getKeys().isEmpty())
                if (game.getKeys().get(game.getKeys().size() - 1) == KeyEvent.VK_W && (game.getDirection() != 1 || game.getSnake().size() == 1))
                    game.setDirection(0); //w
                else if (game.getKeys().get(game.getKeys().size() - 1) == KeyEvent.VK_S && (game.getDirection() != 0 || game.getSnake().size() == 1))
                    game.setDirection(1); //s
                else if (game.getKeys().get(game.getKeys().size() - 1) == KeyEvent.VK_A && (game.getDirection() != 3 || game.getSnake().size() == 1))
                    game.setDirection(2); //a
                else if (game.getKeys().get(game.getKeys().size() - 1) == KeyEvent.VK_D && (game.getDirection() != 2 || game.getSnake().size() == 1))
                    game.setDirection(3); //d


            try {
                sleep(5);
            } catch (InterruptedException e) {
                currentThread().interrupt(); // e.printStackTrace(); //диагностикиа исключения
            }
        }
    }
}
