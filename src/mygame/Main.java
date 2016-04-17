package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.TextureKey;
import com.jme3.audio.AudioNode;
import com.jme3.bounding.BoundingVolume;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.collision.CollisionResults;
import com.jme3.effect.ParticleEmitter;
import com.jme3.effect.ParticleMesh;
import com.jme3.effect.shapes.EmitterSphereShape;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Sphere;
import com.jme3.texture.Texture;
import com.jme3.texture.Texture.WrapMode;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.TextRenderer;
import de.lessvoid.nifty.screen.Screen;
import static java.lang.Thread.sleep;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * test
 *
 * @author normenhansen
 */
public class Main extends SimpleApplication {
    private Nifty nifty;
    public float fieldX = 8;
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
    private Node powerUpNode;
    private float moveSpeed = 50;
    private float shootSpeed = 50;
    private float specialSpeed = 1f;
    private int offsetP1 = 0;
    private boolean shootPauseP1 = false;
    private long timeP1 = 0;
    private boolean shootPauseP2 = false;
    private long timeP2 = 0;
    private int offsetP2 = 0;
    private int shootFrequency = 500;
    private Node shot = new Node("Shot");
    private Node fenceNode = new Node("fenceNode");
    //sound
    private AudioNode shootSoundP1;
    private AudioNode shootSoundP2;
    private AudioNode collision;
    private AudioNode powerUp;
    private AudioNode menuSound;
    private AudioNode gameSound;
    private AudioNode hitSound;
    private int playerLife = 3;
    private Node deathNode = new Node("death");
    private Node field1Pl1;
    private Node field2Pl1;
    private Node field3Pl1;
    private Node field1Pl2;
    private Node field2Pl2;
    private Node field3Pl2;
    private int random1;
    private int random3;
    private int random2;
    private Geometry specialGeom;
    private Boolean specialBoolP1 = false;
    private Boolean specialBoolP2 = false;
    private float specialSpeedChar = 1f;
    private long specialTimeP1;
    private long specialTimeP2;
    private long powerUpTime;
    long powerUpDownTime;
    private String shootStr = "Shoot";
    ParticleEmitter fire;
    pYListener pY;

    public static void main(String[] args) {
        Main app = new Main();
        app.start();
    }

    public void resetPlayer() {
        character1.warp(new Vector3f(3, 2f, -2));
        character2.warp(new Vector3f(-3, 2f, 18));
    }

    public void removeAll(Node removeNode) {
        System.out.println("remove");
        removeNode.removeControl(RigidBodyControl.class);
        bulletAppState.getPhysicsSpace().remove(removeNode);

        for (int i = 0; i < removeNode.getChildren().size(); i++) {
            removeAll((Node) removeNode.getChild(i));
        }
        removeNode.removeFromParent();
    }

    public void removeRigid(Node removeNode) {
        // removeNode.removeControl(RigidBodyControl.class);
        bulletAppState.getPhysicsSpace().remove(removeNode);
    }

    public void removeRigid(Geometry removeNode) {
        // removeNode.removeControl(RigidBodyControl.class);
        bulletAppState.getPhysicsSpace().remove(removeNode);
    }

    public void removeAll(Geometry removeNode) {

        bulletAppState.getPhysicsSpace().remove(removeNode);
        removeNode.removeControl(RigidBodyControl.class);

        removeNode.removeFromParent();
    }

