package view.gameFrames;

import controller.editor.ReadingFiles;

public class GameUIController implements BoardController{

    MFrame frame;
    MFrame2 frame2;

    public GameUIController(){
        frame2 = new MFrame2();
    }

    @Override
    public void boardError(String message) {

    }
}
