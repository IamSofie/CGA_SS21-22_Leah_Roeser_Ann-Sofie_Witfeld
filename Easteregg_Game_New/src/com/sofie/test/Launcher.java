package com.sofie.test;

import com.sofie.core.EngineManager;
import com.sofie.core.WindowManager;
import com.sofie.core.utils.Consts;



public class Launcher {
    private static WindowManager window;
    private static TestGame game;

    public static void main(String[] args){
        window = new WindowManager(Consts.TITLE, 1600, 900, false);
        game = new TestGame();
        EngineManager engine = new EngineManager();

        try {
            engine.start();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static WindowManager getWindow() {
        return window;
    }

    public static TestGame getGame() {
        return game;
    }
}
