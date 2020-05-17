package com.oop.project.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.oop.project.FlameBadge;
import com.oop.project.entities.PlayableCharacter;
import com.oop.project.entities.Stats;

import java.util.ArrayList;
import java.util.HashMap;

public class PartyScreen implements Screen {
    public final float PORTRAIT_RESOLUTION = 128;
    public final int PORTRAIT_WIDTH = 6;
    private final FlameBadge game;
    private final Stage stage;
    private final Skin skin;
    private ArrayList<PlayableCharacter> selectedCharacters;
    private final BitmapFont font;
    private PlayableCharacter currentSelect;

    public PartyScreen(FlameBadge game) {
        this.game = game;
        this.stage = new Stage();
        this.skin = Skins.defaultSkin();
        this.selectedCharacters = new ArrayList<>();
        this.font = new BitmapFont();
        this.currentSelect = game.currentGame.getParty().getCharacters().get(0);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        float nextX = 0, nextY = stage.getHeight()-PORTRAIT_RESOLUTION;

        // Create a frame, inside which portraits will be visible
        while(nextY > 0) {
            for(int i=0; i<PORTRAIT_WIDTH; i++) {
                final Image image = new Image(new Texture("frame.png"));
                image.setPosition(nextX, nextY);
                image.setHeight(PORTRAIT_RESOLUTION);
                image.setWidth(PORTRAIT_RESOLUTION);
                stage.addActor(image);
                nextX += PORTRAIT_RESOLUTION;
            }
            nextX = 0;
            nextY -= PORTRAIT_RESOLUTION;
        }
        nextX = 0;
        nextY = stage.getHeight()-PORTRAIT_RESOLUTION;

        // For every available character, create a portrait in the menu
        for(final PlayableCharacter character : game.currentGame.getParty().getCharacters()) {
            final ImageButton image = new ImageButton(new TextureRegionDrawable(new Texture(character.getShortname()+"/portrait.png")));
            image.setColor(0, 0, 0, 0.6f);
            image.setPosition(nextX+8,nextY+8);
            nextX += PORTRAIT_RESOLUTION;
            if(nextX >= PORTRAIT_RESOLUTION * PORTRAIT_WIDTH) {
                nextX = 0;
                nextY -= PORTRAIT_RESOLUTION;
            }
            image.addListener(new ClickListener() {
                private boolean clicked = false;
                private PlayableCharacter owner = character;

                // On clicking a portrait, select it if it's not nad vice versa
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if(!clicked) {
                        image.setColor(0, 0, 0, 1); // Set transparency to 0
                        selectedCharacters.add(owner);
                        clicked = true;
                    } else {
                        image.setColor(0, 0, 0, 0.7f); // Set transparency to 0.7
                        selectedCharacters.remove(owner);
                        clicked = false;
                    }
                }

                // Every time the mouse hovers over a portrait, the character is selected as 'currentSelect'
                @Override
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    currentSelect = owner;
                }
            });
            image.setHeight(PORTRAIT_RESOLUTION - 16);
            image.setWidth(PORTRAIT_RESOLUTION - 16);
            stage.addActor(image);
        }
    }

    @Override
    public void render(float delta) {
        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            dispose();
            game.setScreen(new LobbyScreen(game));
            return;
        }

        game.batch.setProjectionMatrix(game.cam.combined);
        Gdx.gl.glClearColor(0.3f, 0.3f, 0.3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
        game.batch.begin();
        HashMap<Stats, Integer> unitStats = currentSelect.getUnitStats();
        font.draw(game.batch, currentSelect.getLongname(), PORTRAIT_RESOLUTION*PORTRAIT_WIDTH, game.cam.viewportHeight-20);
        font.draw(game.batch, "MAXHP: "+unitStats.get(Stats.MAXHP), PORTRAIT_RESOLUTION*PORTRAIT_WIDTH, game.cam.viewportHeight-40);
        font.draw(game.batch, "ATK: "+unitStats.get(Stats.ATK), PORTRAIT_RESOLUTION*PORTRAIT_WIDTH, game.cam.viewportHeight-60);
        font.draw(game.batch, "SPD: "+unitStats.get(Stats.SPD), PORTRAIT_RESOLUTION*PORTRAIT_WIDTH, game.cam.viewportHeight-80);
        font.draw(game.batch, "SKILL: "+unitStats.get(Stats.SKILL), PORTRAIT_RESOLUTION*PORTRAIT_WIDTH, game.cam.viewportHeight-100);
        font.draw(game.batch, "LCK: "+unitStats.get(Stats.LUCK), PORTRAIT_RESOLUTION*PORTRAIT_WIDTH, game.cam.viewportHeight-120);
        font.draw(game.batch, "DEF: "+unitStats.get(Stats.DEF), PORTRAIT_RESOLUTION*PORTRAIT_WIDTH, game.cam.viewportHeight-140);
        font.draw(game.batch, "RES: "+unitStats.get(Stats.RES), PORTRAIT_RESOLUTION*PORTRAIT_WIDTH, game.cam.viewportHeight-160);
        font.draw(game.batch, "MOV: "+unitStats.get(Stats.MOV), PORTRAIT_RESOLUTION*PORTRAIT_WIDTH, game.cam.viewportHeight-180);
        font.draw(game.batch, "EXP: "+unitStats.get(Stats.EXP), PORTRAIT_RESOLUTION*PORTRAIT_WIDTH, game.cam.viewportHeight-200);
        font.draw(game.batch, "LVL: "+unitStats.get(Stats.LVL), PORTRAIT_RESOLUTION*PORTRAIT_WIDTH, game.cam.viewportHeight-220);
        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

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
