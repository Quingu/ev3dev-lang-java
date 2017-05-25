package ev3dev.sensors;

import lombok.extern.slf4j.Slf4j;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public @Slf4j class EV3Key implements Key {

    public static final String SYSTEM_EVENT_PATH = "/dev/input/by-path/platform-gpio-keys.0-event";

    public static final int BUTTON_UP = 103;
    public static final int BUTTON_DOWN = 108;
    public static final int BUTTON_LEFT = 105;
    public static final int BUTTON_RIGHT = 106;
    public static final int BUTTON_ENTER = 28;
    public static final int BUTTON_BACKSPACE = 14;

    public static final int BUTTON_ALL = 0;

    private int button;

    //TODO Use an ENUM
    public EV3Key(int button) {
        if (button != BUTTON_UP &&
                button != BUTTON_DOWN &&
                button != BUTTON_LEFT &&
                button != BUTTON_RIGHT &&
                button != BUTTON_ENTER &&
                button != BUTTON_ENTER &&
                button != BUTTON_BACKSPACE &&
                button != BUTTON_ALL){
            throw new RuntimeException("The button that you specified does not exist. Better use the integer fields like Button.BUTTON_UP");
        }
        this.button = button;
    }

    /**
     * Returns whether the button is pressed.
     * @return Boolean that the button is pressed.
     */
    public boolean isPressed(){
        try {
            DataInputStream in = new DataInputStream(new FileInputStream(SYSTEM_EVENT_PATH));
            byte[] val = new byte[16];
            in.readFully(val);
            in.close();
            if(button == BUTTON_ALL){
                return true;
            }
            return test_bit(button, val);
        } catch (FileNotFoundException e){
            System.err.println("Error: Are you running this on your EV3? You must run it on your EV3.\n If you still have problems, report a issue to \"mob41/ev3dev-lang-java\".");
            e.printStackTrace();
            System.exit(-1);
            return false;
        } catch (IOException e){
            System.err.println("### ERROR MESSAGE ###\nError: Unexpected error! Report an issue to \"mob41/ev3dev-lang-java\" now, with logs!\n === STACK TRACE ===");
            e.printStackTrace();
            System.err.println("=== END STACK TRACE ===\nError: Unexpected error! Report an issue to \"mob41/ev3dev-lang-java\" now, with logs!\n ### END MESSAGE ###");
            System.exit(-1);
            return false;
        }
    }

    private static boolean test_bit(int bit, byte[] bytes){
        System.out.println("Bit: " + Integer.toHexString((bytes[bit / 8] & (1 << (bit % 8))) ));
        return ((bytes[bit / 8] & (1 << (bit % 8))) != 1);
    }

    @Override
    public int getId() {
        return button;
    }

    @Override
    public boolean isDown() {
        log.debug("This feature is not implemented");
        return false;
    }

    @Override
    public boolean isUp() {
        log.debug("This feature is not implemented");
        return false;
    }

    @Override
    public void waitForPress() {
        isPressed();
    }

    @Override
    public void waitForPressAndRelease() {
        log.debug("This feature is not implemented");
    }

    @Override
    public void addKeyListener(KeyListener listener) {
        log.debug("This feature is not implemented");
    }

    @Override
    public void simulateEvent(int event) {
        log.debug("This feature is not implemented");
    }

    @Override
    public String getName() {
        log.debug("This feature is not implemented");
        return null;
    }
}
