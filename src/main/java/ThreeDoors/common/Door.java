package ThreeDoors.common;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Door {
    private boolean isOpen = false;
    private boolean isPrizeHere = false;
}