    public void buildBase(Node playerNode) {
        if (getUserHealth(playerNode) == 3) {
            if (playerNode.getName().equals("Node_Player1") == true) {

                if ((field2Pl2).getParent() != null) {
                    System.out.println("jj");
                    removeAll(field2Pl2);
                }
                if ((field2Pl2).getParent() == null && (field2Pl2.getControl(RigidBodyControl.class) != null)) {
                    removeRigid(field2Pl2);
                }
                if ((field3Pl2).getParent() != null) {
                    removeAll(field3Pl2);
                }
                if ((field3Pl2).getParent() == null && (field3Pl2.getControl(RigidBodyControl.class) != null)) {
                    removeRigid(field3Pl2);
                }
            } else {

                if ((field2Pl1).getParent() != null) {
                    removeAll(field2Pl1);
                }
                if ((field2Pl1).getParent() == null && (field2Pl1.getControl(RigidBodyControl.class) != null)) {
                    removeRigid(field2Pl1);
                }
                if ((field3Pl1).getParent() != null) {
                    removeAll(field3Pl1);
                }
                if ((field3Pl1).getParent() == null && (field3Pl1.getControl(RigidBodyControl.class) != null)) {
                    removeRigid(field3Pl1);
                }
            }
        } else if (getUserHealth(playerNode) == 2) {
            if (playerNode.getName().equals("Node_Player1") == true) {
                
                if ((field2Pl2).getParent() == null) {
                    System.out.println("dazu");
                    RigidBodyControl rb = new RigidBodyControl(0);
                    field2Pl2.addControl(rb);
                    bulletAppState.getPhysicsSpace().add(field2Pl2);
                    rootNode.attachChild(field2Pl2);
                }
                if ((field3Pl2).getParent() != null) {
                    removeAll(field3Pl2);
                }
                if ((field3Pl2).getParent() == null && (field3Pl2.getControl(RigidBodyControl.class) != null)) {
                    removeRigid(field3Pl2);
                }
            } else {

                if ((field2Pl1).getParent() == null) {
                    RigidBodyControl rb = new RigidBodyControl(0);
                    field2Pl1.addControl(rb);
                    bulletAppState.getPhysicsSpace().add(field2Pl1);
                    rootNode.attachChild(field2Pl1);
                }
                if ((field3Pl1).getParent() != null) {
                    removeAll(field3Pl1);
                }
                if ((field3Pl1).getParent() == null && (field3Pl1.getControl(RigidBodyControl.class) != null)) {
                    removeRigid(field3Pl1);
                }
            }
        } else {
            if (playerNode.getName().equals("Node_Player1") == true) {

                if ((field2Pl2).getParent() == null) {
                    RigidBodyControl rb = new RigidBodyControl(0);
                    field2Pl2.addControl(rb);
                    bulletAppState.getPhysicsSpace().add(field2Pl2);
                    rootNode.attachChild(field2Pl2);
                }
                if ((field3Pl2).getParent() == null) {
                    RigidBodyControl rb = new RigidBodyControl(0);
                    field3Pl2.addControl(rb);
                    bulletAppState.getPhysicsSpace().add(field3Pl2);
                    rootNode.attachChild(field3Pl2);
                }
            } else {

                if ((field2Pl1).getParent() == null) {
                    RigidBodyControl rb = new RigidBodyControl(0);
                    field2Pl1.addControl(rb);
                    bulletAppState.getPhysicsSpace().add(field2Pl1);
                    rootNode.attachChild(field2Pl1);
                }
                if ((field3Pl1).getParent() == null) {
                    RigidBodyControl rb = new RigidBodyControl(0);
                    field3Pl1.addControl(rb);
                    bulletAppState.getPhysicsSpace().add(field3Pl1);
                    rootNode.attachChild(field3Pl1);
                }
            }
        }

    }

