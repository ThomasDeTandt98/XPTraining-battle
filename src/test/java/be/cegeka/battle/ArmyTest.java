package be.cegeka.battle;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;

public class ArmyTest {
    private Soldier soldier;
    private Army army;
    private IHeadquarters headquarters;

    @BeforeEach
    void setup(){
        soldier = new Soldier("Bob");
        headquarters = Mockito.mock(IHeadquarters.class);
        army = new Army(headquarters);
        army.addSoldier(soldier);
    }

    @Test
    void army_whenSoldierAddedToArmy_thenSoldierInArmy() {
        assertThat(army.getSoldiers()).containsExactly(soldier);
    }

    @Test
    void army_whenAddingTheSameSoldierTwice_throwsException() {
        Assertions.assertThatThrownBy(() -> army.addSoldier(soldier))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Same soldier can not be added twice");
    }

    @Test
    void army_whenAddingNull_throwsException() {
        Assertions.assertThatThrownBy(() -> army.addSoldier(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Soldier must exist");
    }

    @Test
    void army_whenGettingFrontMen_returnsFirstEnlistedSoldier(){
        Soldier otherSoldier = new Soldier("Bart");
        army.addSoldier(otherSoldier);

        assertThat(army.getFrontMen().get()).isEqualTo(soldier);
    }

    @Test
    void army_whenArmyFights_strongestArmyWins(){
        Soldier soldier3 = new Soldier("Ben");
        Soldier soldier4 = new Soldier("Bono");
        army.addSoldier(soldier3);
        Army otherArmy = new Army(headquarters);
        Soldier otherSoldier = new Soldier("Bart");
        otherArmy.addSoldier(otherSoldier);
        otherArmy.addSoldier(soldier4);

        Army winner = army.fight(otherArmy);

        assertThat(winner).isEqualTo(army);
        assertThat(otherArmy.getFrontMen()).isEmpty();
        assertThat(winner.getFrontMen().get()).isEqualTo(soldier);
    }

    @Test
    void army_whenSoldierIsEnlisted_reportToHeadquarters(){
        Soldier otherSoldier = new Soldier("Bart");

        army.addSoldier(otherSoldier);

        Mockito.verify(headquarters).reportEnlistment(otherSoldier.getName());
    }

    @Test
    void army_whenSoldierIsEnlisted_headquartersAssignsIdToSoldier(){
        int id = 5;
        Soldier otherSoldier = new Soldier("Bart");
        Mockito.when(headquarters.reportEnlistment(otherSoldier.getName())).thenReturn(id);

        army.addSoldier(otherSoldier);

        assertThat(otherSoldier.getId()).isEqualTo(id);
    }

    @Test
    void army_whenSoldierDied_headquartersIsNotified(){
        int id = 5;
        Soldier otherSoldier = new Soldier("Bart");
        Mockito.when(headquarters.reportEnlistment(otherSoldier.getName())).thenReturn(id);

        army.addSoldier(otherSoldier);
        army.soldierDied(soldier);

        Mockito.verify(headquarters).reportCasualty(soldier.getId());
    }

    @Test
    void army_whenArmyWins_reportedToHeadquarters(){
        Soldier soldier3 = new Soldier("Ben");
        Soldier soldier4 = new Soldier("Bono");
        army.addSoldier(soldier3);
        Army otherArmy = new Army(headquarters);
        Soldier otherSoldier = new Soldier("Bart");
        otherArmy.addSoldier(otherSoldier);
        otherArmy.addSoldier(soldier4);

        army.fight(otherArmy);


        Mockito.verify(headquarters).reportVictory(2);
    }
}
