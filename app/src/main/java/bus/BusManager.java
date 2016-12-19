package bus;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

/**
 * Created by gerardosuarez on 18/12/16.
 */
public class BusManager {

    public static Bus bus;

    public static BusManager busManager;

    /**
     * Bus Singleton method to inialize the BusManager
     * @return
     */
    public static BusManager getInstance(){
        if (busManager == null){
            busManager = new BusManager();
        }

        return busManager;
    }

    /**
     * BusManager constructor to initialize the bus
     */
    private BusManager (){
        bus = new Bus(ThreadEnforcer.MAIN);
    }

    //Getter and Setters

    public static Bus getBus() {
        return bus;
    }

    public static void setBus(Bus bus) {
        BusManager.bus = bus;
    }
}
