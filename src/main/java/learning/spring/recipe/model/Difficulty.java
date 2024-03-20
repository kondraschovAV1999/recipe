package learning.spring.recipe.model;

import lombok.Getter;

@Getter
public enum Difficulty {
    EASY("Easy"),
    MODERATE("Moderate"),
    HARD("Hard");
    private final String displayValue;

    Difficulty(String displayName) {
        this.displayValue = displayName;
    }
}
