package com.app.gdx.twitter;

import com.badlogic.gdx.Gdx;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.OAuthAuthorization;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

/**
 * Created by mayami on 2015/07/20.
 */
public class TwitterOAuth {
    public String authorizationUrl;
    Configuration configuration;
    Twitter twitter;

    final String consumerKey = "コンシューマーキー";
    final String consumerSecret = "コンシューマーセレクト";
    AccessToken accessToken;
    RequestToken requestToken;


    public TwitterOAuth()throws TwitterException{
        setConfiguration();
    }

    /**
     * 認証の際につかう
     */
    private void setConfiguration()throws TwitterException{
        ConfigurationBuilder builder = new ConfigurationBuilder();
        builder.setOAuthConsumerKey(consumerKey);
        builder.setOAuthConsumerSecret(consumerSecret);

        configuration =  builder.build();

        OAuthAuthorization authorization = new OAuthAuthorization(configuration);
        requestToken = authorization.getOAuthRequestToken();
        authorizationUrl = requestToken.getAuthorizationURL();
    }


    /**
     * 認証後にアクセストークンををセットする。これによりつぶやきが可能になる
     * @param accessToken
     * @return
     */
    private void setConfiguration(AccessToken accessToken) {
        ConfigurationBuilder builder = new ConfigurationBuilder();
        builder.setOAuthConsumerKey(consumerKey);
        builder.setOAuthConsumerSecret(consumerSecret);
        builder.setOAuthAccessToken(accessToken.getToken());
        builder.setOAuthConsumerSecret(accessToken.getTokenSecret());

        configuration = builder.build();
    }

    public void Authorization(String pin) throws TwitterException {
        TwitterFactory twitterFactory = new TwitterFactory(configuration);
        twitter = twitterFactory.getInstance();
        accessToken = twitter.getOAuthAccessToken(requestToken, pin);
        Gdx.app.log("INFO", accessToken.getToken());
    }


    public AccessToken getAccessToken(){
        return accessToken;
    }
}
