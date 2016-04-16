package mygame.GUI;

import com.jme3.asset.AssetManager;
import com.jme3.input.InputManager;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;

/**
 *
 * @author worm
 */
public class MainMenu implements ScreenController {
    private Nifty nifty;
    
    public void init(AssetManager assetManager, Node rootNode, ViewPort view, InputManager inputManager) {   
        NiftyJmeDisplay niftyDisplay = new NiftyJmeDisplay(assetManager, inputManager, null, view);
        nifty = niftyDisplay.getNifty();
        nifty.fromXml("Interface/MenuScreen.xml", "start", this);
        // attach the nifty display to the gui view port as a processor
        view.addProcessor(niftyDisplay);
        inputManager.setCursorVisible(true);
    }

    public void bind(Nifty nifty, Screen screen) {
        System.out.println("bind( " + screen.getScreenId() + ") - main menu");
    }

    public void onStartScreen() {
        System.out.println("onStartScreen - main menu");
    }

    public void onEndScreen() {
        System.out.println("onEndScreen - main menu");
    }

    public void quit(){
        nifty.gotoScreen("end");
    }
}
