package be.cegeka.battle;

public class Sword extends Weapon{
    private final int damage = 2;

    @Override
    public int getDamage() {
        return damage;
    }
}