    @Override
    public void simpleInitApp() {
       
        powerUpNode = new Node("PowerUpNode");
        rootNode.attachChild(powerUpNode);
        random1 = (int) (Math.random() * 10) % 3;
        random2 = (int) (Math.random() * 10) % 3;
        random3 = (int) (Math.random() * 10) % 3;
        // MainMenu m = new MainMenu(assetManager,rootNode,guiViewPort,inputManager);
        rootNode.attachChild(shot);
        bulletAppState = new BulletAppState();
        stateManager.attach(bulletAppState);

        setUpKeys();

        Node fieldNode = new Node("fieldNode");

        Node exitNode = new Node("exit");

        flyCam.setMoveSpeed(30);


        fenceNode.attachChild(createFence());
        field1Pl1 = createField(0, 0, 20);
        field2Pl1 = createField(0, 0, 16);
        field3Pl1 = createField(0, 0, 12);
        field1Pl2 = createField(0, 0, -4);
        field2Pl2 = createField(0, 0, 0);
        field3Pl2 = createField(0, 0, 4);



        //fieldNode.attachChild(field);
        //createPlayer1();


        //create Player 1

        character1 = new BetterCharacterControl(1f, 2, 100);
        character1.setGravity(new Vector3f(0, 10000, 0));
        player1 = (createPlayer(character1, "Player1", new Vector3f(0, 3f, 0)));

        //create Player 2

        character2 = new BetterCharacterControl(1f, 2, 100);
        player2 = (createPlayer(character2, "Player2", new Vector3f(0, 3f, 12)));
        pY = new pYListener(bulletAppState, rootNode, character1, character2, field1Pl1, field2Pl1, field3Pl1, field1Pl2, field2Pl2, field3Pl2, assetManager, specialBoolP1, specialBoolP2);

        shot.addControl(pY);

        //player2.setLocalTranslation(0, 10, 10);
        //character2.setApplyPhysicsLocal(true);
        //character1.setApplyPhysicsLocal(true);
        // exitNode.attachChild(createExit());

        rootNode.attachChild(exitNode);
        rootNode.attachChild(fenceNode);
        rootNode.attachChild(field1Pl1);

        rootNode.attachChild(field1Pl2);

        //  rootNode.attachChild(createStopper(0,,0));
        rootNode.attachChild(player1);
        rootNode.attachChild(player2);
        deathNode = createDeath();
        rootNode.attachChild(deathNode);
        buildBase(player1);
        buildBase(player2);
        // sounds  Menu Sound could also be game sound
        shootSoundP1 = new AudioNode(assetManager, "Sounds/shootplayer1.wav", false);
        rootNode.attachChild(shootSoundP1);

        shootSoundP2 = new AudioNode(assetManager, "Sounds/shootplayer2.wav", false);
        rootNode.attachChild(shootSoundP2);

        menuSound = new AudioNode(assetManager, "Sounds/MenuSound.wav", false);
        menuSound.setLooping(true);
        rootNode.attachChild(menuSound);

        hitSound = new AudioNode(assetManager, "Sounds/Hit_Hurt78.wav", false);
        rootNode.attachChild(hitSound);

        collision = new AudioNode(assetManager, "Sounds/collision.wav", false);
        rootNode.attachChild(collision);

        gameSound = new AudioNode(assetManager, "Sounds/gameSoundBackground.wav", false);
        gameSound.setLooping(true);
        rootNode.attachChild(gameSound);

        powerUp = new AudioNode(assetManager, "Sounds/powerup.wav", false);
        rootNode.attachChild(shootSoundP1);

        gameSound.play();
        if (random3 == 0) {
            emitterTest();
        }
        setUpCamera();
        resetPlayer();
        spawnPowerUp();
        powerUpDownTime = System.currentTimeMillis();
        
        
        Spatial model = assetManager.loadModel("Models/model.max"); 
        
        rootNode.attachChild(model);
    }

