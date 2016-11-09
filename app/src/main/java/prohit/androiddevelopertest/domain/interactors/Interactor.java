package prohit.androiddevelopertest.domain.interactors;

/**
 * Created by  Rahul Purohit.
 */
public interface Interactor extends Runnable {

    public static final String ERROR_CONNECTION = "ERROR CONNECTION";
    public static final String ERROR_HAS_UPDATE = "ERROR_HAS_UPDATE";

    @Deprecated
    public void cancel();
}
