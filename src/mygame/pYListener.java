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
    public pYListener(BulletAppState bulletAppState,Node rootNode) {
        bulletAppState.getPhysicsSpace().addCollisionListener(this);
    }    
    public void collision(PhysicsCollisionEvent event) {
       
      //  System.out.println("col");
      //  System.out.println(event.getNodeA().getName());
     //   System.out.println(event.getNodeB().getName());
        if ( event.getNodeA().getName().equals("BlockG") ) {
            if(event.getNodeB().getName().equals("cannon ball")){
                Geometry A = (Geometry) event.getNodeA();
               System.out.println( A.getUserData("health"));
              A.removeFromParent();
            }
          
            //final Node node = (Node)event.getNodeA();
            
            /** ... do something with the node ... */
        } else  if ( event.getNodeB().getName().equals("cannon ball") ) {
            if(event.getNodeA().getName().equals("BlockG")){
                  System.out.println("shot");
            }
        }
    }
}