    public void setOffset(int direction, int life) {

        if (life != 3) {
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

            if (name.equals("AimLeftP1")) {
                if (keyPressed) {
                    angleLeftP1 = true;
                } else {
                    angleLeftP1 = false;
                }
            } else if (name.equals("AimRightP1")) {
                if (keyPressed) {
                    angleRightP1 = true;
                } else {
                    angleRightP1 = false;
                }
            }

            if (name.equals("AimLeftP2")) {
                if (keyPressed) {
                    angleLeftP2 = true;
                } else {
                    angleLeftP2 = false;
                }
            } else if (name.equals("AimRightP2")) {
                if (keyPressed) {
                    angleRightP2 = true;
                } else {
                    angleRightP2 = false;
                }
            }


            if (name.equals(shootStr+"P1") && !keyPressed) {
                float shotAngle = 0;
                if (angleLeftP1 == true) {
                    shotAngle = -0.5f;
                } else if (angleRightP1 == true) {
                    shotAngle = 0.5f;
                } else {
                    shotAngle = 0;
                }
                if (shootPauseP1 == false) {
                    //specialBoolP1 = true;
                    Geometry bullet = makeShotP1(shotAngle);

                    if (pY.getSpecialP1() == true) {

                        specialGeom = (bullet);
                    }
                    shootPauseP1 = true;

                    timeP1 = System.currentTimeMillis();
                }
            }

            if (name.equals(shootStr+"P2") && !keyPressed) {
                float shotAngle = 0;
                if (angleLeftP2 == true) {
                    shotAngle = -0.5f;
                } else if (angleRightP2 == true) {
                    shotAngle = 0.5f;
                } else {
                    shotAngle = 0;
                }
                if (shootPauseP2 == false) {
                    Geometry bullet = makeShotP2(shotAngle);
                    System.out.println(pY.getSpecialP2());
                    if (pY.getSpecialP2() == true) {
                        specialGeom = (bullet);
                    }
                    shootPauseP2 = true;

                    timeP2 = System.currentTimeMillis();
                }
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

    private void setUpCamera() {
        flyCam.setEnabled(false);
        cam.setLocation(new Vector3f(0, 28, 35));
        cam.lookAt(new Vector3f(0, 0, 10), Vector3f.UNIT_Y);
    }

    public void spawnPowerUp() {
        powerUpTime = System.currentTimeMillis();
        int randomPl1 = (int) ((Math.random() * 6) + 1);
        int randomXPl1 = (int) ((Math.random() * fieldX * 2 - 2) + 1);
        int randomPl2 = (int) ((Math.random() * 6) + 1);
        int randomXPl2 = (int) ((Math.random() * fieldX * 2 - 2) + 1);
        createPowerUp(randomXPl1 - fieldX + 1, 1.5f, -8, randomPl1);
        createPowerUp(randomXPl2 - fieldX + 1, 1.5f, 24, randomPl2);
    }

    public void checkPowerUpTime() {
        long timer = System.currentTimeMillis() - powerUpTime;
        if (timer > 2000) {
            int size = powerUpNode.getChildren().size();
            for (int i = 0; i < size; i++) {

                removeAll((Geometry) powerUpNode.getChild(0));
            }
            //powerUpDownTime = System.currentTimeMillis();
            powerUpSpawnTimer();
        }

    }

    public void powerUpSpawnTimer() {
        long timer = System.currentTimeMillis() - powerUpDownTime;

        if (timer > 2000) {
            spawnPowerUp();
            powerUpDownTime = System.currentTimeMillis();
        }
    }

    public void createPowerUp(float x, float z, float y, int powerUpInt) {
        String powerUpName = "";
        switch (powerUpInt) {
            case 1:
                powerUpName = "Power1";
                break;

            case 2:
                powerUpName = "Power2";
                break;

            case 3:
                powerUpName = "Power3";
                break;

            case 4:
                powerUpName = "Power4";
                break;

            case 5:
                powerUpName = "Power5";
                break;
            case 6:
                powerUpName = "Power6";
                break;

        }
        Geometry powerUp = createBox(0.5f, 0.5f, 0.5f, powerUpName);
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Pink);
        powerUp.setMaterial(mat);
        powerUp.setName(powerUpName);
        powerUp.setLocalTranslation(x, z, y);
        RigidBodyControl rb = new RigidBodyControl(0);
        powerUp.addControl(rb);
        bulletAppState.getPhysicsSpace().add(powerUp);
        powerUpNode.attachChild(powerUp);
    }

    private void setUpSpecialCamera(Geometry followNode) {
        
       shootStr = "";
        specialSpeed = 0.1f;
        specialSpeedChar = 0.03f;
        cam.setLocation(new Vector3f(0, 35, 0));

        cam.lookAt(followNode.getWorldTranslation(), Vector3f.UNIT_Y);
        long timeP1 = System.currentTimeMillis() - specialTimeP1;
        long timeP2 = System.currentTimeMillis() - specialTimeP2;
        int timer = 5000;
        String name = followNode.getName();

        int playerNr = Integer.parseInt(name.substring(name.length() - 1, name.length()));


        if (followNode.getParent() == null || (playerNr == 1 && (timeP1 > timer)) || (playerNr == 2 && (timeP2 > timer))) {
              shootPauseP1 = false;
        shootPauseP2 = false;
            setUpCamera();
            if (playerNr == 1) {
                pY.setSpecialP1(false);
            }
            if (playerNr == 2) {
                pY.setSpecialP2(false);
            }
            specialSpeed = 1f;
            specialSpeedChar = 1f;
             shootStr = "Shoot";
            //specialTimeP1 = 0;
            //specialTimeP2= 0;
           specialGeom= null;
        }
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

    public Node createPlayer(BetterCharacterControl charC, String playerName, Vector3f location) {


        Node charNode1 = new Node();
        charNode1.setName("Node_" + playerName);
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Pink);

        Geometry model2 = createBox(1, 1, 1, playerName);

        model2.setMaterial(mat);
        model2.setLocalTranslation(0, 1, 0);
        charNode1.setLocalTranslation(location);

        charNode1.attachChild(model2);
        charNode1.addControl(charC);
        charNode1.setUserData("health", playerLife);
        RigidBodyControl rb = new RigidBodyControl(1);
        charNode1.addControl(rb);
        bulletAppState.getPhysicsSpace().add(charNode1);

        return (charNode1);
    }

    public Geometry createBox(float sizeX, float sizeY, float sizeZ, String name) {
        Box b = new Box(sizeX, sizeY, sizeZ);
        Geometry geom = new Geometry(name, b);
        return geom;

    }

    public Node createDeath() {
        Node field = new Node("death");
        Geometry fieldBottomPlayer1 = createBox(fieldX * 5, 0.5f, fieldY * 5, "field_death");
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Black);
        fieldBottomPlayer1.setMaterial(mat);
        RigidBodyControl rb = new RigidBodyControl(0);
        fieldBottomPlayer1.addControl(rb);
        bulletAppState.getPhysicsSpace().add(fieldBottomPlayer1);
        fieldBottomPlayer1.setLocalTranslation(0, -5, 0);

        field.attachChild(fieldBottomPlayer1);
        return field;
    }

    public Node createStopper(float x, float z, float y) {
        Node field = new Node("field");
        Geometry fieldBottomPlayer1 = createBox(fieldX, 0.2f, 0.2f, "stopper");
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");

        Material tex_mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        TextureKey key = new TextureKey("Textures/gras.png");
        key.setGenerateMips(true);
        Texture tex2 = assetManager.loadTexture(key);
        tex_mat.setTexture("ColorMap", tex2);
        tex2.setWrap(WrapMode.Repeat);
        fieldBottomPlayer1.getMesh().scaleTextureCoordinates(new Vector2f(1, 4));
        //tex_ma
        //TODO TEXTURE
        //tex2.setWrap(Texture.WrapAxis., Texture.WrapMode.Repeat);
        mat.setColor("Color", ColorRGBA.Blue);
        fieldBottomPlayer1.setMaterial(tex_mat);
        fieldBottomPlayer1.setLocalTranslation(x, z, y);
        field.attachChild(fieldBottomPlayer1);
        RigidBodyControl rb = new RigidBodyControl(0);
        field.addControl(rb);
        bulletAppState.getPhysicsSpace().add(field);
        /*Geometry fieldBottomPlayer2 = fieldBottomPlayer1.clone();

         fieldBottomPlayer2.setLocalTranslation(x,z,y);
         Material mat2 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
         mat2.setColor("Color", ColorRGBA.Red);
         fieldBottomPlayer2.setMaterial(mat2);
         field.attachChild(fieldBottomPlayer2);
         * */

        return field;
    }

    public void emitterTest() {
        fire = new ParticleEmitter("Emitter", ParticleMesh.Type.Triangle, 1000);
        Material mat_red = new Material(assetManager, "Common/MatDefs/Misc/Particle.j3md");
        fire.setShape(new EmitterSphereShape(Vector3f.ZERO, 25f));
        mat_red.setTexture("Texture", assetManager.loadTexture("Textures/raindrop3.png"));
        fire.setMaterial(mat_red);
        fire.setImagesX(1);
        fire.setImagesY(1); // 2x2 texture animation
        fire.setEndColor(new ColorRGBA(1f, 1f, 1f, 1f));   // red
        fire.setStartColor(new ColorRGBA(1f, 1f, 1f, 1f)); // yellow
        fire.getParticleInfluencer().setInitialVelocity(new Vector3f(0, 0, 0));
        fire.setStartSize(0.1f);
        fire.setEndSize(0.1f);
        fire.setGravity(0, 100f, 0);
        fire.setLowLife(0.9f);
        fire.setHighLife(2f);
        fire.setFacingVelocity(false);
        fire.setRotateSpeed(0.0f);
//points.setFaceNormal(new Vector3f(0,0,1));

        fire.setParticlesPerSec(800000);
//fire.setShadowMode(ShadowMode.CastAndReceive);
        //fire.getParticleInfluencer().setVelocityVariation(0.3f);
        rootNode.attachChild(fire);

        fire.updateLogicalState(0);

        fire.updateGeometricState();

    }

    public Node createField(float x, float z, float y) {
        Node field = new Node("field");
        Geometry fieldBottomPlayer1 = createBox(fieldX, 1, fieldY / 3, "Field_p1");
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");

        Material tex_mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        String str = "";
        switch (random1) {
            case 0:
                str = "gras";
                break;
            case 1:
                str = "schild";
                break;
            case 2:
                str = "ziegel";
                break;
        }
        TextureKey key = new TextureKey("Textures/" + str + ".png");
        key.setGenerateMips(true);
        Texture tex2 = assetManager.loadTexture(key);
        tex_mat.setTexture("ColorMap", tex2);
        tex2.setWrap(WrapMode.Repeat);
        fieldBottomPlayer1.getMesh().scaleTextureCoordinates(new Vector2f(1, 4));
        //tex_ma
        //TODO TEXTURE
        //tex2.setWrap(Texture.WrapAxis., Texture.WrapMode.Repeat);
        mat.setColor("Color", ColorRGBA.Blue);
        fieldBottomPlayer1.setMaterial(tex_mat);
        fieldBottomPlayer1.setLocalTranslation(x, z, y);
        field.attachChild(fieldBottomPlayer1);
        RigidBodyControl rb = new RigidBodyControl(0);
        field.addControl(rb);
        bulletAppState.getPhysicsSpace().add(field);
        /*Geometry fieldBottomPlayer2 = fieldBottomPlayer1.clone();

         fieldBottomPlayer2.setLocalTranslation(x,z,y);
         Material mat2 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
         mat2.setColor("Color", ColorRGBA.Red);
         fieldBottomPlayer2.setMaterial(mat2);
         field.attachChild(fieldBottomPlayer2);
         * */

        return field;
    }

    public Node createExit() {
        Node exit = new Node("Block");
        Node exitLeft = new Node();
        Node exitRight = new Node();
        Node exitFront = new Node();
        Node exitBack = new Node();

        Geometry fenceGeom = createBox(1, 1, 2 * fieldY + fieldY / 3 + 4, "Block");
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Orange);
        fenceGeom.setMaterial(mat);
        fenceGeom.setLocalTranslation(fieldX + 3f, 2, 8);
        exitLeft.attachChild(fenceGeom);
        Geometry fenceGeomRight = fenceGeom.clone();
        fenceGeomRight.setLocalTranslation(-fieldX - 3f, 2, 8);
        exitRight.attachChild(fenceGeomRight);
        exit.attachChild(exitLeft);
        exit.attachChild(exitRight);

        Geometry fenceGeomFront = createBox(fieldX + fieldX / 3 + 1, 1, 1, "Block");
        fenceGeomFront.setLocalTranslation(0, 2, -fieldY - 3f);
        fenceGeomFront.setMaterial(mat);
        exitFront.attachChild(fenceGeomFront);
        exit.attachChild(exitFront);

        Geometry fenceGeomBack = fenceGeomFront.clone();
        fenceGeomFront.setLocalTranslation(0, 2, 2 * (fieldY) + 13);
        exitBack.attachChild(fenceGeomBack);
        exit.attachChild(exitBack);
        return exit;

    }

