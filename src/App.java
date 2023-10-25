import Networking.Communication;
import UI.Frame;
import UI.MainMenu;

public class App {

    public static void main(String[] args) throws Exception {
        new MainMenu(new Frame().init(), new Communication());    
    }
}
