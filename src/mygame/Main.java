package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Sphere;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.TextRenderer;
import de.lessvoid.nifty.screen.Screen;

/**
 * test
 * @author normenhansen
 */
public class Main extends SimpleApplication {
public float fieldX =8;
public float fieldRatio = 0.75f;
public float fieldY = fieldX * fieldRatio;
//private Node charNode1;
private boolean leftP1 = false, rightP1 = false, upP1 = false, downP1 = false;
private boolean leftP2 = false, rightP2 = false, upP2 = false, downP2 = false;
private boolean angleLeftP1 = false, angleRightP1 = false, angleLeftP2 = false, angleRightP2 = false;
private BulletAppState bulletAppState;
private Node player1;
BetterCharacterControl character1;
BetterCharacterControl character2;
private Node player2;
private float moveSpeed = 50;
private float shootSpeed = 50;
private int offsetP1 = 0;

private int offsetP2 = 0;
    public static void main(String[] args) {
        Main app = new Main();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        
       // MainMenu m = new MainMenu(assetManager,rootNode,guiViewPort,inputManager);
        
        bulletAppState = new BulletAppState();
        stateManager.attach(bulletAppState);
        setUpKeys();
        
        Node fieldNode = new Node("fieldNode");
        Node fenceNode = new Node("fenceNode");
        Node exitNode = new Node("exit");
        
        flyCam.setMoveSpeed(30);
        
        
        fenceNode.attachChild(createFence());
        
        Node field = createField();
        RigidBodyControl rb = new RigidBodyControl(0);
        field.addControl(rb);
        bulletAppState.getPhysicsSpace().add(field);
        //fieldNode.attachChild(field);
        //createPlayer1();
        
        
        //create Player 1
      
        character1 = new BetterCharacterControl(1f,2,2);
        player1 = (createPlayer(character1,"Player1",new Vector3f(0, 3f, 0)));
        
        //create Player 2
       
        character2 = new BetterCharacterControl(1f,2,2);
        player2 = (createPlayer(character2,"Player2",new Vector3f(0, 3f, 12)));
        
        
        //player2.setLocalTranslation(0, 10, 10);
        //character2.setApplyPhysicsLocal(true);
        //character1.setApplyPhysicsLocal(true);
        exitNode.attachChild(createExit());
        
        rootNode.attachChild(exitNode);
        rootNode.attachChild(fenceNode);
        rootNode.attachChild(field);
        rootNode.attachChild(player1);
        rootNode.attachChild(player2);
        setUpCamera();
    }
    
    public void setOffset(int direction,int life){
        
        if(life != 3){
           // if
        }
        
    }
    
    private ActionListener actionListener = new ActionListener() {
        public void onAction(String name, boolean keyPressed, float tpf) {

            if (name.equals("LeftP1")) {
                if (keyPressed) {
                    leftP1 = true;
                } else {
                    leftP1 = false;
                }
            } else if (name.equals("RightP1")) {
                if (keyPressed) {
                    rightP1 = true;
                } else {
                    rightP1 = false;
                }
            } else if (name.equals("UpP1")) {
                if (keyPressed) {
                    upP1 = true;
                } else {
                    upP1 = false;
                }
            } else if (name.equals("DownP1")) {
                if (keyPressed) {
                    downP1 = true;
                } else {
                    downP1 = false;
                }
            } 
            
            if(name.equals("AimLeftP1")){
                if (keyPressed) {
                    angleLeftP1 = true;
                } else {
                    angleLeftP1 = false;
                }
            }else if (name.equals("AimRightP1")){
                 if (keyPressed) {
                    angleRightP1 = true;
                } else {
                    angleRightP1 = false;
                }
            }
            
           if(name.equals("AimLeftP2")){
                if (keyPressed) {
                    angleLeftP2 = true;
                } else {
                    angleLeftP2 = false;
                }
            }else if (name.equals("AimRightP2")){
                 if (keyPressed) {
                    angleRightP2 = true;
                } else {
                    angleRightP2 = false;
                }
            }
                
                
             if (name.equals("ShootP1") && !keyPressed) {
                 float shotAngle = 0;
                 if(angleLeftP1 == true){
                     shotAngle = -0.5f;
                 }else if(angleRightP1 == true){
                     shotAngle = 0.5f; 
                 }else{
                     shotAngle = 0;
                 }
                 makeShotP1(shotAngle);
             }
             if (name.equals("ShootP2") && !keyPressed) {
                float shotAngle = 0;
                 if(angleLeftP2 == true){
                     shotAngle = -0.5f;
                 }else if(angleRightP2 == true){
                     shotAngle = 0.5f; 
                 }else{
                     shotAngle = 0;
                 }
                 makeShotP2(shotAngle);
             }
             if (name.equals("LeftP2")) {
                if (keyPressed) {
                    leftP2 = true;
                } else {
                    leftP2 = false;
                }
            } else if (name.equals("RightP2")) {
                if (keyPressed) {
                    rightP2 = true;
                } else {
                    rightP2 = false;
                }
            } else if (name.equals("UpP2")) {
                if (keyPressed) {
                    upP2 = true;
                } else {
                    upP2 = false;
                }
            } else if (name.equals("DownP2")) {
                if (keyPressed) {
                    downP2 = true;
                } else {
                    downP2 = false;
                }
            }
        }
    };
    
