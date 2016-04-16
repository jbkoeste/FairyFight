/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;

/**
 *
 * @author jbkoeste
 */
public class pYListener extends RigidBodyControl
        implements PhysicsCollisionListener {

    private BulletAppState bulletAppState;
    private int shootFrequency = 500;
    private boolean shootPause = false;
    private long time = 0;
    private boolean shootPausePlayer = false;
    private long timePlayer = 0;
    private BetterCharacterControl character1;
    private BetterCharacterControl character2;
    private Node field1Pl1;
    private Node field2Pl1;
    private Node field3Pl1;
    private Node field1Pl2;
    private Node field2Pl2;
    private Node field3Pl2;
    private Node rootNode;

    public pYListener(BulletAppState bulletAppState, Node rootNode, BetterCharacterControl character1, BetterCharacterControl character2, Node field1Pl1, Node field2Pl1, Node field3Pl1, Node field1Pl2, Node field2Pl2, Node field3Pl2) {
        this.bulletAppState = bulletAppState;
        this.character1 = character1;
        this.character2 = character2;
        this.field1Pl1 =  field1Pl1;
        this.field2Pl1 =  field2Pl1;
        this.field3Pl1 =  field3Pl1;
        this.field1Pl2 =  field1Pl2;
        this.field2Pl2 =  field2Pl2;
        this.field3Pl2 =  field3Pl2;
        this.rootNode = rootNode;
        bulletAppState.getPhysicsSpace().addCollisionListener(this);
    }

    public void collision(PhysicsCollisionEvent event) {
        long timeSpent = System.currentTimeMillis() - time;
        if (timeSpent > shootFrequency) {
            shootPause = false;
        }

        long timeSpentPlayer = System.currentTimeMillis() - timePlayer;
        if (timeSpentPlayer > shootFrequency) {
            shootPausePlayer = false;
        }
        //  System.out.println("col");
        //  System.out.println(event.getNodeA().getName());
        //   System.out.println(event.getNodeB().getName());
        if (event.getNodeA().getName().equals("BlockG")) {
            if (event.getNodeB().getName().equals("cannon ball")) {
                Geometry A = (Geometry) event.getNodeA();
                //  System.out.println( A.getUserData("health"));
                if (shootPause == false) {
                    decHealth(A);
                    shootPause = true;
                    time = System.currentTimeMillis();
                }
            }

            //final Node node = (Node)event.getNodeA();

            /**
             * ... do something with the node ...
             */
        } else if (event.getNodeB().getName().equals("cannon ball")) {
            if (event.getNodeA().getName().equals("Node_player2")) {
                System.out.println("shot");
            }
        }

        if (event.getNodeA().getName().equals("Node_Player2")) {

            if (event.getNodeB().getName().equals("cannon ball")) {
                Node A = (Node) event.getNodeA();
               Geometry B = (Geometry) event.getNodeB();
                if (shootPausePlayer == false) {
                    System.out.println(getUserHealth(A));
                    decHealth(A);
                    buildBase(A);
                    removeAll(B);
                    removeRigid(B);
                    resetPlayer();
                    shootPausePlayer = true;
                    timePlayer = System.currentTimeMillis();
                }
            }

        }



        if (event.getNodeA().getName().equals("Node_Player1")) {

            if (event.getNodeB().getName().equals("cannon ball")) {
                Node A = (Node) event.getNodeA();
               Geometry B = (Geometry) event.getNodeB();
                if (shootPausePlayer == false) {
                    System.out.println(getUserHealth(A));
                    decHealth(A);
                    buildBase(A);
                    removeAll(B);
                    removeRigid(B);
                    resetPlayer();
                    shootPausePlayer = true;
                    timePlayer = System.currentTimeMillis();
                }
            }

        }


        if (event.getNodeA().getName().equals("death")) {

            if (event.getNodeB().getName().equals("Node_Player1")) {
                Node A = (Node) event.getNodeA();

                System.out.println(getUserHealth(A));
                decHealth(A);
                resetPlayer();

            }

        }
    }

    public void resetPlayer() {
        character1.warp(new Vector3f(3, 2f, -2));
        character2.warp(new Vector3f(-3, 2f, 18));
    }

    public int getUserHealth(Geometry playerNode) {
        return Integer.parseInt("" + playerNode.getUserData("health"));
    }

    public void setUserHealth(Geometry playerNode, int value) {
        playerNode.setUserData("health", value);
    }

    public void incHealth(Geometry playerNode) {
        setUserHealth(playerNode, getUserHealth(playerNode) + 1);
     
        
    }

    public void decHealth(Geometry playerNode) {
        setUserHealth(playerNode, getUserHealth(playerNode) - 1);
        if (getUserHealth(playerNode) <= 0) {
            bulletAppState.getPhysicsSpace().remove(playerNode.getControl(RigidBodyControl.class));
            playerNode.removeFromParent();
        }
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
        }
    }
public void removeAll(Node removeNode){
        bulletAppState.getPhysicsSpace().remove(removeNode);
        removeNode.removeFromParent();
    }

   public void removeRigid(Node removeNode){
        // removeNode.removeControl(RigidBodyControl.class);
         bulletAppState.getPhysicsSpace().remove(removeNode);
    }
   public void removeAll(Geometry removeNode){
        bulletAppState.getPhysicsSpace().remove(removeNode);
        removeNode.removeFromParent();
    }

   public void removeRigid(Geometry removeNode){
        // removeNode.removeControl(RigidBodyControl.class);
         bulletAppState.getPhysicsSpace().remove(removeNode);
    }
   
     public void buildBase(Node playerNode) {
        if (getUserHealth(playerNode) == 3) {
            if (playerNode.getName().equals("Node_Player1") == true) {
                
                if ((field2Pl2).getParent() != null ) {
                    System.out.println("jj");
                    removeAll(field2Pl2);
                }
                if( (field2Pl2).getParent() == null&&(field2Pl2.getControl(RigidBodyControl.class)!=null)){
                    removeRigid(field2Pl2);
                }
                if ((field3Pl2).getParent() != null) {
                    removeAll(field3Pl2);
                }
                if( (field3Pl2).getParent() == null&&(field3Pl2.getControl(RigidBodyControl.class)!=null)){
                    removeRigid(field3Pl2);
                }
            } else {

                if ((field2Pl1).getParent() != null) {
                    removeAll(field2Pl1);
                }
                if( (field2Pl1).getParent() == null&&(field2Pl1.getControl(RigidBodyControl.class)!=null)){
                    removeRigid(field2Pl1);
                }
                if ((field3Pl1).getParent() != null) {
                    removeAll(field3Pl1);
                }
                if( (field3Pl1).getParent() == null&&(field3Pl1.getControl(RigidBodyControl.class)!=null)){
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
                if( (field3Pl2).getParent() == null&&(field3Pl2.getControl(RigidBodyControl.class)!=null)){
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
                if( (field3Pl1).getParent() == null&&(field3Pl1.getControl(RigidBodyControl.class)!=null)){
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
}
