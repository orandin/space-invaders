package spaceinvaders.entities;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import gameframework.game.GameData;

/**
 * @author Matthieu Lepers
 */
public class AlienArray extends EntiteMovable {
	
	protected final Enemies[][] enemyArray = new Enemies[11][5];
	protected double xPos = 0;
	protected int yPos = 0;
	protected int sign = 1;
	
	/**
	 * Create the array witch set in movement all enemies
	 * @param data
	 * 		The game data
	 */
	public AlienArray(GameData data) {
		super(data);
		super.setPosition(new Point(0, 0));
		
		//Creating all enemies
		for (int i = 0; i < enemyArray.length; i++) {
			for (int j = 0; j < enemyArray[0].length; j++) {
				
				Enemies enemy = rulesToCreateEnemy(j, i, j);
				enemyArray[i][j] = enemy;
				data.getUniverse().addGameEntity(enemy);	
			}
		}
	}
	
	/* ----- Getters ----- */
	/**
	 * This entity have no bounding box, the player can't collide with it
	 */
	@Override
	public Rectangle getBoundingBox() {
		return new Rectangle(0, 0, 0, 0);
	}
	
	/* ----- Setters ----- */
	/**
	 * Remove an enemy by set it to null
	 * @param position
	 * 		The position in array, x is row, y is column
	 */
	public void removeEnemyFromPosition(Point position) {
		this.enemyArray[(int) position.getX()][(int) position.getY()] = null;
	}
	
	@Override
	public void oneStepMoveAddedBehavior() {}
	
	/**
	 * Anime all the enemies in the array
	 */
	@Override
	public void draw(Graphics g) {
		for (int i = 0; i < enemyArray.length; i++) {
			for (int j = 0; j < enemyArray[0].length; j++) {
				if (enemyArray[i][j] != null) {
					xPos += this.sign * 0.05;
					if (xPos >= 100) {
						this.sign = -1;
						yPos += 4;
					}
					if (xPos <= 0) {
						this.sign = 1;
						yPos += 4;
					}
					enemyArray[i][j].setOffsets(xPos, yPos);
					
					enemyArray[i][j].draw(g);
				}
			}
		}
	}
	
	/**
	 * Defines the rules to create an enemy by row
	 * @param row
	 * 		The enemy row in the game
	 * @param posX
	 * 		The enemy initial X position
	 * @param posY
	 * 		The enemy initial X position
	 * @return An enemy
	 */
	private Enemies rulesToCreateEnemy(int row, int posX, int posY) {
		switch(row) {
			case 1:
				return new LargeAlien(super.data, posX, posY, this);

			case 2:
			case 3:
				return new MediumAlien(super.data, posX, posY, this);
		}
		return new SmallAlien(super.data, posX, posY, this);
	}
}