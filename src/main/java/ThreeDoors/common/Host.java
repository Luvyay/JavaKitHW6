package ThreeDoors.common;

import java.util.Random;

public class Host {
    private static final Random random = new Random();

    private Door[] doors = new Door[3];
    private ViewInterface viewInterface;

    private int numberOfDoorWithPrize;

    public Host(ViewInterface viewInterface) {
        this.viewInterface = viewInterface;
    }

    public void hidingPrize() {
        for (int i = 0; i < doors.length; i++) {
            doors[i] = new Door();
        }

        numberOfDoorWithPrize = random.nextInt(doors.length);
        doors[numberOfDoorWithPrize].setPrizeHere(true);
    }

    public void chooseDoor(int numberOfDoor) {
        viewInterface.result(doors[numberOfDoor].isPrizeHere());
    }

    public int openEmptyDoor(int firstChoose) {
        for (int i = 0; i < doors.length; i++) {
            if (i != firstChoose && !doors[i].isPrizeHere() && !doors[i].isOpen()) {
                doors[i].setOpen(true);
                return i;
            }
        }

        return -1;
    }

    public boolean isClose(int numberOfDoor) {
        if (!doors[numberOfDoor].isOpen()) return true;

        return false;
    }

    public boolean checkCorrectNumberOfDoor(int numberOfDoor) {
        return numberOfDoor >= 0 && numberOfDoor < doors.length;
    }

    public int getLengthDoors() {
        return doors.length;
    }
}
