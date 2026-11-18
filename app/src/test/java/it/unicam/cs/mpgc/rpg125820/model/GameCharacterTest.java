package it.unicam.cs.mpgc.rpg125820.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameCharacterTest {

    private static class TestCharacter extends GameCharacter {
        TestCharacter(int health, int attack, int defense) {
            super("Test", health, attack, defense);
        }
    }

    @Test
    void damageIsReducedByDefense() {
        TestCharacter character = new TestCharacter(100, 10, 3);
        int applied = character.takeDamage(10);
        assertEquals(7, applied);
        assertEquals(93, character.getHealth());
    }

    @Test
    void damageIsNeverBelowMinimum() {
        TestCharacter character = new TestCharacter(100, 10, 50);
        assertEquals(1, character.takeDamage(10));
    }

    @Test
    void healthNeverGoesBelowZero() {
        TestCharacter character = new TestCharacter(5, 10, 0);
        character.takeDamage(100);
        assertEquals(0, character.getHealth());
        assertFalse(character.isAlive());
    }

    @Test
    void negativeDamageIsRejected() {
        TestCharacter character = new TestCharacter(100, 10, 0);
        assertThrows(IllegalArgumentException.class, () -> character.takeDamage(-1));
    }
}
