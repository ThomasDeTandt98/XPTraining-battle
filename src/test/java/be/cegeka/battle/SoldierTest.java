package be.cegeka.battle;


import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SoldierTest {

    private final Soldier soldier=new Soldier("Bob");
    private final Soldier otherSoldier=new Soldier("Bart");
    @Test
    void construction_aSoldierMustHaveAName() {
        Soldier soldier = new Soldier("name");

        assertThat(soldier.getName()).isEqualTo("name");
    }

    @Test
    void construction_aSoldierMustHaveAName_cannotBeNull() {
        assertThatThrownBy(() -> new Soldier(null))
                .hasMessage("A soldier must have a name")
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void construction_aSoldierMustHaveAName_cannotBeEmpty() {
        assertThatThrownBy(() -> new Soldier(""))
                .hasMessage("A soldier must have a name")
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void construction_aSoldierMustHaveAName_cannotBeBlank() {
        assertThatThrownBy(() -> new Soldier("     "))
                .hasMessage("A soldier must have a name")
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void soldier_whenCreated_thenDefaultWeaponBareFist(){


        assertThat(soldier.getWeapon()).isInstanceOf(BareFist.class);
    }

    @Test
    void soldier_whenFight_thenHighestDamageWins(){
        soldier.setWeapon(new Axe());

        Soldier loser = soldier.fight(otherSoldier);

        assertThat(loser).isEqualTo(otherSoldier);
    }

    @Test
    void soldier_whenFight_thenEqualDamageAttackerWins(){
        Soldier loser = soldier.fight(otherSoldier);
        assertThat(loser).isEqualTo(otherSoldier);
    }
}