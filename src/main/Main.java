package main;

import main.Domain.WallHandler;
import main.UI.MainMenuFrame;

public class Main {
    public static void main(String[] args) {
        WallHandler wallHandler = WallHandler.getInstance();
        wallHandler.resetAllMaps();
        new MainMenuFrame();
    }
}
