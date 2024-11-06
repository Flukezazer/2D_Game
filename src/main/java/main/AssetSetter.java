/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import monster.MON_Ghost;
import monster.MON_Ghost2;
import object.OBJ_Exit;
import object.OBJ_Door;
import object.OBJ_Key;

/**
 *
 * @author User
 */
public class AssetSetter {
    
    GamePanel gp;
    
    public AssetSetter(GamePanel gp){
        this.gp = gp;
    }
    
    public void setMonster(){
        
        
        
        gp.monster[0] = new MON_Ghost(gp);
        gp.monster[0].worldX = gp.tileSize*10;
        gp.monster[0].worldY = gp.tileSize*88;
        
        gp.monster[1] = new MON_Ghost(gp);
        gp.monster[1].worldX = gp.tileSize*10;
        gp.monster[1].worldY = gp.tileSize*91;
        
        gp.monster[2] = new MON_Ghost(gp);
        gp.monster[2].worldX = gp.tileSize*17;
        gp.monster[2].worldY = gp.tileSize*34;
        
        gp.monster[3] = new MON_Ghost(gp);
        gp.monster[3].worldX = gp.tileSize*21;
        gp.monster[3].worldY = gp.tileSize*35;
        
        gp.monster[4] = new MON_Ghost(gp);
        gp.monster[4].worldX = gp.tileSize*74;
        gp.monster[4].worldY = gp.tileSize*33;
        
        gp.monster[5] = new MON_Ghost(gp);
        gp.monster[5].worldX = gp.tileSize*72;
        gp.monster[5].worldY = gp.tileSize*35;
        
        gp.monster[6] = new MON_Ghost(gp);
        gp.monster[6].worldX = gp.tileSize*17;
        gp.monster[6].worldY = gp.tileSize*38;
        
        gp.monster[7] = new MON_Ghost2(gp);
        gp.monster[7].worldX = gp.tileSize*14;
        gp.monster[7].worldY = gp.tileSize*37;
        
        gp.monster[8] = new MON_Ghost2(gp);
        gp.monster[8].worldX = gp.tileSize*24;
        gp.monster[8].worldY = gp.tileSize*36;
        
        gp.monster[9] = new MON_Ghost2(gp);
        gp.monster[9].worldX = gp.tileSize*23;
        gp.monster[9].worldY = gp.tileSize*34;
        
        gp.monster[10] = new MON_Ghost(gp);
        gp.monster[10].worldX = gp.tileSize*73;
        gp.monster[10].worldY = gp.tileSize*38;
        
        gp.monster[11] = new MON_Ghost(gp);
        gp.monster[11].worldX = gp.tileSize*72;
        gp.monster[11].worldY = gp.tileSize*38;
        
        
    }
    public void setObject(){
        
        gp.obj[0] = new OBJ_Key();
        gp.obj[0].worldX = 7 * gp.tileSize;
        gp.obj[0].worldY = 89 * gp.tileSize;
        
        gp.obj[1] = new OBJ_Key();
        gp.obj[1].worldX = 19 * gp.tileSize;
        gp.obj[1].worldY = 32 * gp.tileSize;
        
        gp.obj[2] = new OBJ_Key();
        gp.obj[2].worldX = 12 * gp.tileSize;
        gp.obj[2].worldY = 68 * gp.tileSize;
        
        gp.obj[3] = new OBJ_Key();
        gp.obj[3].worldX = 78 * gp.tileSize;
        gp.obj[3].worldY = 34 * gp.tileSize;
        
        gp.obj[4] = new OBJ_Door();
        gp.obj[4].worldX = 83 * gp.tileSize;
        gp.obj[4].worldY = 62 * gp.tileSize;
        
        gp.obj[5] = new OBJ_Door();
        gp.obj[5].worldX = 88 * gp.tileSize;
        gp.obj[5].worldY = 70 * gp.tileSize;
        
        gp.obj[6] = new OBJ_Exit();
        gp.obj[6].worldX = 76* gp.tileSize;
        gp.obj[6].worldY = 86 * gp.tileSize;
        
        gp.obj[7] = new OBJ_Door();
        gp.obj[7].worldX = 83 * gp.tileSize;
        gp.obj[7].worldY = 74 * gp.tileSize;
        
        gp.obj[8] = new OBJ_Door();
        gp.obj[8].worldX = 76 * gp.tileSize;
        gp.obj[8].worldY = 80 * gp.tileSize;
    }
}
