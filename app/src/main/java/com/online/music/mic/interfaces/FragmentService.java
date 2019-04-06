package com.online.music.mic.interfaces;

import android.os.Bundle;

public interface FragmentService {

    void inflateFragment(Bundle bundle, int fragmentId, String tag, boolean isBackstack);
}
