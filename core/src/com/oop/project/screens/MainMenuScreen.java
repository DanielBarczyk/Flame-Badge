package com.oop.project.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.oop.project.FlameBadge;

import static com.oop.project.entities.Entity.printStatsExplanationButton;

public class MainMenuScreen implements Screen {
    private final FlameBadge game;
    private final Stage stage;
    private final Skin skin;

    public MainMenuScreen(FlameBadge game) {
        this.game = game;
        this.stage = new Stage();
        this.skin = Skins.defaultSkin();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        // Create a button starting a new game
        final TextButton gameButton = new TextButton("New game", skin);
        gameButton.setPosition((float)Gdx.graphics.getWidth()/2 - gameButton.getWidth()/2,
                               (float)Gdx.graphics.getHeight()/2 + gameButton.getHeight() + 10);
        gameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dispose();
                game.setScreen(new LobbyScreen(game));
            }
        });
        stage.addActor(gameButton);

        // Create a button loading a game
        TextButton loadButton = new TextButton("Load game", skin);
        loadButton.setPosition((float)Gdx.graphics.getWidth()/2 - loadButton.getWidth()/2,
                               (float)Gdx.graphics.getHeight()/2);
        loadButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("TO DO: Load game");
            }
        });
        stage.addActor(loadButton);

        // Create a button opening settings
        TextButton settButton = new TextButton("Settings", skin);
        settButton.setPosition((float)Gdx.graphics.getWidth()/2 - settButton.getWidth()/2,
                (float)Gdx.graphics.getHeight()/2 - settButton.getHeight() - 10);
        settButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("TO DO: Open settings");
            }
        });
        stage.addActor(settButton);

        //Create a button exiting the game
        TextButton exitButton = new TextButton("Exit", skin);
        exitButton.setPosition((float)Gdx.graphics.getWidth()/2 - exitButton.getWidth()/2,
                               (float)Gdx.graphics.getHeight()/2 - 2 * exitButton.getHeight() - 20);
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.dispose();
                System.exit(0);
            }
        });
        stage.addActor(exitButton);

        //Create a button for information about stats
        TextButton statsInfoButton = new TextButton("Stats Explained", skin);
        statsInfoButton.setPosition(3*(float)Gdx.graphics.getWidth()/4 - statsInfoButton.getWidth()/4,
               3*(float)Gdx.graphics.getHeight()/4 - statsInfoButton.getHeight());
        statsInfoButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                printStatsExplanationButton(stage, skin);
            }
        });
        stage.addActor(statsInfoButton);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
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
