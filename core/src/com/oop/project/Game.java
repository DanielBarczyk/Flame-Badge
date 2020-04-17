package com.oop.project;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.oop.project.battles.Combat;
import com.oop.project.entities.EnemyCharacter;
import com.oop.project.map.GameMap;
import com.oop.project.map.TileType;
import com.oop.project.map.TiledGameMap;

public class Game extends ApplicationAdapter {
	private SpriteBatch batch;
	private OrthographicCamera cam;

	private GameMap gameMap;

	@Override
	public void create () {
		batch = new SpriteBatch();

		cam = new OrthographicCamera();
		cam.setToOrtho(false,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		cam.update();

		gameMap = new TiledGameMap();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

//drag map around
		if(Gdx.input.isTouched()){
			cam.translate(-Gdx.input.getDeltaX(),Gdx.input.getDeltaY());
			cam.update();
		}

		if(Gdx.input.justTouched()){
			Vector3 pos=cam.unproject(new Vector3(Gdx.input.getX(),Gdx.input.getY(),0));
			TileType type=gameMap.getTileTypeByLocation(0,pos.x,pos.y);
			if(type!=null){ //if we clicked in bounds
				int tileX=(int)(pos.x/TileType.TILE_SIZE);
				int tileY=(int)(pos.y/TileType.TILE_SIZE);
				if(gameMap.isTileOccupiedByPlayable(tileX,tileY)) {
					gameMap.activeCharacter= gameMap.playableOnTile(tileX,tileY);
				}
				if(gameMap.isTileOccupiedByEnemy(tileX,tileY)){
					EnemyCharacter enemyCharacter=gameMap.enemyOnTile(tileX,tileY);

					if(Combat.canTheyFight(gameMap.activeCharacter,enemyCharacter)){
						Combat.battle(gameMap.activeCharacter,enemyCharacter);
						if(gameMap.activeCharacter.getCurrentHp()<0) gameMap.kill(gameMap.activeCharacter);
						if(enemyCharacter.getCurrentHp()<0) gameMap.kill(enemyCharacter);
						gameMap.activeCharacter.makeInactive();
					}
				}
				//System.out.println(type.getId()+" "+type.getName());       //provide info on the tile clicked
			}
		}

		gameMap.update(Gdx.graphics.getDeltaTime());
		gameMap.render(cam,batch);
	}

	@Override
	public void dispose () {
		batch.dispose();
		gameMap.dispose();
	}
}