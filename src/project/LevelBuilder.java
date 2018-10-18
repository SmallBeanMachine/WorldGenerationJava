package project;

public class LevelBuilder {
	private Level level;
	
	public LevelBuilder() {
		level = new Level();
	}
	
	public LevelBuilder fillLevel(Block block) {
		for (int i = 0; i < Constants.LEVEL_ROWS; i++) {
			for (int j = 0; j < Constants.LEVEL_COLUMNS; j++) {
				level.set(i, j, block);
			}
		}
		
		return this;
	}
	
	public LevelBuilder createRooms(int numberOfRooms, int minimumRoomWidth, int minimumRoomHeight, int maximumRoomWidth, int maximumRoomHeight, Block block) {
		for (int i = 0; i < numberOfRooms; i++) {
			int x1 = (int) (Math.random() * (Constants.LEVEL_ROWS - minimumRoomWidth));
			int y1 = (int) (Math.random() * (Constants.LEVEL_COLUMNS - minimumRoomHeight));
			int x2 = x1 + (int) (Math.random() * (maximumRoomWidth - minimumRoomWidth));
			int y2 = y1 + (int) (Math.random() * (maximumRoomHeight - minimumRoomHeight));
			if (x2 > Constants.LEVEL_ROWS) x2 = Constants.LEVEL_ROWS - 1;
			if (y2 > Constants.LEVEL_COLUMNS) y2 = Constants.LEVEL_COLUMNS - 1;
			
			for (int j = x1; j < x2; j++) {
				for (int k = y1; k < y2; k++) {
					level.set(j, k, block);
				}
			}
		}
		return this;
	}
	
	public LevelBuilder smooth(Block aliveState, Block deadState) {
		Level tempLevel = level.clone();
		
		for (int i = 0; i < Constants.LEVEL_ROWS; i++) {
			for (int j = 0; j < Constants.LEVEL_COLUMNS; j++) {
				int neighbors = 0;
				
				for (int k = 0; k < 3; k++) {
					for (int l = 0; l < 3; l++) {
						try {
							if (k == 1 && l == 1) continue;
							if (tempLevel.get(i + (k - 1), j + (l - 1)).getColor() == aliveState.getColor()) {
								neighbors++;
							}
						} catch (ArrayIndexOutOfBoundsException e) {;}
					}
				}
				
				if (neighbors >= Constants.MIN_NEIGHBORS_ALIVE && neighbors <= Constants.MAX_NEIGHBORS_ALIVE) {
					level.set(i, j, aliveState);
				} else {
					level.set(i, j, deadState);
				}
			}
		}
		
		return this;
	}
	
	public Level getLevel() {
		return level;
	}
}
