package mygame.controllers;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.input.InputManager;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import mygame.Main;

public class GameOverScreenStateController extends AbstractAppState implements ScreenController {
    private Nifty nifty;
    
    private AssetManager assetManager;
    private Node rootNode;
    private ViewPort guiViewPort;
    private InputManager inputManager;
    private SimpleApplication game;
    
    public GameOverScreenStateController(SimpleApplication app) {
        assetManager = app.getAssetManager();
        rootNode = app.getRootNode();
        guiViewPort = app.getViewPort();
        inputManager = app.getInputManager();
        game = app;
        nifty = Main.getNifty();
    }
    
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app); 
        
        this.init(assetManager, rootNode, guiViewPort, inputManager);
    }  
        
    @Override
    public void cleanup() {
      super.cleanup();
      // unregister all my listeners, detach all my nodes, etc...
//      this.app.getRootNode().detachChild(getX()); // modify scene graph...
//      this.app.doSomethingElse();                 // call custom methods...
    }
 
    @Override
    public void setEnabled(boolean enabled) {
      // Pause and unpause
      super.setEnabled(enabled);
      if(enabled){
        // init stuff that is in use while this state is RUNNING
//        this.app.getRootNode().attachChild(getX()); // modify scene graph...
//        this.app.doSomethingElse();                 // call custom methods...
      } else {
        // take away everything not needed while this state is PAUSED
      }
    }
 
    // Note that update is only called while the state is both attached and enabled.
    @Override
    public void update(float tpf) {
      // do the following while game is RUNNING
//      this.app.getRootNode().getChild("blah").scale(tpf); // modify scene graph...
//      x.setUserData(...);                                 // call some methods...
    }
        
    public void init(AssetManager assetManager, Node rootNode, ViewPort view, InputManager inputManager) {   
        NiftyJmeDisplay niftyDisplay = new NiftyJmeDisplay(assetManager, inputManager, null, view);
        nifty = niftyDisplay.getNifty();
        nifty.fromXml("Interface/GameOverScreen.xml", "gameover", this);
        // attach the nifty display to the gui view port as a processor
        view.addProcessor(niftyDisplay);
        inputManager.setCursorVisible(true);
    }

    public void bind(Nifty nifty, Screen screen) {
        System.out.println("bind( " + screen.getScreenId() + ") - game over");
    }

    public void onStartScreen() {
        System.out.println("onStartScreen - game over");
    }

    public void onEndScreen() {
        System.out.println("onEndScreen - game over");
    }

    public void returnToMainMenu() { 
        MainMenuScreenStateController mainMenuScreenState = new MainMenuScreenStateController(game);
        game.getStateManager().attach(mainMenuScreenState);
        nifty.gotoScreen("start");
        
//        MainMenuScreenStateController mainMenuState = new MainMenuScreenStateController(game);
//        AppStateManager sm = game.getStateManager();
//        if (!sm.hasState(mainMenuState))
//            sm.attach(mainMenuState);
//        sm.detach(this);
    }
}

