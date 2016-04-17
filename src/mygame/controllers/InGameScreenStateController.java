/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.controllers;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.input.InputManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import mygame.Game;
import mygame.Main;

/**
 *
 * @author bub
 */
public class InGameScreenStateController extends AbstractAppState implements ScreenController {
    private Nifty nifty;
    private Screen screen;
    
    private AssetManager assetManager;
    private Node rootNode;
    private ViewPort guiViewPort;
    private InputManager inputManager;
    private SimpleApplication game;
    private Game theGame;
    
//    public InGameScreenStateController(SimpleApplication app) {
    public InGameScreenStateController(SimpleApplication app, Game newGame) {
        assetManager = app.getAssetManager();
        rootNode = app.getRootNode();
        guiViewPort = app.getViewPort();
        inputManager = app.getInputManager();
        game = app;
        theGame = newGame;
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
    }
 
    @Override
    public void setEnabled(boolean enabled) {
      // Pause and unpause
      super.setEnabled(enabled);
      if(enabled){
          
      } else {

      }
    }
 
    // Note that update is only called while the state is both attached and enabled.
    @Override
    public void update(float tpf) {
        theGame.simpleUpdate(tpf);
    }
        
    public void init(AssetManager assetManager, Node rootNode, ViewPort view, InputManager inputManager) {
        nifty.fromXml("Interface/InGameScreen.xml", "ingame", this);
        inputManager.setCursorVisible(true);
    }

    public void bind(Nifty nifty, Screen screen) {
        this.nifty = nifty;
        this.screen = screen;
        System.out.println("bind( " + screen.getScreenId() + ") - in game");
    }

    public void onStartScreen() {
        System.out.println("onStartScreen - in game");
    }

    public void onEndScreen() {
        System.out.println("onEndScreen - in game");
    }

    public void returnToMainMenu() {
        MainMenuScreenStateController mainMenuScreenState = new MainMenuScreenStateController(game);
        game.getStateManager().attach(mainMenuScreenState);
        nifty.gotoScreen("start");
    }
}