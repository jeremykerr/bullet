package com.mygdx.game;

import com.artemis.World;
import com.artemis.WorldConfiguration;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.components.*;
import com.mygdx.game.systems.*;

public class BulletGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	FPSLogger fpsLogger = new FPSLogger();

	public static Camera camera;
	final float WORLD_WIDTH = 1000;
	final float WORLD_HEIGHT = 1000;

	public static long debugEntitiesCreated = 0;

	public static World world;
	
	@Override
	public void create () {


		/*
		Graphics.Monitor currMonitor = Gdx.graphics.getMonitor();
		Graphics.DisplayMode displayMode = Gdx.graphics.getDisplayMode(currMonitor);
		if(!Gdx.graphics.setFullscreenMode(displayMode)) {
			System.out.println("full screen failed");
		}
		*/



		camera = new OrthographicCamera(WORLD_WIDTH, WORLD_WIDTH * (WORLD_HEIGHT / WORLD_WIDTH));
		//camera = new OrthographicCamera(1920, 1080);
		camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
		camera.update();

		WorldConfiguration config = new WorldConfiguration()
				.setSystem(new RenderingSystem())
				.setSystem(new CircularMovementSystem())
				.setSystem(new LinearMovementSystem())
				.setSystem(new CountdownSystem())
				.setSystem(new SpawningSystem())
                .setSystem(new RingSpawningSystem())
				.setSystem(new RotatedLinearMovementSystem());
		world = new World(config);


		int spawner = world.create();
		world.edit(spawner)
				.add(new Position(400,400))
				.add(new Sprite(200, 200, false))
			//	.add(new CircularMovement())
				//.add(new LinearMovement())
                .add(new Spawner());

        int circleSpawner = world.create();
        world.edit(circleSpawner)
                .add(new Position(600,400))
                .add(new Sprite(200, 200, false))
                	.add(new CircularMovement())
                //.add(new LinearMovement())
                .add(new Spawner())
                .add(new RingPositions());
	}

	@Override
	public void render () {

		fpsLogger.log();

		world.setDelta(Gdx.graphics.getDeltaTime());
		System.out.println(debugEntitiesCreated);
		world.process();
	}

	@Override
	public void resize(int width, int height) {
		camera.viewportWidth = WORLD_WIDTH;
		camera.viewportHeight = WORLD_WIDTH * (WORLD_HEIGHT/WORLD_WIDTH);
		//camera.viewportWidth = 1920;
		//camera.viewportHeight = 1080;
		camera.update();
	}
}
