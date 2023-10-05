package be.cegeka.battle;


import com.google.common.base.Strings;

public class Soldier {

    private int id;
    private final String name;
    private Weapon weapon=new BareFist();

    public Soldier(String name) {
        if (Strings.isNullOrEmpty(name) ||name.isBlank()) {
            throw new IllegalArgumentException("A soldier must have a name");
        }

        this.name = name;
    }

    String getName() {
        return this.name;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public Soldier fight(Soldier otherSoldier) {
        if (weapon.getDamage()>=otherSoldier.getWeapon().getDamage()) {
            return otherSoldier;
        }
        return this;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
