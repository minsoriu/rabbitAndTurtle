public class Main {
    public static void main(String[] args) throws InterruptedException {
        RabbitAndTurtle.race();
    }
}

class AnimalThread extends Thread {
    String name;
    int priority;
    private int distance = 0;
    AnimalThread(String name, int priority) {
        this.name = name;
        this.priority = priority;
        setPriority(this.priority);
    }
    @Override
    public void run() {
        while (this.distance < 30) {
            distance++;
            System.out.println(this.name + ": " + this.distance + " м");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public int getDistance() { return this.distance; }
}

class RabbitAndTurtle {
    public static void race() throws InterruptedException {
        AnimalThread rabbit = new AnimalThread("Кролик", 5);
        AnimalThread turtle = new AnimalThread("Черепаха", 5);
        rabbit.start();
        turtle.start();
        while (rabbit.isAlive() && turtle.isAlive()) {
            if (rabbit.getDistance() > turtle.getDistance()) {
                turtle.setPriority(10);
                rabbit.setPriority(1);
            } else if (turtle.getDistance() > rabbit.getDistance()) {
                rabbit.setPriority(10);
                turtle.setPriority(1);
            }
        }
        rabbit.join();
        turtle.join();
        if (rabbit.getDistance() >= 30) {
            System.out.println("Кролик победил!");
        } else {
            System.out.println("Черепаха победила!");
        }
    }
}