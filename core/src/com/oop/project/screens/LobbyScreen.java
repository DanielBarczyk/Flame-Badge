package com.oop.project.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.oop.project.FlameBadge;
import com.oop.project.lobby.GameData;
import com.oop.project.lobby.SaveGame;

public class LobbyScreen implements Screen {
    private final FlameBadge game;
    private final Stage stage;
    private final Skin skin;

    public LobbyScreen(FlameBadge game) {
        this.game = game;
        this.stage = new Stage();
        this.skin = Skins.defaultSkin();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        // Create a button starting a new game
        final TextButton fightButton = new TextButton("Fight", skin);
        fightButton.setPosition((float)Gdx.graphics.getWidth()/2 - fightButton.getWidth()/2,
                (float)Gdx.graphics.getHeight()/2 + fightButton.getHeight()/2 + 10);
        fightButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dispose();
                game.setScreen(new FightScreen(game));
            }
        });
        stage.addActor(fightButton);

        // Create a button loading a game
        TextButton partyButton = new TextButton("Manage party", skin);
        partyButton.setPosition((float)Gdx.graphics.getWidth()/2 - partyButton.getWidth()/2,
                (float)Gdx.graphics.getHeight()/2 - partyButton.getHeight()/2);
        partyButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dispose();
                game.setScreen(new PartyScreen(game));
            }
        });
        stage.addActor(partyButton);

        // Create a button opening settings
        TextButton backButton = new TextButton("Back to Main Menu", skin);
        backButton.setPosition((float)Gdx.graphics.getWidth()/2 - backButton.getWidth()/2,
                (float)Gdx.graphics.getHeight()/2 - 3*backButton.getHeight()/2 - 10);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.currentGame.saveGame();
                dispose();
                game.setScreen(new MainMenuScreen(game));
            }
        });
        stage.addActor(backButton);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.3f, 0.3f, 0.3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
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