    public Node createFence() {
        Node fence = new Node("fence");
        Node fenceLeft = new Node("fenceLeft");
        Node fenceRight = new Node("fenceRight");

        Material tex_mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        String str = "";
        switch (random1) {
            case 0:
                str = "ziegel";
                break;
            case 1:
                str = "schild";
                break;
            case 2:
                str = "gras";
                break;
        }
        TextureKey key = new TextureKey("Textures/" + str + ".png");
        key.setGenerateMips(true);
        Texture tex2 = assetManager.loadTexture(key);
        tex_mat.setTexture("ColorMap", tex2);
        tex2.setWrap(WrapMode.Repeat);


        for (int j = 0; j < 2; j++) {

            for (int i = 0; i < 2 * fieldY + fieldY / 3 + 2; i++) {

                Geometry fenceGeom = createBox(1, 1, 1, "BlockG");
                fenceGeom.setLocalTranslation(fieldX + 1, j * 2, 2 * i - 2 - fieldY + 1);


                //Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
                //mat.setColor("Color", ColorRGBA.Green);
                // Material mat2 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
                //mat2.setColor("Color", ColorRGBA.Yellow);
                //if (i % 2 == 0) {
                // fenceGeom.setMaterial(mat2);
                //} else {
                fenceGeom.setMaterial(tex_mat);
                fenceGeom.getMesh().scaleTextureCoordinates(new Vector2f(1, 1));
                fenceGeom.setUserData("health", 3);
                //}
                fenceLeft.attachChild(fenceGeom);
                RigidBodyControl rb = new RigidBodyControl(0);
                fenceGeom.addControl(rb);
                bulletAppState.getPhysicsSpace().add(fenceGeom);
                //fence.setMaterial(mat);
            }
        }

        for (int j = 0; j < 2; j++) {

            for (int i = 0; i < 2 * fieldY + fieldY / 3 + 2; i++) {

                Geometry fenceGeom = createBox(1, 1, 1, "BlockG");
                fenceGeom.setLocalTranslation(-fieldX - 1, j * 2, 2 * i + -2 - fieldY + 1);


                //Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
                //mat.setColor("Color", ColorRGBA.Green);
                // Material mat2 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
                //mat2.setColor("Color", ColorRGBA.Yellow);
                //if (i % 2 == 0) {
                // fenceGeom.setMaterial(mat2);
                //} else {
                fenceGeom.setMaterial(tex_mat);
                fenceGeom.getMesh().scaleTextureCoordinates(new Vector2f(1, 1));
                fenceGeom.setUserData("health", 3);
                //}
                fenceRight.attachChild(fenceGeom);
                RigidBodyControl rb = new RigidBodyControl(0);
                fenceGeom.addControl(rb);
                bulletAppState.getPhysicsSpace().add(fenceGeom);
                //fence.setMaterial(mat);
            }
        }
        // fenceLeft.setLocalTranslation(fieldX + 1, 0, -fieldY + 1);


        // fenceRight = (Node) fenceLeft.clone();
        //  fenceRight.setLocalTranslation(-fieldX - 1, 0, -fieldY + 1);
        fence.attachChild(fenceLeft);
        fence.attachChild(fenceRight);


        return fence;
    }

