package be.cegeka.battle;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Army {
    private List<Soldier> soldiers = new ArrayList<>();
    private final IHeadquarters headquarters;

    public Army(IHeadquarters headquarters) {
        this.headquarters = headquarters;
    }

    public List<Soldier> getSoldiers() {
        return soldiers;
    }

    public void addSoldier(Soldier soldier) {
        if (soldier == null) {
            throw new IllegalArgumentException("Soldier must exist");
        }
        if (soldiers.contains(soldier)) {
            throw new IllegalArgumentException("Same soldier can not be added twice");
        }

        soldiers.add(soldier);
        soldier.setId(headquarters.reportEnlistment(soldier.getName()));
    }

    public Optional<Soldier> getFrontMen() {
        if(soldiers.size()>0){
            return Optional.of(soldiers.get(0));
        }else{
            return Optional.empty();
        }
    }

    public void soldierDied(Soldier soldier) {
        headquarters.reportCasualty(soldier.getId());
        soldiers.remove(soldier);
    }

    public IHeadquarters getHeadquarters() {
        return headquarters;
    }

    public Army fight(Army otherArmy) {
        Optional<Soldier> frontMen, otherArmyFrontMen;
        do {
            frontMen = getFrontMen();
            otherArmyFrontMen = otherArmy.getFrontMen();
            if (armiesStillHaveSoldiers(frontMen, otherArmyFrontMen)) {
                Soldier deadMan = frontMen.get().fight(otherArmyFrontMen.get());
                soldierDied(deadMan);
                otherArmy.soldierDied(deadMan);
            }

        } while (armiesStillHaveSoldiers(frontMen, otherArmyFrontMen));
        if (frontMen.isPresent()) {
            headquarters.reportVictory(soldiers.size());
            return this;
        }
        otherArmy.getHeadquarters().reportVictory(soldiers.size());
        return otherArmy;
    }

    private static boolean armiesStillHaveSoldiers(Optional<Soldier> frontMen, Optional<Soldier> otherArmyFrontMen) {
        return frontMen.isPresent() && otherArmyFrontMen.isPresent();
    }
}
