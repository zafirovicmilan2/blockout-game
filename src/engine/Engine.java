package engine;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class Engine implements EventHandler<KeyEvent> {



    public void addTransformation(){

    }

    @Override
    public void handle(KeyEvent event) {
        if (event.getCode() == KeyCode.RIGHT && event.getEventType() == KeyEvent.KEY_PRESSED) {
        } else if (event.getCode() == KeyCode.LEFT && event.getEventType() == KeyEvent.KEY_PRESSED) {

        } else if (event.getCode() == KeyCode.UP && event.getEventType() == KeyEvent.KEY_PRESSED) {

        } else if (event.getCode() == KeyCode.DOWN && event.getEventType() == KeyEvent.KEY_PRESSED) {

        } else if (event.getCode() == KeyCode.V && event.getEventType() == KeyEvent.KEY_PRESSED) {

        } else if (event.getCode() == KeyCode.F && event.getEventType() == KeyEvent.KEY_PRESSED) {

        } else if (event.getCode() == KeyCode.B && event.getEventType() == KeyEvent.KEY_PRESSED) {

        } else if (event.getCode() == KeyCode.G && event.getEventType() == KeyEvent.KEY_PRESSED) {

        } else if (event.getCode() == KeyCode.N && event.getEventType() == KeyEvent.KEY_PRESSED) {

        } else if (event.getCode() == KeyCode.H && event.getEventType() == KeyEvent.KEY_PRESSED) {

        }
    }
}
