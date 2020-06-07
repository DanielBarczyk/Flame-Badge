package com.oop.project.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

import java.util.Collection;

public class Skins {

    public static Skin defaultSkin() {
        BitmapFont font = new BitmapFont();
        Skin skin = new Skin();
        skin.add("default", font);

        Pixmap pixmap = new Pixmap(Gdx.graphics.getWidth()/4,Gdx.graphics.getHeight()/10, Pixmap.Format.RGB565);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        skin.add("background", new Texture(pixmap));

        TextField.TextFieldStyle textFieldStyle = new TextField.TextFieldStyle();
        textFieldStyle.font = BoldFont();
        textFieldStyle.fontColor = Color.LIGHT_GRAY;
        textFieldStyle.focusedFontColor = Color.WHITE;
        skin.add("default", textFieldStyle);

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.newDrawable("background", Color.GRAY);
        textButtonStyle.down = skin.newDrawable("background", Color.DARK_GRAY);
        textButtonStyle.checked = skin.newDrawable("background", Color.DARK_GRAY);
        textButtonStyle.over = skin.newDrawable("background", Color.LIGHT_GRAY);
        textButtonStyle.font = skin.getFont("default");
        skin.add("default", textButtonStyle);
        return skin;
    }

    public static BitmapFont DefaultFont() {

        FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("Blackwood Castle.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter fontParameter  = new FreeTypeFontGenerator.FreeTypeFontParameter();

        fontParameter.size = 40;
        fontParameter.borderWidth = 5;
        fontParameter.borderColor = Color.BLACK;
        fontParameter.color = Color.WHITE;
        return fontGenerator.generateFont(fontParameter);
    }

    public static BitmapFont BoldFont() {
        FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("Blackwood Castle.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter fontParameter  = new FreeTypeFontGenerator.FreeTypeFontParameter();

        fontParameter.size = 50;
        fontParameter.borderWidth = 5;
        fontParameter.borderColor = Color.BLACK;
        fontParameter.color = Color.LIGHT_GRAY;
        return fontGenerator.generateFont(fontParameter);
    }

    public static BitmapFont TitleFont() {
        FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("Blackwood Castle.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter fontParameter  = new FreeTypeFontGenerator.FreeTypeFontParameter();

        fontParameter.size = 100;
        fontParameter.borderWidth = 5;
        fontParameter.borderColor = Color.FIREBRICK;
        fontParameter.color = Color.SCARLET;
        return fontGenerator.generateFont(fontParameter);
    }

    public static BitmapFont SubTitleFont() {
        FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("Blackwood Castle.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter fontParameter  = new FreeTypeFontGenerator.FreeTypeFontParameter();

        fontParameter.size = 100;
        fontParameter.borderWidth = 5;
        fontParameter.borderColor = Color.BLACK;
        fontParameter.color = Color.LIGHT_GRAY;
        return fontGenerator.generateFont(fontParameter);
    }

    public static BitmapFont RedFont() {

        FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("Blackwood Castle.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter fontParameter  = new FreeTypeFontGenerator.FreeTypeFontParameter();

        fontParameter.size = 40;
        fontParameter.borderWidth = 5;
        fontParameter.borderColor = Color.BLACK;
        fontParameter.color = Color.RED;
        return fontGenerator.generateFont(fontParameter);
    }


}
