package playing.entities.dynamics.crabby;

public class CrabbyHealth extends CrabbyModule {

    private final int maxHealth = 100;
    private int currentHealth = maxHealth;

    public CrabbyHealth(Crabby crabby) {
        super(crabby);
    }

    public void attackEnemy(int value) {
        currentHealth -= value;
        if (currentHealth <= 0) {
            crabby.getCrabbyAnimation().setAnimationState(CrabbyAnimation.AnimationState.DEAD);
        } else {
            crabby.getCrabbyAnimation().setAnimationState(CrabbyAnimation.AnimationState.HIT);
        }
    }
}
