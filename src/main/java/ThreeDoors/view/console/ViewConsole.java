package ThreeDoors.view.console;

import ThreeDoors.common.Host;
import ThreeDoors.common.ModeGame;
import ThreeDoors.common.ViewInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class ViewConsole implements ViewInterface {
    private static final Random random = new Random();

    private List<Boolean> statistics;
    private Host host;
    private ModeGame modeGame;

    private int countIteration;
    private boolean isChangeChoose;

    public ViewConsole(int countIteration, ModeGame modeGame, boolean isChangeChoose) {
        this.countIteration = countIteration;
        this.modeGame = modeGame;
        this.isChangeChoose = isChangeChoose;

        statistics = new ArrayList<>();
        host = new Host(this);
    }

    public ViewConsole(ModeGame modeGame, boolean isChangeChoose) {
        this(1000, modeGame, isChangeChoose);
    }

    public ViewConsole() {
        this(ModeGame.RANDOM, true);
    }

    @Override
    public void result(boolean isWin) {
        if (isWin) {
            System.out.println("Вы выиграли АВТОМОБИЛЬ!");
        } else {
            System.out.println("Здесь пусто");
        }

        statistics.add(isWin);
    }

    public void start() {
        showStartText();

        if (modeGame == ModeGame.HUMAN) {
            playModHuman();
        } else if (modeGame == ModeGame.RANDOM) {
            playModRandom();
        }

        showStatistics();
    }

    private void showStartText() {
        System.out.println("Игра три двери.");
        System.out.println("Данная игра предлагает на выбор три двери и только за одной дверью находится приз.");
        System.out.println("Ваша задача угадать за какой дверью находится приз. Удачи!");
    }

    private void playModHuman() {
        for (int i = 0; i < countIteration; i++) {
            host.hidingPrize();

            System.out.println("Все двери закрыты. Выберите одну из дверей.");

            int firstChoose = chooseDoorOne();

            System.out.println("Вы выбрали дверь под номером: " + firstChoose);

            openEmptyDoor(firstChoose - 1); // firstChoose -1 because user choose between [1;3] and need [0;2]

            System.out.println("Поменяете свой выбор или оставите свой выбор на двери с номером: "
                    + firstChoose + "?");

            chooseDoor();

            System.out.println("------------------------------");
        }
    }

    private int chooseDoorOne() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Номер двери: ");
        int firstChoose = scanner.nextInt();

        while (!host.checkCorrectNumberOfDoor(firstChoose - 1)) {
            System.out.println("Не верный номер двери. Укажите корректный номер.");

            System.out.print("Номер двери: ");
            firstChoose = scanner.nextInt();
        }

        return firstChoose;
    }

    private void openEmptyDoor(int firstChoose) {
        System.out.println("Для интереса и азарта мы открое одну дверь.");
        int numberOfEmptyOpenDoor = host.openEmptyDoor(firstChoose) + 1;
        System.out.println("Была открыта дверь: " + numberOfEmptyOpenDoor + " и за данной дверью ПУСТО.");
    }

    public void chooseDoor() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Номер двери: ");
        int curNumberOfDoor = scanner.nextInt();

        while (!host.checkCorrectNumberOfDoor(curNumberOfDoor - 1) || !host.isClose(curNumberOfDoor - 1)) {
            if (!host.checkCorrectNumberOfDoor(curNumberOfDoor - 1)) {
                System.out.println("Не верный номер двери. Укажите корректный номер.");
            } else if (!host.isClose(curNumberOfDoor - 1)) {
                System.out.println("Данная дверь уже открыта. Выберите другую!");
            }

            System.out.print("Номер двери: ");
            curNumberOfDoor = scanner.nextInt();
        }

        host.chooseDoor(curNumberOfDoor - 1);
    }

    private void playModRandom() {
        for (int i = 0; i < countIteration; i++) {
            host.hidingPrize();

            int randomNumberDoor = random.nextInt(host.getLengthDoors());
            int emptyOpenDoor = host.openEmptyDoor(randomNumberDoor);

            if (isChangeChoose) {
                int changeNumber = randomNumberDoor;

                for (int j = 0; j < host.getLengthDoors(); j++) {
                    if (j != randomNumberDoor && j != emptyOpenDoor) {
                        changeNumber = j;
                        break;
                    }
                }

                host.chooseDoor(changeNumber);
            } else {
                host.chooseDoor(randomNumberDoor);
            }
        }
    }

    private void showStatistics() {
        System.out.println("------------------------------");
        System.out.println("Статистика по игре.");
        System.out.println("Всего игр: " + countIteration);

        int countWin = 0;
        int countLose = 0;

        for (int i = 0; i < statistics.size(); i++) {
            if (statistics.get(i)) {
                countWin++;
            } else {
                countLose++;
            }
        }

        int percentWin = countWin * 100 / countIteration;
        int percentLose = countLose * 100 / countIteration;

        System.out.println("Побед: " + countWin + " (процент побед: " + percentWin + "%)");
        System.out.println("Проигрышей: " + countLose + " (процент проигрышей: " + percentLose + "%)");
    }
}
