# Discord Bot Base [![Build Status](https://travis-ci.org/Thibstars/Discord-Bot-Base.svg?branch=master)](https://travis-ci.org/Thibstars/Discord-Bot-Base) [![codecov](https://codecov.io/gh/Thibstars/Discord-Bot-Base/branch/master/graph/badge.svg)](https://codecov.io/gh/Thibstars/Discord-Bot-Base) # 
Discord bot base application.

## Usage ##

In order to run the application, one must first add a Discord bot token to `bot.token` in the `token.properties` file.
Note that this file is ignored in `.gitignore`. A bot token should never be committed in git.

When running directly using `java -jar` you can also directly pass your token as a first run argument. This is also the used approach in the `Dockerfile`.

### Docker ###
When running the application from the `Dockerfile` make sure to add a new `BOT_TOKEN` environment variable with the bot token as value so it can be picked up 
in the underlying `java -jar` entrypoint command.