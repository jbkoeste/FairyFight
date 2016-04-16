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
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Sphere;
import com.jme3.texture.Texture;
import com.jme3.texture.Texture.WrapMode;
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
    private float moveSpeed = 50;
    private float shootSpeed = 50;
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

    public void removeAll(Geometry removeNode) {
        System.out.println("removeg");
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
        pYListener pY = new pYListener(bulletAppState, rootNode, character1, character2, field1Pl1, field2Pl1, field3Pl1, field1Pl2, field2Pl2, field3Pl2, assetManager);

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

        setUpCamera();
        resetPlayer();
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


            if (name.equals("ShootP1") && !keyPressed) {
                float shotAngle = 0;
                if (angleLeftP1 == true) {
                    shotAngle = -0.5f;
                } else if (angleRightP1 == true) {
                    shotAngle = 0.5f;
                } else {
                    shotAngle = 0;
                }
                if (shootPauseP1 == false) {
                    makeShotP1(shotAngle);
                    shootPauseP1 = true;

                    timeP1 = System.currentTimeMillis();
                }
            }

            if (name.equals("ShootP2") && !keyPressed) {
                float shotAngle = 0;
                if (angleLeftP2 == true) {
                    shotAngle = -0.5f;
                } else if (angleRightP2 == true) {
                    shotAngle = 0.5f;
                } else {
                    shotAngle = 0;
                }
                if (shootPauseP2 == false) {
                    makeShotP2(shotAngle);
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

    public Node createField(float x, float z, float y) {
        Node field = new Node("field");
        Geometry fieldBottomPlayer1 = createBox(fieldX, 1, fieldY / 3, "Field_p1");
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
        TextureKey key = new TextureKey("Textures/ziegel.png");
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
            walkDirectionP1.addLocal(new Vector3f(-10, 0, 0));
        }
        if (leftP2) {
            walkDirectionP2.addLocal(new Vector3f(-10, 0, 0));
        }
        if (rightP1) {
            walkDirectionP1.addLocal(new Vector3f(10, 0, 0));
        }
        if (rightP2) {
            walkDirectionP2.addLocal(new Vector3f(10, 0, 0));
        }
        if (upP1) {
            walkDirectionP1.addLocal(new Vector3f(0, 0, -10));
            // character1.warp();
        }
        if (upP2) {
            walkDirectionP2.addLocal(new Vector3f(0, 0, -10));
        }
        if (downP1) {
            walkDirectionP1.addLocal(new Vector3f(0, 0, 10));
        }
        if (downP2) {
            walkDirectionP2.addLocal(new Vector3f(0, 0, 10));
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
        // }
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }

    public void makeShotP1(float shotAngle) {
        shootSoundP1.playInstance(); //sound
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.White);
        Sphere sphere = new Sphere(10, 10, 0.5f, true, false);
        // sphere.setTextureMode(Sphere.TextureMode.Projected);
        Geometry ball_geo = new Geometry("cannon ball", sphere);
        ball_geo.setMaterial(mat);

        //rootNode.attachChild();

        ball_geo.setLocalTranslation(player1.getWorldTranslation().addLocal(0, 1f, 2.5f));

        RigidBodyControl ball_phy = new RigidBodyControl(0.1f);

        ball_geo.addControl(ball_phy);
        bulletAppState.getPhysicsSpace().add(ball_phy);
        Vector3f shootDirection = new Vector3f(shotAngle, 0, 1);
        ball_phy.setLinearVelocity(shootDirection.mult(shootSpeed));
        shot.attachChild(ball_geo);
    }

    public void makeShotP2(float shotAngle) {
        shootSoundP2.playInstance(); //sound    
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.White);
        Sphere sphere = new Sphere(10, 10, 0.5f, true, false);
        // sphere.setTextureMode(Sphere.TextureMode.Projected);
        Geometry ball_geo = new Geometry("cannon ball", sphere);
        ball_geo.setMaterial(mat);
        shot.attachChild(ball_geo);

        ball_geo.setLocalTranslation(player2.getWorldTranslation().addLocal(0, 1f, -2.5f));

        RigidBodyControl ball_phy = new RigidBodyControl(0.1f);

        ball_geo.addControl(ball_phy);
        bulletAppState.getPhysicsSpace().add(ball_phy);
        Vector3f shootDirection = new Vector3f(shotAngle, 0, -1);
        ball_phy.setLinearVelocity(shootDirection.mult(shootSpeed));

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
