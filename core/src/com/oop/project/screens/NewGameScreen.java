package com.oop.project.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.oop.project.FlameBadge;
import com.oop.project.lobby.GameData;

public class NewGameScreen implements Screen {
    private final FlameBadge game;
    private final Stage stage;
    private final Skin skin;
    private final TextField textField;
    private final BitmapFont font;
    private final BitmapFont redFont;
    private boolean error = false;

    public NewGameScreen(FlameBadge game) {
        this.game = game;
        this.stage = new Stage();
        this.skin = Skins.defaultSkin();
        this.textField = new TextField("NewGame", skin);
        this.font = Skins.SubTitleFont();
        this.redFont = Skins.RedFont();
        textField.setHeight(200);
        textField.setWidth(400);
        textField.setMaxLength(13);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        textField.setTextFieldFilter(new TextField.TextFieldFilter() {
            @Override
            public boolean acceptChar(TextField textField, char c) {
                return Character.isLetter(c);
            }
        });
        textField.setPosition(stage.getWidth()/2-textField.getWidth()/2, stage.getHeight()/2-textField.getHeight()/2);
        stage.addActor(textField);
    }

    @Override
    public void render(float delta) {
        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            dispose();
            game.setScreen(new MainMenuScreen(game));
            return;
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            try{
                game.currentGame = new GameData(textField.getText());
                dispose();
                game.setScreen(new LobbyScreen(game));
                return;
            }catch(RuntimeException e){
                error = true;
            }
        }

        Gdx.gl.glClearColor(0.3f, 0.3f, 0.3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();

        game.batch.setProjectionMatrix(game.cam.combined);
        game.batch.begin();
        font.draw(game.batch, "INSERT SAVE NAME:", 100, stage.getHeight()-100);
        if(error) {
            redFont.draw(game.batch, "This save name is already taken!", 300, stage.getHeight()/2-textField.getHeight());
        }
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
