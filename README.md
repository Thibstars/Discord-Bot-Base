# Discord Bot Base [![Build Status](https://travis-ci.org/Thibstars/Discord-Bot-Base.svg?branch=master)](https://travis-ci.org/Thibstars/Discord-Bot-Base) [![codecov](https://codecov.io/gh/Thibstars/Discord-Bot-Base/branch/master/graph/badge.svg)](https://codecov.io/gh/Thibstars/Discord-Bot-Base) [![Dependabot Status](https://api.dependabot.com/badges/status?host=github&repo=Thibstars/Discord-Bot-Base)](https://dependabot.com)
 # 
This repository serves as a starting point to create Java Discord bot applications. It comes with some default commands and provides some predefined 
configuration.

This project's aim is to help you get your bot project kicked off as fast and smooth as possible.
To facilitate this, the project makes use of [JDA](https://github.com/DV8FromTheWorld/JDA) and [Picocli](https://github.com/remkop/picocli).

## Usage ##

In order to run the application, one must first add a Discord bot token to `bot.token` in the `token.properties` file.
**Note that a bot token should never be committed in git!**

When running directly using `java -jar` you can also pass your token as a run argument instead. This is also the used approach in the `Dockerfile`.

### Docker ###
When running the application from the `Dockerfile` make sure to add a new `BOT_TOKEN` environment variable with the bot token as value so it can be picked up 
in the underlying `java -jar` entrypoint command.

## Discord Setup ##
### Retrieving the Token ###
If you didn't create a bot account yet, navigate to https://discordapp.com/developers/applications while logged in.
From there, create a new application. In the left panel you will have a 'Bot' section. When a new bot is added, the necessary token will become available.
Since the token provides access to the bot, make sure to never share it.

### Inviting the Bot to a Server ###
While still in the developer portal in your application context, navigate to 'OAuth2' in the left panel.
Under 'scopes', tick the 'bot' checkbox to retrieve an invite url for your bot. If the bot needs any permissions, make sure to tick them in the 
'bot permissions' section. When ready, navigate to the generated url and add your bot to the target server.

When the bot is running, you will now see it appear in the member list of your server in the Discord app.