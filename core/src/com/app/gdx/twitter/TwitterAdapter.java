package com.app.gdx.twitter;

import twitter4j.auth.AccessToken;

public interface TwitterAdapter {

    AccessToken loadTwitter();

    void saveTwitter(AccessToken accessToken);
}
