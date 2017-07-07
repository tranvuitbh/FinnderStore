package com.example.vutran.finderawsome.Direction;

import java.util.List;

/**
 * Created by VuTran on 6/14/2017.
 */

public interface DirectionFinderListener {
    void onDirectionFinderStart();
    void onDirectionFinderSuccess(List<Route> route);
}
