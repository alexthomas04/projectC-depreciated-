package entities;

import lombok.Getter;

/**
 * The Interface Edible.
 */
public interface Edible {
	
	/**
	 * Gets the food level.
	 *
	 * @return the food level
	 */
	abstract int getFoodLevel();
	
	/**
	 * Gets the max food level.
	 *
	 * @return the max food level
	 */
	abstract int getMaxFoodLevel();
}
