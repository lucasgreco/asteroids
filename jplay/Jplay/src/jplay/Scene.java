/*
 * The use this code commercially must only be done with permission of the author.
 * Any modification shall be advised and sent to the author.
 * The author is not responsible for any problem therefrom the use of this frameWork.
 *
 * 
 * @author VisionLab/PUC-Rio
 * Modifications by Gefersom Cardoso Lima  
 *                  Federal Fluminense University
 *                  Computer Science
 */


package jplay;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class responsible for handling a Scenario.
 */
public class Scene
{
    private GameImage backDrop;
    private GameImage[] tiles;
    private String nameImages[];//It is used when we want to save the state of the scene
    private ArrayList tileLayer;
    private ArrayList overlays;
    private int drawStartX = 0;
    private int drawStartY = 0;

    /**
     * Loads a scene from a file.
     * @param sceneFile File path.
     */
    public void loadFromFile(String sceneFile)
    {
            tileLayer = new ArrayList();
            overlays = new ArrayList();

            try
            {
                BufferedReader input = new BufferedReader(new FileReader(new File(sceneFile)));

                //first read the number of tile images
                String line = input.readLine();
                int numOfTileImages = Integer.parseInt(line, 10);
                tiles = new GameImage[numOfTileImages];
                nameImages = new String[numOfTileImages+1];

                for(int i = 0 ; i < numOfTileImages ; i++)
                {
                    //read each tile image name
                    line = input.readLine();
                    tiles[i] = new Sprite(line);
                    nameImages[i] = line;
                }

                //now read the tile set map until the final
                //character is found "%"
                String endTileSet = "%";

                line = input.readLine();

                while(line.equals(endTileSet) != true)
                {
                    ArrayList tileLine = new ArrayList();

                    String[] tileIndices = line.split(",");

                    for(int i = 0 ; i < tileIndices.length ; i++)
                    {
                        TileInfo tileInfo = new TileInfo();
                        tileInfo.id = Integer.parseInt(tileIndices[i]);
                        tileLine.add( tileInfo );
                    }
                    tileLayer.add(tileLine);

                    line = input.readLine();
                }

                //now read the backdrop file
                line = input.readLine();
                backDrop = new GameImage(line);
                nameImages[numOfTileImages] = line;

            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
    }

    /**
     * Adds a overlay scene.
     * @param overlay Any GameObject.
     */
    public void addOverlay(GameObject overlay)
    {
            overlays.add(overlay);
    }

    /**
     * Sets the initial X and Y position will be used to draw the scene.
     * @param drawStartX
     * @param drawStartY
     */
    public void setDrawStartPos(int drawStartX, int drawStartY)
    {
            this.drawStartX = drawStartX;
            this.drawStartY = drawStartY;
    }

    /**
     * Draws the scene on the screen.
     */
    public void draw()
    {
            //first clear the scene
            Graphics g = Window.getInstance().getGameGraphics();
            Window.getInstance().clear(Color.BLACK);

            //first draw the backdrop
            int startDrawX = drawStartX;
            int startDrawY = drawStartY;

            backDrop.draw();

            //now draw the tile set
            int tileWidth = tiles[0].width;
            int tileHeight = tiles[0].height;

            int line = 0;
            int drawY = startDrawY;

            do
            {
                ArrayList tileLine = (ArrayList)tileLayer.get(line);

                int drawX = startDrawX;

                for(int c = 0 ; c < tileLine.size() ; c++, drawX += tileWidth)
                {
                    TileInfo tileInfo = (TileInfo) tileLine.get(c);

                    if(tileInfo.id == 0)
                    {
                        continue;
                    }
                    tiles[tileInfo.id-1].x = drawX;
                    tiles[tileInfo.id-1].y = drawY;
                    tiles[tileInfo.id-1].draw();
                }

                drawY += tileHeight;
                line++;

            }while(line < tileLayer.size());

            //finally draw the overlays
            for(int i = 0 ; i < overlays.size() ; i++)
            {
                GameImage element = (GameImage)overlays.get(i);
                element.draw();
            }
    }

    /**
     * Returns the file info stored in the row and column position of matrix.
     * @param row Row in the matrix.
     * @param colunm Column in the matrix.
     * @return TileInfo
     */
    public TileInfo getTile(int row, int colunm)
    {
            ArrayList<TileInfo> tileLine = (ArrayList<TileInfo>) tileLayer.get(row);
            return tileLine.get(colunm);
    }

    /**
     * Returns the Tiles below the area bounded by max and min points.
     * @param min Upper left corner point of the area.
     * @param max Lower right point of the area.
     * @return Vector
     */
    @SuppressWarnings("UseOfObsoleteCollectionType")
    public Vector getTilesFromRect(Point min, Point max)
    {

            Vector v = new Vector();

            int startDrawX = drawStartX;
            int startDrawY = drawStartY;

            int tileWidth = tiles[0].width;
            int tileHeight = tiles[0].height;
           
            int line = 0;
            int drawY = startDrawY;

            do
            {
                ArrayList tileLine = (ArrayList)tileLayer.get(line);

                int drawX = startDrawX;

                for(int c = 0 ; c < tileLine.size() ; c++, drawX += tileWidth)
                {
                    TileInfo tile = (TileInfo) tileLine.get(c);

                    tile.x = drawX;
                    tile.y = drawY;
                    tile.width = tileWidth;
                    tile.height = tileHeight;

                    if((min.x > drawX + tileWidth -1) || (max.x < tile.x))
                    {
                        continue;
                    }
                    if((min.y > drawY + tileHeight +1) || (max.y < tile.y))
                    {
                        continue;
                    }
                    boolean add = v.add(tile);
                }

                drawY += tileHeight;
                line++;

            }while(line < tileLayer.size());

            return v;
    }

    /**
     * Removes a tile from the matrix.
     * @param row
     * @param colunm
     */
    public void removeTile(int row, int colunm)
    {
            ArrayList<TileInfo> tileLine = (ArrayList<TileInfo>) tileLayer.get(row);
            if ( colunm < tileLine.size())
                tileLine.remove( colunm);
    }

    /**
     * Changes the id tile storage in the matrix.
     * @param row Row of the matrix.
     * @param colunm Colunm of the matrix.
     * @param newID New code which will replace the old Id represented for the row and column.
     */
    public void changeTile(int row, int colunm, int newID)
    {
            ArrayList<TileInfo> tileLine = (ArrayList<TileInfo>) tileLayer.get(row);
            tileLine.get(colunm).id = newID;
    }

    /**
     * Save the current state of the scene in a new file.
     * @param fileName Path of the file to save the scene.
     */
    public void saveToFile(String fileName)
    {
        try
        {
            BufferedWriter out = new BufferedWriter(new FileWriter(fileName));

            out.write(this.tiles.length + "\n");
            for (int i=0; i < tiles.length; i++)
                out.write(nameImages[i]+"\n");

            for (int i=0; i < tileLayer.size(); i++)
            {
                ArrayList<TileInfo> tileLine = (ArrayList<TileInfo>) tileLayer.get(i);
                int j = 0;
                for (j=0; j < tileLine.size()-1; j++)                
                    out.write(tileLine.get(j).id + ",");
                
                out.write(tileLine.get(j).id+"\n");
            }

            out.write("%\n");
            out.write(this.nameImages[tiles.length]);

            out.close();
            
        } catch (IOException ex) {
            Logger.getLogger(Scene.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