    private void setUpCamera(){
        flyCam.setEnabled(false);
        cam.setLocation(new Vector3f(0,28,35));
        cam.lookAt(new Vector3f(0,0,10), Vector3f.UNIT_Y);
    }
     private void setUpKeys() {
        inputManager.addMapping("LeftP1", new KeyTrigger(KeyInput.KEY_A));
        inputManager.addMapping("RightP1", new KeyTrigger(KeyInput.KEY_D));
        inputManager.addMapping("UpP1", new KeyTrigger(KeyInput.KEY_W));
        inputManager.addMapping("DownP1", new KeyTrigger(KeyInput.KEY_S));
        inputManager.addMapping("ShootP1", new KeyTrigger(KeyInput.KEY_SPACE));
        inputManager.addMapping("AimLeftP1", new KeyTrigger(KeyInput.KEY_Q));
        inputManager.addMapping("AimRightP1", new KeyTrigger(KeyInput.KEY_E));
        inputManager.addMapping("LeftP2", new KeyTrigger(KeyInput.KEY_NUMPAD4));
        inputManager.addMapping("RightP2", new KeyTrigger(KeyInput.KEY_NUMPAD6));
        inputManager.addMapping("UpP2", new KeyTrigger(KeyInput.KEY_NUMPAD8));
        inputManager.addMapping("DownP2", new KeyTrigger(KeyInput.KEY_NUMPAD5));
        inputManager.addMapping("ShootP2", new KeyTrigger(KeyInput.KEY_NUMPAD0));
        inputManager.addMapping("AimLeftP2", new KeyTrigger(KeyInput.KEY_NUMPAD7));
        inputManager.addMapping("AimRightP2", new KeyTrigger(KeyInput.KEY_NUMPAD9));

        inputManager.addListener(actionListener, "LeftP1");
        inputManager.addListener(actionListener, "RightP1");
        inputManager.addListener(actionListener, "UpP1");
        inputManager.addListener(actionListener, "DownP1");
        inputManager.addListener(actionListener, "ShootP1");
        inputManager.addListener(actionListener, "AimLeftP1");
        inputManager.addListener(actionListener, "AimRightP1");
        inputManager.addListener(actionListener, "LeftP2");
        inputManager.addListener(actionListener, "RightP2");
        inputManager.addListener(actionListener, "UpP2");
        inputManager.addListener(actionListener, "DownP2");
        inputManager.addListener(actionListener, "ShootP2");
        inputManager.addListener(actionListener, "AimLeftP2");
        inputManager.addListener(actionListener, "AimRightP2");

    }
    public Node createPlayer(BetterCharacterControl charC, String playerName, Vector3f location){
 
        
        Node charNode1 = new Node();
        charNode1.setName("Node_" + playerName);
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Pink);
       
