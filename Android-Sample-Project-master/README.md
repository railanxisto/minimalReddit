# Minimal Reddit

A minimal reddit app devoted to your subreddit of choice

## Qualifications

* Make basic reddit browser from publically api calls (no OAuth required)
* Api documentation: https://www.reddit.com/dev/api

* Desired functionality
    * Browse default or subreddit of your choice with  image and titles. Bonus points if you use pagination
        * E.g. https://www.reddit.com/r/androiddev.json?limit=50&after=d94dps
    * Drill into posts from the subreddit (t3 ids) and view content and comments (t1 ids). Show username associated and points for top level replies and their immediate children (not necessary to go deeper)
        * E.g. https://www.reddit.com/comments/d8uhci/.json

* Implementation guidelines:

    * No need to recreate all functionality (or comment formatting) or expose all the information of reddit/existing apps by any means. Only things required are what's listed above
    * Any and all 3rd party dependencies you feel like using are fair game.
    * Kotlin required, MVVM architecture a plus
