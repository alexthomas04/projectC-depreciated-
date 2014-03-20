package entities;

import lombok.Getter;

public interface Edible {
	abstract int getFoodLevel();
	abstract int getMaxFoodLevel();
}
