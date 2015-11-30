package com.singularity.lib;

/**
 * Created by zstring on 11/27/15.
 * Abstract class for the Listener/Observer.
 * It has many abstract function that any Observable class will implement
 * to get the notification of the respective event.
 */
abstract class EEGEventListener {

    /**
     * This method will be called by the EEGListenerAndNotify class whenever there is an
     * jawClench event occurs. Client will override this method and do the necessary functionality.
     */
    public abstract void jawClench();
}