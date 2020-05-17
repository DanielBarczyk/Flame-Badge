package com.oop.project.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.oop.project.Equipment.Weapon;
import com.oop.project.FlameBadge;
import com.oop.project.battles.Combat;
import com.oop.project.entities.EnemyCharacter;
import com.oop.project.lobby.GameData;
import com.oop.project.map.TileType;
import com.oop.project.map.TiledGameMap;

public class FightScreen implements Screen {
    private FlameBadge game;
    private final Stage stage;
    private final Skin skin;
    FightScreen(FlameBadge game) {
        this.game = game;
        this.stage = new Stage();
        this.skin=Skins.defaultSkin();
    }

    @Override
    public void show() {
        game.gameMap = new TiledGameMap(game);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        mouseInput();
        keyboardInput();
        game.gameMap.update(Gdx.graphics.getDeltaTime());
        game.gameMap.render(game.cam, game.batch);
        stage.act();
        stage.draw();
    }


    private void mouseInput(){
        //drag map around
        if(Gdx.input.isTouched()) {
            game.cam.translate(-Gdx.input.getDeltaX(),Gdx.input.getDeltaY());
            game.cam.update();
        }

        if(Gdx.input.justTouched()) {
            Vector3 pos = game.cam.unproject(new Vector3(Gdx.input.getX(),Gdx.input.getY(),0));
            TileType type = game.gameMap.getTileTypeByLocation(0,pos.x,pos.y);

            if(type != null) { //if we clicked in bounds
                int tileX = (int) (pos.x / TileType.TILE_SIZE);
                int tileY = (int) (pos.y / TileType.TILE_SIZE);
                if(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
                    if (game.gameMap.isTileOccupiedByPlayable(tileX, tileY)) {
                        game.gameMap.playableOnTile(tileX, tileY).printStats();
                        game.gameMap.playableOnTile(tileX, tileY).printStatsButton(stage,skin);
                    }
                    if (game.gameMap.isTileOccupiedByEnemy(tileX, tileY)) {
                        game.gameMap.enemyOnTile(tileX, tileY).printStats();
                        game.gameMap.enemyOnTile(tileX, tileY).printStatsButton(stage,skin);
                    }
                } else if(Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT)){
                    if (game.gameMap.isTileOccupiedByEnemy(tileX, tileY)) {
                        EnemyCharacter enemyCharacter = game.gameMap.enemyOnTile(tileX, tileY);
                        if (Combat.canTheyFight(game.gameMap.activeCharacter, enemyCharacter)) {
                            Combat.showCombatPrediction(game.gameMap.activeCharacter,enemyCharacter,stage,skin);
                        }
                    }
                } else {
                    if (game.gameMap.isTileOccupiedByPlayable(tileX, tileY)) {
                        Weapon.cleanInventoryButtons(game.gameMap.activeCharacter);
                        game.gameMap.activeCharacter = game.gameMap.playableOnTile(tileX, tileY);
                    }
                    if (game.gameMap.isTileOccupiedByEnemy(tileX, tileY)) {
                        EnemyCharacter enemyCharacter = game.gameMap.enemyOnTile(tileX, tileY);

                        if (Combat.canTheyFight(game.gameMap.activeCharacter, enemyCharacter)) {
                            Combat.battle(game.gameMap.activeCharacter, enemyCharacter);
                            if (game.gameMap.activeCharacter.getCurrentHp() <= 0)
                                game.gameMap.kill(game.gameMap.activeCharacter);
                            if (enemyCharacter.getCurrentHp() <= 0)
                                game.gameMap.kill(enemyCharacter);
                            game.gameMap.activeCharacter.makeInactive();
                        }
                    }
                }
            }
        }
    }

    private void keyboardInput(){
        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            dispose();
            game.setScreen(new LobbyScreen(game));
            return;
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)){
            game.gameMap.endTurn();
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.I)){
            Weapon.showItems(stage, skin, game.gameMap.activeCharacter);
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
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}
