package com.singularity.lib;

import android.util.Log;

import com.interaxon.libmuse.MuseArtifactPacket;
import com.interaxon.libmuse.MuseDataListener;
import com.interaxon.libmuse.MuseDataPacket;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zstring on 11/27/15.
 * This class listens different any eeg device(muse) event and pass them to the EEGEventListener
 * class so that client can implement the respective functionality.
 * This class behaves Observer/Listener for Muse Device and Subject for the android activity class.
 */
public class EEGListenerAndNotify extends MuseDataListener {

    /**
     * it contains all the observer attached to it and uses this list to notify whenever there is an
     * event occurs
     */
    private List<EEGEventListener> observers;

    /**
     * Constructor of the class. Initializes the observer member variable.
     */
    public EEGListenerAndNotify() {
        observers = new ArrayList<EEGEventListener>();
    }

    @Override
    public void receiveMuseDataPacket(MuseDataPacket p) {
        throw new UnsupportedOperationException("This class does not override this " +
                "receiveMuseDataPacket Method");
    }

    @Override
    public void receiveMuseArtifactPacket(MuseArtifactPacket p) {
        if (p.getHeadbandOn() && p.getJawClench()) {
            Log.i("Artifacts", "jawClench");
            notifyObserver();
        }
    }

    /**
     * attach function takes observer as an input and add it to the list. This function attaches the
     * given listener.
     * @param observer Listener to be attached.
     */
    public void attach(EEGEventListener observer) {
        this.observers.add(observer);
    }

    /**
     * Notifies all the attached observer with respective event.
     */
    public void notifyObserver(){
        for (EEGEventListener observer : observers)
            observer.jawClench();
    }
}