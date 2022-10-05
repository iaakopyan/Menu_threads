class Repaint extends Thread {
    private Game game;
    Repaint(Game game) {
        this.game = game;
    }
    public void run() { //������ ������
        while (game.isRunning()) {
            game.repaint();
            try {
                sleep(10);

            } catch (InterruptedException e) {
                currentThread().interrupt();// e.printStackTrace(); //������������ ����������
            }
        }
    }
}