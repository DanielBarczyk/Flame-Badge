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

public class LoadGameScreen implements Screen {
    private final FlameBadge game;
    private final Stage stage;
    private final Skin skin;

    public LoadGameScreen(FlameBadge game) {
        this.game = game;
        this.stage = new Stage();
        this.skin = Skins.defaultSkin();
    }
    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        int offset = 100;
        for(final GameData save : GameData.getSaves()) {
            // Create a button starting a new game
            final TextButton saveButton = new TextButton(save.toString(), skin);
            saveButton.setPosition((float)Gdx.graphics.getWidth()/2 - saveButton.getWidth()/2,
                    (float)Gdx.graphics.getHeight() - offset);
            saveButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    game.currentGame = GameData.loadGame(save.toString());
                    dispose();
                    game.setScreen(new LobbyScreen(game));
                }
            });
            offset += saveButton.getHeight() + 10;
            stage.addActor(saveButton);
        }
        final TextButton backButton = new TextButton("Back to menu", skin);
        backButton.setPosition((float)Gdx.graphics.getWidth()/2 - backButton.getWidth()/2,
                (float)Gdx.graphics.getHeight() - offset);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
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
