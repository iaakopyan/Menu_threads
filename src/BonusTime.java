class BonusTime extends Thread{
    private Update game;
    BonusTime(Update game) {

        this.game = game;
    }

    public void run(){

        game.SPEED=200;

        try {

            sleep(5000);

        } catch (InterruptedException e) {
            currentThread().interrupt(); //диагностикиа исключения
        }
        game.SPEED=400;

    }
}