        Geometry model2 = createBox(1, 1, 1, playerName);

        model2.setMaterial(mat);
        model2.setLocalTranslation(0,1,0);
        charNode1.setLocalTranslation(location);
        
        charNode1.attachChild(model2);
        charNode1.addControl(charC);
        charNode1.setUserData("health", 3);
        bulletAppState.getPhysicsSpace().add(charNode1);

      return( charNode1);
    }
    
    public Geometry createBox(float sizeX, float sizeY, float sizeZ, String name){
        Box b = new Box(sizeX, sizeY, sizeZ);
        Geometry geom = new Geometry(name,b);
        return geom;
        
    }
    public Node createField(){
        Node field = new Node("field");
        Geometry fieldBottomPlayer1 = createBox(fieldX,1,fieldY,"Field_p1");
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Blue);
        fieldBottomPlayer1.setMaterial(mat);
        
        field.attachChild(fieldBottomPlayer1);
        
        Geometry fieldBottomPlayer2 = fieldBottomPlayer1.clone();
        
        fieldBottomPlayer2.setLocalTranslation(0, 0, fieldY*2+fieldY/1.5f);
        Material mat2 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat2.setColor("Color", ColorRGBA.Red);
         fieldBottomPlayer2.setMaterial(mat2);
        field.attachChild(fieldBottomPlayer2);
        
        return field;
    }
    
    public Node createExit(){
        Node exit = new Node();
        Node exitLeft = new Node();
        Node exitRight = new Node();
        Node exitFront = new Node();
        Node exitBack = new Node();
        
        Geometry fenceGeom = createBox(1,1,2*fieldY+fieldY/3+4,"Block");
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Orange);
        fenceGeom.setMaterial(mat);
        fenceGeom.setLocalTranslation(fieldX+3f, 2, 8);
        exitLeft.attachChild(fenceGeom);
        Geometry fenceGeomRight = fenceGeom.clone();
        fenceGeomRight.setLocalTranslation(-fieldX-3f, 2, 8);
        exitRight.attachChild(fenceGeomRight);
        exit.attachChild(exitLeft);
        exit.attachChild(exitRight);
        
        Geometry fenceGeomFront = createBox(fieldX+fieldX/3+1,1,1,"Block");
        fenceGeomFront.setLocalTranslation(0, 2, -fieldY-3f);
        fenceGeomFront.setMaterial(mat);
        exitFront.attachChild(fenceGeomFront);
        exit.attachChild(exitFront);
        
        Geometry fenceGeomBack = fenceGeomFront.clone();
        fenceGeomFront.setLocalTranslation(0, 2, 2*(fieldY)+13);
        exitBack.attachChild(fenceGeomBack);
        exit.attachChild(exitBack);
        return exit;
        
    }
    public Node createFence(){
        Node fence = new Node("fence");
        Node fenceLeft = new Node("fenceLeft");
        Node fenceRight = new Node("fenceRight");
        for (int j = 0; j<2;j++){
                   
            for (int i = 0;i<2*fieldY+fieldY/3+2;i++){
                Geometry fenceGeom = createBox(1,1,1,"Block");
                fenceGeom.setLocalTranslation(0,j*2, 2*i-2);
                
                
                Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
                mat.setColor("Color", ColorRGBA.Green);
                Material mat2 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
                mat2.setColor("Color", ColorRGBA.Yellow);
                if(i%2==0){
                    fenceGeom.setMaterial(mat2);
                }else{
                    fenceGeom.setMaterial(mat);      
                }
                fenceLeft.attachChild(fenceGeom);
                //fence.setMaterial(mat);
            }
            fenceRight = (Node) fenceLeft.clone();
            
            fenceLeft.setLocalTranslation(fieldX+1, 0, -fieldY+1);
            fenceRight.setLocalTranslation(-fieldX-1, 0, -fieldY+1);
            fence.attachChild(fenceLeft);
            fence.attachChild(fenceRight);
        }
         return fence;
    }
    
    
    
    public void createPlayer2(){
        
    }

    @Override
    public void simpleUpdate(float tpf) {
       // flyCam.setEnabled(true);
        
        Vector3f walkDirectionP1 = new Vector3f();
        walkDirectionP1.set(0, 0, 0);
        Vector3f walkDirectionP2 = new Vector3f();
        walkDirectionP2.set(0, 0, 0);
        
        if (leftP1) {
            walkDirectionP1.addLocal(new Vector3f (-10,0,0));
        }
        if (leftP2) {
            walkDirectionP2.addLocal(new Vector3f (-10,0,0));
        }
        if (rightP1) {
            walkDirectionP1.addLocal(new Vector3f (10,0,0));
        }
        if (rightP2) {
            walkDirectionP2.addLocal(new Vector3f (10,0,0));
        }
        if (upP1) {
            //walkDirectionP1.addLocal(new Vector3f (0,0,-10));
           // character1.warp();
        }
        if (upP2) {
            //walkDirectionP2.addLocal(new Vector3f (0,0,-10));
        }
        if (downP1) {
            //walkDirectionP1.addLocal(new Vector3f (0,0,10));
        }
         if (downP2) {
            //walkDirectionP2.addLocal(new Vector3f (0,0,10));
        }
        
            character1.setWalkDirection(walkDirectionP1);
            walkDirectionP1.multLocal( 1000*moveSpeed).multLocal(tpf);
            character2.setWalkDirection(walkDirectionP2);
            walkDirectionP2.multLocal( 1000*moveSpeed).multLocal(tpf);  
              
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
    
    public void makeShotP1(float shotAngle) {
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.White);
          Sphere sphere = new Sphere(10, 10, 0.5f, true, false);
       // sphere.setTextureMode(Sphere.TextureMode.Projected);
        Geometry ball_geo = new Geometry("cannon ball", sphere);
        ball_geo.setMaterial(mat);
        rootNode.attachChild(ball_geo);

        ball_geo.setLocalTranslation(player1.getWorldTranslation().addLocal(0, 1f, 0.5f));

       RigidBodyControl ball_phy = new RigidBodyControl(0.1f);

        ball_geo.addControl(ball_phy);
        bulletAppState.getPhysicsSpace().add(ball_phy);
        Vector3f shootDirection = new Vector3f(shotAngle,0,1);
        ball_phy.setLinearVelocity(shootDirection.mult(shootSpeed));

    }
    
        public void makeShotP2() {
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.White);
          Sphere sphere = new Sphere(10, 10, 0.5f, true, false);
       // sphere.setTextureMode(Sphere.TextureMode.Projected);
        Geometry ball_geo = new Geometry("cannon ball", sphere);
        ball_geo.setMaterial(mat);
        rootNode.attachChild(ball_geo);

        ball_geo.setLocalTranslation(player2.getWorldTranslation().addLocal(0, 1f, -0.5f));

       RigidBodyControl ball_phy = new RigidBodyControl(0.1f);

        ball_geo.addControl(ball_phy);
        bulletAppState.getPhysicsSpace().add(ball_phy);
        Vector3f shootDirection = new Vector3f(0,0,-1);
        ball_phy.setLinearVelocity(shootDirection.mult(shootSpeed));

    }
    
    
    public int getUserHealth(Node playerNode){
        return Integer.parseInt("" + playerNode.getUserData("health"));
    }
    
    public void setUserHealth(Node playerNode,int value){
        playerNode.setUserData("health", value);
    }
    
    public void incHealth(Node playerNode){
        setUserHealth(playerNode,getUserHealth(playerNode)+1);
    }
    
    public void decHealth(Node playerNode){
        setUserHealth(playerNode,getUserHealth(playerNode)-1);
    }
  /*  public void setLifePanelText() {
        Screen screen = nifty.getCurrentScreen();
        Element layer = screen.findElementByName("LifePanel");
        layer.getRenderer(TextRenderer.class).setText("Lifepoints " + player1.getUserData("health"));
        nifty.update();
    }*/
}
