package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;

/**
 * test
 * @author normenhansen
 */
public class Main extends SimpleApplication {
public float fieldX =8;
public float fieldRatio = 0.75f;
public float fieldY = fieldX * fieldRatio;

    public static void main(String[] args) {
        Main app = new Main();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        Node fieldNode = new Node("fieldNode");
        Node fenceNode = new Node("fenceNode");
        Node exitNode = new Node("exit");
        
        flyCam.setMoveSpeed(30);
        
        
        fenceNode.attachChild(createFence());
        fieldNode.attachChild(createField());
        exitNode.attachChild(createExit());
        
        rootNode.attachChild(exitNode);
        rootNode.attachChild(fenceNode);
        rootNode.attachChild(fieldNode);
        
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
        exit.attachChild(exitLeft);
        exit.attachChild(exitRight);
        exit.attachChild(exitFront);
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
           
            
            fenceLeft.setLocalTranslation(fieldX+1, 0, -fieldY+1);
             fenceRight = (Node) fenceLeft.clone();
            fenceRight.setLocalTranslation(-fieldX-1, 0, -fieldY+1);
            fence.attachChild(fenceLeft);
            fence.attachChild(fenceRight);
        }
         return fence;
    }
    
    
    public void createPlayer1(){
        
    }
    
    public void createPlayer2(){
        
    }

    @Override
    public void simpleUpdate(float tpf) {
        //TODO: add update code
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}
