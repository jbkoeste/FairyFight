/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.bullet.control.RigidBodyControl;
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
    

    public pYListener(BulletAppState bulletAppState, Node rootNode) {
        this.bulletAppState = bulletAppState;
        bulletAppState.getPhysicsSpace().addCollisionListener(this);
    }

    public void collision(PhysicsCollisionEvent event) {
        long timeSpent = System.currentTimeMillis() - time;
        if (timeSpent > shootFrequency) {
            shootPause = false;
        }
        
         long timeSpentPlayer = System.currentTimeMillis() - time;
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
                    time= System.currentTimeMillis();
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
                if (shootPausePlayer == false) {
                    System.out.println(getUserHealth(A));
                    decHealth(A);
                    shootPausePlayer = true;
                    timePlayer= System.currentTimeMillis();
                }
            }

            //final Node node = (Node)event.getNodeA();

            /**
             * ... do something with the node ...
             */
        }
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
}
