package com.oop.project.map;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.oop.project.FlameBadge;

public class TiledGameMap extends GameMap {

    private TiledMap tiledMap;
    private OrthogonalTiledMapRenderer tiledMapRenderer;

    public TiledGameMap(FlameBadge game){
        this.game=game;
        loadmaps();
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        activeCharacter = playableCharacters.get(0);
    }

    private void loadmaps(){
        switch (game.currentGame.getCurrentLevel()) {
            case 1:
                loadmap1();
                break;
            case 2:
                loadmap2();
                break;
            case 3:
                loadmap3();
                break;
            case 4:
                loadmap4();
                break;
            case 5:
                loadmap5();
                break;
        }
    }

    private void loadmap1(){
        tiledMap=new TmxMapLoader().load("map1.tmx");
        mapMaxX=9;
        mapMaxY=4;
        addPlayableCharactersMap1();
        addEnemyCharactersMap1();
    }

    private void loadmap2(){
        tiledMap=new TmxMapLoader().load("map2.tmx");
        mapMaxX=14;
        mapMaxY=9;
        addPlayableCharactersMap2();
        addEnemyCharactersMap2();
    }private void loadmap3(){
        tiledMap=new TmxMapLoader().load("map3.tmx");
        mapMaxX=14;
        mapMaxY=9;
        addPlayableCharactersMap3();
        addEnemyCharactersMap3();
    }private void loadmap4(){
        tiledMap=new TmxMapLoader().load("map4.tmx");
        mapMaxX=14;
        mapMaxY=9;
        addPlayableCharactersMap4();
        addEnemyCharactersMap4();
    }private void loadmap5(){
        tiledMap=new TmxMapLoader().load("map5.tmx");
        mapMaxX=14;
        mapMaxY=9;
        addPlayableCharactersMap5();
        addEnemyCharactersMap5();
    }


    @Override
    public void render(OrthographicCamera camera, SpriteBatch spriteBatch) {
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();

        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();
        super.render(camera,spriteBatch);
        spriteBatch.end();

    }

    @Override
    public void update(float delta) {
        super.update(delta);
    }

    @Override
    public void dispose() {
        tiledMap.dispose();
    }

    @Override
    public TileType getTileTypeByCoordinate(int layer, int col, int row) {
        Cell cell = ((TiledMapTileLayer)tiledMap.getLayers().get(layer)).getCell(col,row);
        if(cell!=null){
            TiledMapTile tile=cell.getTile();
            if(tile!=null){
                int id=tile.getId();
                return TileType.getTileTypeById(id);
            }
        }
        return null;
    }

}