    public void createPlayer2() {
    }

    @Override
    public void simpleUpdate(float tpf) {

        if (pY.getSpecialP1() == true || pY.getSpecialP2() == true) {
            if (specialGeom != null) {
               
                setUpSpecialCamera(specialGeom);
            }
        }

        checkPowerUpTime();

        long timeSpentP1 = System.currentTimeMillis() - timeP1;
        if (timeSpentP1 > shootFrequency) {
            shootPauseP1 = false;
        }

        long timeSpentP2 = System.currentTimeMillis() - timeP2;
        if (timeSpentP2 > shootFrequency) {
            shootPauseP2 = false;
        }


        Vector3f walkDirectionP1 = new Vector3f();
        walkDirectionP1.set(0, 0, 0);
        Vector3f walkDirectionP2 = new Vector3f();
        walkDirectionP2.set(0, 0, 0);

        if (leftP1) {
            walkDirectionP1.addLocal(new Vector3f(-10 * specialSpeedChar, 0, 0));
        }
        if (leftP2) {
            walkDirectionP2.addLocal(new Vector3f(-10 * specialSpeedChar, 0, 0));
        }
        if (rightP1) {
            walkDirectionP1.addLocal(new Vector3f(10 * specialSpeedChar, 0, 0));
        }
        if (rightP2) {
            walkDirectionP2.addLocal(new Vector3f(10 * specialSpeedChar, 0, 0));
        }
        if (upP1) {
            walkDirectionP1.addLocal(new Vector3f(0, 0, -10 * specialSpeedChar));
            // character1.warp();
        }
        if (upP2) {
            walkDirectionP2.addLocal(new Vector3f(0, 0, -10 * specialSpeedChar));
        }
        if (downP1) {
            walkDirectionP1.addLocal(new Vector3f(0, 0, 10 * specialSpeedChar));
        }
        if (downP2) {
            walkDirectionP2.addLocal(new Vector3f(0, 0, 10 * specialSpeedChar));
        }

        character1.setWalkDirection(walkDirectionP1);
        walkDirectionP1.multLocal(1000 * moveSpeed).multLocal(tpf);
        character2.setWalkDirection(walkDirectionP2);
        walkDirectionP2.multLocal(1000 * moveSpeed).multLocal(tpf);



        BoundingVolume bvP1 = player1.getWorldBound();
        CollisionResults results = new CollisionResults();
        deathNode.collideWith(bvP1, results);
        if (results.size() > 0) {
            //   Geometry hit = results.getClosestCollision().getGeometry();
            //  System.out.println(hit.getName() + "  " + hit.getUserData("health"));
            //   String healthStr = hit.getUserData("health");
            decHealth(player1);
            // System.out.println(getUserHealth(player1));
            buildBase(player1);
            buildBase(player2);
            collision.playInstance();
            resetPlayer();

        }
        BoundingVolume bvP2 = player2.getWorldBound();
        results = new CollisionResults();
        deathNode.collideWith(bvP2, results);
        if (results.size() > 0) {
            decHealth(player2);
            resetPlayer();
            buildBase(player1);
            buildBase(player2);
            collision.playInstance();
            resetPlayer();
        }

        BoundingVolume bvShot = deathNode.getWorldBound();
        results = new CollisionResults();
        shot.collideWith(bvShot, results);
        if (results.size() > 0) {
            Geometry geom = results.getClosestCollision().getGeometry();
            removeAll(geom);
            // removeRigid(geom);

        }
        /* 
         BoundingVolume bvPower = powerUpNode.getWorldBound();
         results = new CollisionResults();
         shot.collideWith(bvPower, results);
         if (results.size() > 0) {
         System.out.println("0");
         Geometry geom = results.getClosestCollision().getGeometry();
         removeAll(geom);
         // removeRigid(geom);

         }*/
        // }
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }

