package com.oop.project.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector3;
import com.oop.project.FlameBadge;
import com.oop.project.battles.Combat;
import com.oop.project.entities.EnemyCharacter;
import com.oop.project.map.TileType;
import com.oop.project.map.TiledGameMap;

public class FightScreen implements Screen {
    FlameBadge game;
    public FightScreen(FlameBadge game) {
        this.game = game;
    }

    @Override
    public void show() {
        game.gameMap = new TiledGameMap();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        mouseInput();
        endInput();
        game.gameMap.update(Gdx.graphics.getDeltaTime());
        game.gameMap.render(game.cam, game.batch);
    }


    public void mouseInput(){
        //drag map around
        if(Gdx.input.isTouched()) {
            game.cam.translate(-Gdx.input.getDeltaX(),Gdx.input.getDeltaY());
            game.cam.update();
        }

        if(Gdx.input.justTouched()) {
            Vector3 pos = game.cam.unproject(new Vector3(Gdx.input.getX(),Gdx.input.getY(),0));
            TileType type = game.gameMap.getTileTypeByLocation(0,pos.x,pos.y);

            if(type != null) { //if we clicked in bounds
                int tileX = (int)(pos.x / TileType.TILE_SIZE);
                int tileY = (int)(pos.y / TileType.TILE_SIZE);
                if(game.gameMap.isTileOccupiedByPlayable(tileX,tileY)) {
                    game.gameMap.activeCharacter = game.gameMap.playableOnTile(tileX,tileY);
                }
                if(game.gameMap.isTileOccupiedByEnemy(tileX,tileY)){
                    EnemyCharacter enemyCharacter = game.gameMap.enemyOnTile(tileX,tileY);

                    if(Combat.canTheyFight(game.gameMap.activeCharacter,enemyCharacter)){
                        Combat.battle(game.gameMap.activeCharacter,enemyCharacter);
                        if(game.gameMap.activeCharacter.getCurrentHp() <= 0)
                            game.gameMap.kill(game.gameMap.activeCharacter);
                        if(enemyCharacter.getCurrentHp() <= 0)
                            game.gameMap.kill(enemyCharacter);
                        game.gameMap.activeCharacter.makeInactive();
                    }
                }
                //System.out.println(type.getId()+" "+type.getName());       //provide info on the tile clicked
            }
        }
    }

    public void endInput() {
        if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)){
            game.gameMap.endTurn();
        }
    }


    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {}
}