package com.example.nhan.keephealthyver2.events;

import com.example.nhan.keephealthyver2.models.BreathRealmObject;
import com.example.nhan.keephealthyver2.models.PhysicalRealmObject;

/**
 * Created by Nhan on 10/14/2016.
 */

public class EventSendPhysicalObject {
    private PhysicalRealmObject physicalRealmObject;

    public EventSendPhysicalObject(PhysicalRealmObject physicalRealmObject) {
        this.physicalRealmObject = physicalRealmObject;
    }

    public PhysicalRealmObject getPhysicalRealmObject() {
        return physicalRealmObject;
    }
}