    public Geometry makeShotP1(float shotAngle) {

        if (pY.getSpecialP1() == true) {
            specialSpeed = 0.1f;
            specialTimeP1 = System.currentTimeMillis();
        }
        shootSoundP1.playInstance(); //sound
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.White);
        Sphere sphere = new Sphere(10, 10, 0.5f, true, false);
        // sphere.setTextureMode(Sphere.TextureMode.Projected);
        Geometry ball_geo = new Geometry("cannon ball1", sphere);
        ball_geo.setMaterial(mat);
        ball_geo.setLocalTranslation(player1.getWorldTranslation().addLocal(0, 1f, 2.5f));
        RigidBodyControl ball_phy = new RigidBodyControl(0.1f);
        ball_geo.addControl(ball_phy);
        bulletAppState.getPhysicsSpace().add(ball_phy);
        Vector3f shootDirection = new Vector3f(shotAngle, 0, 1);
        ball_phy.setLinearVelocity(shootDirection.mult(shootSpeed * specialSpeed));
        ball_phy.setGravity(Vector3f.ZERO);
        shot.attachChild(ball_geo);
        return ball_geo;
    }

    public Geometry makeShotP2(float shotAngle) {

        if (pY.getSpecialP2() == true) {
            specialSpeed = 0.1f;
            specialTimeP2 = System.currentTimeMillis();
        }
        shootSoundP2.playInstance(); //sound    
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.White);
        Sphere sphere = new Sphere(10, 10, 0.5f, true, false);
        // sphere.setTextureMode(Sphere.TextureMode.Projected);
        Geometry ball_geo = new Geometry("cannon ball2", sphere);
        ball_geo.setMaterial(mat);
        shot.attachChild(ball_geo);

        ball_geo.setLocalTranslation(player2.getWorldTranslation().addLocal(0, 1f, -2.5f));

        RigidBodyControl ball_phy = new RigidBodyControl(0.1f);

        ball_geo.addControl(ball_phy);
        bulletAppState.getPhysicsSpace().add(ball_phy);
        Vector3f shootDirection = new Vector3f(shotAngle, 0, -1);
        ball_phy.setLinearVelocity(shootDirection.mult(shootSpeed * specialSpeed));
        ball_phy.setGravity(Vector3f.ZERO);
        return ball_geo;
    }

    public int getUserHealth(Node playerNode) {
        return Integer.parseInt("" + playerNode.getUserData("health"));
    }

    public void setUserHealth(Node playerNode, int value) {
        playerNode.setUserData("health", value);
    }

    public void incHealth(Node playerNode) {
        setUserHealth(playerNode, getUserHealth(playerNode) + 1);
    }

    public void decHealth(Node playerNode) {
        setUserHealth(playerNode, getUserHealth(playerNode) - 1);
        if (getUserHealth(playerNode) <= 0) {
            bulletAppState.getPhysicsSpace().remove(playerNode.getControl(RigidBodyControl.class));
            playerNode.removeFromParent();
            //TODO GAMEEND
        }
    }
    /*  public void setLifePanelText() {
     Screen screen = nifty.getCurrentScreen();
     Element layer = screen.findElementByName("LifePanel");
     layer.getRenderer(TextRenderer.class).setText("Lifepoints " + player1.getUserData("health"));
     nifty.update();
     }*/
}
