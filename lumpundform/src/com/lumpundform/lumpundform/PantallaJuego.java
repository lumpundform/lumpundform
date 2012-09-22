package com.lumpundform.lumpundform;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.tiled.TileAtlas;
import com.badlogic.gdx.graphics.g2d.tiled.TileMapRenderer;
import com.badlogic.gdx.graphics.g2d.tiled.TiledLoader;
import com.badlogic.gdx.graphics.g2d.tiled.TiledMap;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class PantallaJuego implements Screen {
	// private Lumpundform juego;

	private SpriteBatch batch;
	OrthographicCamera camara;
	Heroe heroe;
	Texture fondo;
	TiledMap mapa;
	TileAtlas atlas;
	TileMapRenderer tileMapRenderer;
	World mundo;
	Box2DDebugRenderer debugRenderer;

	private Stage escenario;
	
	public static final float PIXELS_PER_METER = 60.0f;

	public PantallaJuego(Lumpundform juego) {
		// this.juego = juego;

		// Cámara en Modo Ortográfico
		camara = new OrthographicCamera();
		camara.setToOrtho(false);

		// Mapa
		mapa = TiledLoader.createMap(Gdx.files.internal("data/world/level1/level.tmx"));
		atlas = new TileAtlas(mapa, Gdx.files.internal("data/packer/bosque/"));
		tileMapRenderer = new TileMapRenderer(mapa, atlas, 64, 64);
		
		// load collision
		
		mundo = new World(new Vector2(0.0f, -20.0f), true);
		
		loadCollisions("data/packer/colision/level.txt", mundo, 60.0f);
		debugRenderer = new Box2DDebugRenderer();
		// Detectar gestos con DetectorGestos
		Gdx.input.setInputProcessor(new InputMultiplexer(new GestureDetector(
				new DetectorGestos(this)), new ProcesadorEntrada(this)));
		
		batch = new SpriteBatch();
		heroe = new Heroe("heroe", mundo);
		
		// Escenario
		escenario = new Stage(2000.0f, 720.0f, true, batch);
		escenario.addActor(heroe);
		escenario.setCamera(camara);
	}
	
	@Override
	public void render(float delta) {
		// Limpiar pantalla
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		tileMapRenderer.render(camara);

		// Mover cámara
		camara.position.x = heroe.x;
		camara.position.y = heroe.y;
		if (camara.position.x < (0 + Gdx.graphics.getWidth() / 2)) {
			camara.position.x = 0 + Gdx.graphics.getWidth() / 2;
		} else if (camara.position.x > (escenario.width() - Gdx.graphics.getWidth() / 2)) {
			camara.position.x = escenario.width() - Gdx.graphics.getWidth() / 2;
		}
		if (camara.position.y < (0 + Gdx.graphics.getHeight() / 2)) {
			camara.position.y = 0 + Gdx.graphics.getHeight() / 2;
		} else if (camara.position.y > (escenario.height() - Gdx.graphics.getHeight() / 2)) {
			camara.position.y = escenario.height() - Gdx.graphics.getHeight() / 2;
		}
		
		
		/*if (heroe.x > 400 && camara.position.x >= 400 && camara.position.x <= 2000) {
			if (heroe.estado == Personaje.MOVIMIENTO
					&& heroe.direccionX == Personaje.IZQUIERDA) {
				camara.translate(-heroe.velocidad * delta, 0);
			} else if (heroe.estado == Personaje.MOVIMIENTO
					&& heroe.direccionX == Personaje.DERECHA) {
				camara.translate(heroe.velocidad * delta, 0);
			}
			if (camara.position.x > 2000) camara.position.set(2000, camara.position.y, 0);
			if (camara.position.x < 400) camara.position.set(400, camara.position.y, 0);
		} */

		if ((heroe.cuerpo.getLinearVelocity().y == 0) && Gdx.input.isTouched() || Gdx.input.isKeyPressed(Keys.A) || Gdx.input.isKeyPressed(Keys.D)) {
			Vector3 pos = DetectorGestos.alinearCoordenadas(Gdx.input.getX(),
					Gdx.input.getY(), camara);

			if (Gdx.input.isKeyPressed(Keys.D) || (pos.x > heroe.x) && !Gdx.input.isKeyPressed(Keys.A)) {
				heroe.direccionX = Personaje.DERECHA;
			} else {
				heroe.direccionX = Personaje.IZQUIERDA;
			}
			heroe.destinoX = heroe.x;
			heroe.estado = Personaje.MOVIMIENTO;
		} else if (heroe.estado == Personaje.MOVIMIENTO) {
			heroe.estado = Personaje.DE_PIE;
		}

		escenario.act(delta);
		escenario.draw();

		mundo.step(1/60.0f, 6, 2);
		
		//debugRender();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		batch.dispose();
		escenario.dispose();
	}
	

	public void loadCollisions(String collisionsFile, World world,
			float pixelsPerMeter) {
		/**
		 * Detect the tiles and dynamically create a representation of the map
		 * layout, for collision detection. Each tile has its own collision
		 * rules stored in an associated file.
		 * 
		 * The file contains lines in this format (one line per type of tile):
		 * tileNumber XxY,XxY XxY,XxY
		 * 
		 * Ex:
		 * 
		 * 3 0x0,31x0 ... 4 0x0,29x0 29x0,29x31
		 * 
		 * For a 32x32 tileset, the above describes one line segment for tile #3
		 * and two for tile #4. Tile #3 has a line segment across the top. Tile
		 * #1 has a line segment across most of the top and a line segment from
		 * the top to the bottom, 30 pixels in.
		 */

		FileHandle fh = Gdx.files.internal(collisionsFile);
		String collisionFile = fh.readString();
		String lines[] = collisionFile.split("\\r?\\n");

		HashMap<Integer, ArrayList<LineSegment>> tileCollisionJoints = new HashMap<Integer, ArrayList<LineSegment>>();

		/**
		 * Some locations on the map (perhaps most locations) are "undefined",
		 * empty space, and will have the tile type 0. This code adds an empty
		 * list of line segments for this "default" tile.
		 */
		tileCollisionJoints.put(Integer.valueOf(0),
				new ArrayList<LineSegment>());

		for (int n = 0; n < lines.length; n++) {
			String cols[] = lines[n].split(" ");
			int tileNo = Integer.parseInt(cols[0]);

			ArrayList<LineSegment> tmp = new ArrayList<LineSegment>();

			for (int m = 1; m < cols.length; m++) {
				String coords[] = cols[m].split(",");

				String start[] = coords[0].split("x");
				String end[] = coords[1].split("x");

				tmp.add(new LineSegment(Integer.parseInt(start[0]), Integer
						.parseInt(start[1]), Integer.parseInt(end[0]), Integer
						.parseInt(end[1])));
			}

			tileCollisionJoints.put(Integer.valueOf(tileNo), tmp);
		}

		ArrayList<LineSegment> collisionLineSegments = new ArrayList<LineSegment>();

		for (int y = 0; y < mapa.height; y++) {
			for (int x = 0; x < mapa.width; x++) {
				int tileType = mapa.layers.get(0).tiles[(mapa.height - 1)
						- y][x];

				for (int n = 0; n < tileCollisionJoints.get(
						Integer.valueOf(tileType)).size(); n++) {
					LineSegment lineSeg = tileCollisionJoints.get(
							Integer.valueOf(tileType)).get(n);

					addOrExtendCollisionLineSegment(x * mapa.tileWidth
							+ lineSeg.start().x, y * mapa.tileHeight
							- lineSeg.start().y + 32, x * mapa.tileWidth
							+ lineSeg.end().x, y * mapa.tileHeight
							- lineSeg.end().y + 32, collisionLineSegments);
				}
			}
		}

		BodyDef groundBodyDef = new BodyDef();
		groundBodyDef.type = BodyDef.BodyType.StaticBody;
		Body groundBody = world.createBody(groundBodyDef);
		for (LineSegment lineSegment : collisionLineSegments) {
			EdgeShape environmentShape = new EdgeShape();
			
			environmentShape.set(
					lineSegment.start().mul(1 / pixelsPerMeter), lineSegment
							.end().mul(1 / pixelsPerMeter));
			groundBody.createFixture(environmentShape, 0);
			environmentShape.dispose();
		}

		/**
		 * Drawing a boundary around the entire map. We can't use a box because
		 * then the world objects would be inside and the physics engine would
		 * try to push them out.
		 */

		EdgeShape mapBounds = new EdgeShape();
		mapBounds.set(new Vector2(0.0f, 0.0f), new Vector2(getWidth()
				/ pixelsPerMeter, 0.0f));
		groundBody.createFixture(mapBounds, 0);

		mapBounds.set(new Vector2(0.0f, getHeight() / pixelsPerMeter),
				new Vector2(getWidth() / pixelsPerMeter, getHeight()
						/ pixelsPerMeter));
		groundBody.createFixture(mapBounds, 0);

		mapBounds.set(new Vector2(0.0f, 0.0f), new Vector2(0.0f,
				getHeight() / pixelsPerMeter));
		groundBody.createFixture(mapBounds, 0);

		mapBounds.set(new Vector2(getWidth() / pixelsPerMeter, 0.0f),
				new Vector2(getWidth() / pixelsPerMeter, getHeight()
						/ pixelsPerMeter));
		groundBody.createFixture(mapBounds, 0);

		mapBounds.dispose();
	}
	

	private class LineSegment {
		private Vector2 start = new Vector2();
		private Vector2 end = new Vector2();

		/**
		 * Construct a new LineSegment with the specified coordinates.
		 * 
		 * @param x1
		 * @param y1
		 * @param x2
		 * @param y2
		 */
		public LineSegment(float x1, float y1, float x2, float y2) {
			start = new Vector2(x1, y1);
			end = new Vector2(x2, y2);
		}

		/**
		 * The "start" of the line. Start and end are misnomers, this is just
		 * one end of the line.
		 * 
		 * @return Vector2
		 */
		public Vector2 start() {
			return start;
		}

		/**
		 * The "end" of the line. Start and end are misnomers, this is just one
		 * end of the line.
		 * 
		 * @return Vector2
		 */
		public Vector2 end() {
			return end;
		}

		/**
		 * Determine if the requested line could be tacked on to the end of this
		 * line with no kinks or gaps. If it can, the current LineSegment will
		 * be extended by the length of the passed LineSegment.
		 * 
		 * @param lineSegment
		 * @return boolean true if line was extended, false if not.
		 */
		public boolean extendIfPossible(LineSegment lineSegment) {
			/**
			 * First, let's see if the slopes of the two segments are the same.
			 */
			double slope1 = Math.atan2(end.y - start.y, end.x - start.x);
			double slope2 = Math.atan2(lineSegment.end.y - lineSegment.start.y,
					lineSegment.end.x - lineSegment.start.x);

			if (Math.abs(slope1 - slope2) > 1e-9) {
				return false;
			}

			/**
			 * Second, check if either end of this line segment is adjacent to
			 * the requested line segment. So, 1 pixel away up through sqrt(2)
			 * away.
			 * 
			 * Whichever two points are within the right range will be "merged"
			 * so that the two outer points will describe the line segment.
			 */
			if (start.dst(lineSegment.start) <= Math.sqrt(2) + 1e-9) {
				start.set(lineSegment.end);
				return true;
			} else if (end.dst(lineSegment.start) <= Math.sqrt(2) + 1e-9) {
				end.set(lineSegment.end);
				return true;
			} else if (end.dst(lineSegment.end) <= Math.sqrt(2) + 1e-9) {
				end.set(lineSegment.start);
				return true;
			} else if (start.dst(lineSegment.end) <= Math.sqrt(2) + 1e-9) {
				start.set(lineSegment.start);
				return true;
			}

			return false;
		}

		/**
		 * Returns a pretty description of the LineSegment.
		 * 
		 * @return String
		 */
		@Override
		public String toString() {
			return "[" + start.x + "x" + start.y + "] -> [" + end.x + "x"
					+ end.y + "]";
		}
	}
	
	private void addOrExtendCollisionLineSegment(float lsx1, float lsy1,
			float lsx2, float lsy2, ArrayList<LineSegment> collisionLineSegments) {
		LineSegment line = new LineSegment(lsx1, lsy1, lsx2, lsy2);

		boolean didextend = false;

		for (LineSegment test : collisionLineSegments) {
			if (test.extendIfPossible(line)) {
				didextend = true;
				break;
			}
		}

		if (!didextend) {
			collisionLineSegments.add(line);
		}
	}

	/**
	 * Get the height of the map in pixels
	 * 
	 * @return y
	 */
	public int getHeight() {
		return mapa.height * mapa.tileHeight;
	}

	/**
	 * Get the width of the map in pixels
	 * 
	 * @return x
	 */
	public int getWidth() {
		return mapa.width * mapa.tileWidth;
	}
	
	public OrthographicCamera getCamera() {
		if (camara == null) {
			throw new IllegalStateException(
					"getCamera() called out of sequence");
		}
		return camara;
	}
	
	private void debugRender() {
		debugRenderer.render(mundo, getCamera().combined.scale(
				PIXELS_PER_METER,
				PIXELS_PER_METER,
				PIXELS_PER_METER));
	}
}
