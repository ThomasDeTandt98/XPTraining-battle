package be.cegeka.battle;

public interface IHeadquarters {
    int reportEnlistment(String soldierName);
    void reportCasualty(int soldierId);
    void reportVictory(int remainingNumberOfSoldiers);
}